package com.example.desktop;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
    private Connection conn ;

    public static  Connection criarConexao(){
        Connection conexao = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }
        catch (ClassNotFoundException e){
            System.out.println("Oops! Can't find class oracle.jdbc.driver.OracleDriver");
            System.exit(-1);
        }
        try{
            conexao = DriverManager.getConnection(
                    "jdbc:sqlserver://localhost;database=LojaInformatica", "sa","123456");
            //conn.setAutoCommit(false);
        }
        catch(Exception e){
            System.out.println("ERRO " + e.getMessage());
            //javax.swing.JOptionPane.showMessageDialog(null,e.getMessage(),"ERRO", javax.swing.JOptionPane.ERROR_MESSAGE);
            System.exit(-2);
        }
        return conexao;
    }
}
