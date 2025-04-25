package com.branches;

import com.branches.utils.InsertInsumos;
import com.branches.utils.InsertOrcamento;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InsertDatas implements CommandLineRunner {
    private final InsertInsumos insertInsumos;
    private final InsertOrcamento insertOrcamento;

    @Override
    public void run(String... args) throws Exception {
        insertInsumos.insertInsumosSINAPI();

        insertOrcamento.insertOrcamento();
    }
}
