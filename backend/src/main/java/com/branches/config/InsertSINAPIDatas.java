package com.branches.config;

import com.branches.model.Insumo;
import com.branches.repository.InsumoRepository;
import com.branches.service.InsumoService;
import com.branches.utils.SINAPIExcelReader;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InsertSINAPIDatas implements CommandLineRunner {
    private static final Logger log = LogManager.getLogger(InsertSINAPIDatas.class);
    private final InsumoRepository insumoRepository;
    private final SINAPIExcelReader sinapiExcelReader;

    @Override
    public void run(String... args) throws Exception {
        if (insumoRepository.findAll().isEmpty()) {
            List<Insumo> insumos = sinapiExcelReader.allInsumos();

            insumoRepository.saveAll(insumos);

            log.info("SINAPI datas loading successful");
        }
    }
}