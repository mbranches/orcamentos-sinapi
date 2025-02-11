package com.branches.cpu.Dao;

import com.branches.cpu.model.Servico;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicoDao {
    public static List<Servico> consultarServicos(String descricao) {
        List<Servico> servicos = new ArrayList<>();
        String sql;
        if (descricao.isEmpty()) {
            sql = "SELECT * FROM precos_sinapi_teste";
        }
        else {
            sql = "SELECT * FROM precos_sinapi_teste WHERE descricao_insumo LIKE '%" + descricao.replace("\'", "\'\'") + "%'"; //substituindo '' por '''' para que não haja conflito entre o comando sql e a string descrição
        }

        try (PreparedStatement conexao = ConexaoBanco.getConexao().prepareStatement(sql)) {
            ResultSet resultSet = conexao.executeQuery();

            while (resultSet.next()) {
                Servico servico = new Servico();
                servico.setId(resultSet.getLong("id"));
                servico.setCodigo(resultSet.getLong("codigo"));
                servico.setDescricao(resultSet.getString("descricao_insumo").trim());
                servico.setUnidadeMedida(resultSet.getString("unidade_medida").trim());
                servico.setOrigemPreco(resultSet.getString("origem_preco"));
                servico.setPreco(resultSet.getDouble("preco"));
                servicos.add(servico);
            }

            return servicos;

        } catch (SQLException e) {
            System.out.println("Erro em consultarServicos");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
