package com.branches.utils;

import com.branches.request.InsumoPostRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class ReaderXlsx {
    private final ResourceLoader resourceLoader;

    public List<InsumoPostRequest> readerAllInsumos() throws IOException, InvalidFormatException {
        List<InsumoPostRequest> insumos = new ArrayList<>();

        String path = "files/SINAPI_Preco_Ref_Insumos_PA_202412_NaoDesonerado.xlsx";
        File file = resourceLoader.getResource("classpath:%s".formatted(path)).getFile();
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() < 7 || row.getRowNum() >= 4846) continue;

            InsumoPostRequest insumo = InsumoPostRequest.builder().build();

            for (Cell cell : row) {
                if (cell.toString().isEmpty()) continue;

                switch (cell.getColumnIndex()) {
                    case 0:
                        insumo.setCodigo(Long.parseLong(cell.toString().replace(".0", "")));
                        break;
                    case 1:
                        insumo.setDescricao(cell.toString().trim());
                        break;
                    case 2:
                        insumo.setUnidadeMedida(cell.toString().trim());
                        break;
                    case 3:
                        insumo.setOrigemPreco(cell.toString().trim());
                        break;
                    case 4:
                        insumo.setPreco(Double.parseDouble(cell.toString()));
                        break;
                }

            }
                if (!allFieldsFilled(insumo)){
                    log.info(insumo);
                    continue;
                }
                insumos.add(insumo);
        }
        return insumos;

    }

    private static boolean allFieldsFilled(InsumoPostRequest insumoPostRequest) {
        if (
                insumoPostRequest.getCodigo() == null ||
                        insumoPostRequest.getDescricao() == null ||
                        insumoPostRequest.getUnidadeMedida() == null ||
                        insumoPostRequest.getOrigemPreco() == null ||
                        insumoPostRequest.getPreco() == null
        ) {
            return false;
        }
        return true;
    }
}
