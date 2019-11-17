/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.ViewEntregas;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leandro
 */
public class EmpresaVarejista {
    public static Connection conexao;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ViewEntregas().setVisible(true);
        });
    }
        
    public static Connection obterConexao() {
        if(conexao == null){
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conexao = DriverManager.getConnection(
                        "jdbc:oracle:thin:@localhost:1521:xe", "modelagem", "modelagem");
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(EmpresaVarejista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return conexao;
    }
    
    public static void desfazerAlteracoes() {
        if(conexao != null){
            try {
                conexao.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(EmpresaVarejista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void fecharConexao(){
        if(conexao != null){
            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmpresaVarejista.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                conexao = null;
            }
        }
    }
}
