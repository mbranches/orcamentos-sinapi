package com.branches.utils;

import com.branches.model.Supply;
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

    public List<Supply> allSupplies() throws IOException {
        List<Supply> supplies = new ArrayList<>();

        try (InputStream is = resourceLoader.getResource("classpath:" + SINAPI_FILE_PATH).getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() < 7 || row.getRowNum() >= 4846) continue;

                Supply supply = Supply.builder().build();

                for (Cell cell : row) {
                    String cellValue = cell.toString().trim();
                    if (cellValue.isEmpty()) continue;

                    switch (cell.getColumnIndex()) {
                        case 0:
                            supply.setCode(Long.parseLong(cellValue.replace(".0", "")));
                            break;
                        case 1:
                            supply.setDescription(cellValue);
                            break;
                        case 2:
                            supply.setUnitMeasurement(cellValue);
                            break;
                        case 3:
                            supply.setOriginPrice(cellValue);
                            break;
                        case 4:
                            supply.setPrice(Double.parseDouble(cellValue));
                            break;
                    }
                }

                if (allFieldsFilled(supply)) {
                    supplies.add(supply);
                }
            }
        }

        return supplies;
    }

    private static boolean allFieldsFilled(Supply supply) {
        return supply.getCode() != null &&
                supply.getDescription() != null &&
                supply.getUnitMeasurement() != null &&
                supply.getOriginPrice() != null &&
                supply.getPrice() != null;
    }
}