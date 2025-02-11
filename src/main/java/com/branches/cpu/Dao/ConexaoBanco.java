/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.branches.cpu.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 *
 * @author Branches
 */


public class ConexaoBanco {
    private static final String url = "jdbc:mysql://localhost:3306/sinapiprecostestes";
    private static final String user = "root";
    private static final String password = "";

    private static Connection conexao;
    
    
    public static Connection getConexao() {
        try {
            if(conexao == null) {
                conexao = DriverManager.getConnection(url, user, password);
            }
      
            return conexao;
        } catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
            
    }
   
}
