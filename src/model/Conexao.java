/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Victor Gadder
 */
public abstract class Conexao {
    
    protected static Connection con;
    private static final String DBUrl = "jdbc:postgresql://localhost:5432/exercicio";
    private static final String usuario = "postgres";
    private static final String senha = "123456";
    private static final String driver = "org.postgresql.Driver";
        
    public static Connection getConnection(){   
        if (con == null) {
            try {
                Class.forName(driver).newInstance();
                con = DriverManager.getConnection(DBUrl, usuario, senha);
            } catch (Exception e){
                System.err.println("Não foi possível conectar com o servidor do banco de dados.");
            }
        }
        return con;
    }
    
}

