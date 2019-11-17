/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.ViewEntregas;
import View.ViewNovaEntrega;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.System.exit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author carlo
 */
public class NotasFiscaisControlador implements ActionListener {
    ViewEntregas view;
    
    public NotasFiscaisControlador(ViewEntregas view) {
        this.view = view;
    }
    
    static boolean verificarPedido(int numeroPedido) {
        Connection conexao = EmpresaVarejista.obterConexao();
        try {
            PreparedStatement ps = conexao.prepareStatement(
                "SELECT * FROM Pedido WHERE Numero = ?"
            );
            ps.setInt(1, numeroPedido);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            if(resultSet.getRow()>0){
                return true;
            }            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO: Problema ao verificar numero do pedido");
            exit(0);
        } finally {
            EmpresaVarejista.fecharConexao();
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String numeroPedido = JOptionPane.showInputDialog(view, "Digite o numero do pedido");
        
        if(numeroPedido != null){
            try{
                if(verificarPedido(Integer.parseInt(numeroPedido))){
                    new ViewNovaEntrega(Integer.parseInt(numeroPedido)).setVisible(true);
                    view.dispose();
                } 
                else {
                    JOptionPane.showMessageDialog(null, "Pedido inexistente");
                }
            }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "ERRO: Não foi digitado um numero");
            }
        }
        
    }
    
}
