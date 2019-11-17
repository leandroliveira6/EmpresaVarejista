/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ItemDaNotaFiscal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlo
 */
public class ItemNotaFiscalControlador implements ActionListener  {
    
    public static ArrayList<ItemDaNotaFiscal> obterItens(int numeroNotaFiscal){
        ArrayList<ItemDaNotaFiscal> itens = new ArrayList();
        Connection conexao = EmpresaVarejista.obterConexao();
        try {
            Statement statement = conexao.createStatement();
            String query = "SELECT * FROM ItemDaNotaFiscal WHERE NumeroNotaFiscal="+numeroNotaFiscal;
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                itens.add(new ItemDaNotaFiscal(resultSet.getInt(1), resultSet.getFloat(2), resultSet.getInt(3), resultSet.getInt(4), resultSet.getInt(5)));
            }
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(EmpresaVarejista.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            EmpresaVarejista.fecharConexao();
        }
        return itens;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
