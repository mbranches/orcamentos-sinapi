package com.branches.utils;

import com.branches.model.Insumo;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SINAPIExcelReader {
    private final ResourceLoader resourceLoader;
    private static final String SINAPI_FILE_PATH = "files/SINAPI_Preco_Ref_Insumos_PA_202412_NaoDesonerado.xlsx";

    public List<Insumo> allInsumos() throws IOException {
        List<Insumo> insumos = new ArrayList<>();

        try (InputStream is = resourceLoader.getResource("classpath:" + SINAPI_FILE_PATH).getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() < 7 || row.getRowNum() >= 4846) continue;

                Insumo insumo = Insumo.builder().build();

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

                if (allFieldsFilled(insumo)) {
                    insumos.add(insumo);
                }
            }
        }

        return insumos;
    }

    private static boolean allFieldsFilled(Insumo insumo) {
        return insumo.getCodigo() != null &&
                insumo.getDescricao() != null &&
                insumo.getUnidadeMedida() != null &&
                insumo.getOrigemPreco() != null &&
                insumo.getPreco() != null;
    }
}