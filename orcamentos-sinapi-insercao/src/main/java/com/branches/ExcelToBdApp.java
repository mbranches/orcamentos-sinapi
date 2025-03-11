package com.branches;

import com.branches.request.InsumoPostRequest;
import com.branches.service.InsumoService;
import com.branches.util.ReaderXlsx;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class ExcelToBdApp implements CommandLineRunner {
    private final InsumoService service;
    private final ReaderXlsx readerXlsx;

    @Override
    public void run(String... args) throws Exception {
        readerXlsx.readerAllInsumos().forEach(service::save);
    }
}
