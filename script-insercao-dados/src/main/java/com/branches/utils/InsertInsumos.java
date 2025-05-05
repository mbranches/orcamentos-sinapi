package com.branches.utils;

import com.branches.model.Insumo;
import com.branches.service.InsumoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class InsertInsumos {
    private final InsumoService service;
    private final ReaderXlsx readerXlsx;

    public void insertInsumosSINAPI() throws Exception {
        List<Insumo> insumos = service.saveAll(readerXlsx.readerAllInsumos());

        log.info(insumos);
    }
}
