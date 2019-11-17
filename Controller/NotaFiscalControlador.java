/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ItemDaNotaFiscal;
import Model.NotaFiscal;
import View.ViewEntrega;
import View.ViewEntregas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlo
 */
public class NotaFiscalControlador implements ActionListener  {
    ViewEntrega view;
    
    public NotaFiscalControlador(ViewEntrega view){
        this.view = view;
    }
    
    public static ArrayList<NotaFiscal> obterNotas(){
        ArrayList<NotaFiscal> itens = new ArrayList();
        Connection conexao = EmpresaVarejista.obterConexao();
        try {
            Statement statement = conexao.createStatement();
            String query = "SELECT * FROM NotaFiscal";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                itens.add(new NotaFiscal(
                        resultSet.getInt(1), 
                        resultSet.getFloat(2), 
                        (Date)resultSet.getTimestamp(3), 
                        (Date)resultSet.getTimestamp(4), 
                        (Date)resultSet.getTimestamp(5), 
                        resultSet.getInt(6),
                        resultSet.getInt(7))
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmpresaVarejista.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            EmpresaVarejista.fecharConexao();
        }
        return itens;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new ViewEntregas().setVisible(true);
        view.dispose();
    }
    
    
}
