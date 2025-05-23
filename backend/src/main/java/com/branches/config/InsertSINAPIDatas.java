package com.branches.config;

import com.branches.model.Supply;
import com.branches.repository.SupplyRepository;
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
    private final SupplyRepository supplyRepository;
    private final SINAPIExcelReader sinapiExcelReader;

    @Override
    public void run(String... args) throws Exception {
        if (supplyRepository.findAll().isEmpty()) {
            List<Supply> supplies = sinapiExcelReader.allSupplies();

            supplyRepository.saveAll(supplies);

            log.info("SINAPI datas loading successful");
        }
    }
}