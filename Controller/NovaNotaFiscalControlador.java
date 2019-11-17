/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.ViewEntregas;
import View.ViewNovaEntrega;
import View.ViewNovosItensDeEntrega;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.System.exit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author carlo
 */
public class NovaNotaFiscalControlador implements ActionListener {
    ViewNovaEntrega view;
    int numeroPedido;
    
    public NovaNotaFiscalControlador(ViewNovaEntrega view, int numeroPedido) {
        this.view = view;
        this.numeroPedido = numeroPedido;
    }
    
    static boolean verificaPedido() {
        return true;
    }
    
    private int salvarNotaFiscal(){
        Connection conexao = EmpresaVarejista.obterConexao();
        int numero = -1;
        try {
            PreparedStatement prepStat = conexao.prepareStatement(
                "INSERT INTO NotaFiscal VALUES (?,?,TO_DATE(?, 'DD-MM-YYYY'),TO_DATE(?, 'DD-MM-YYYY'),TO_DATE(?, 'DD-MM-YYYY'), ?, ?)"
            );
            prepStat.setInt(1, view.getNumero());
            prepStat.setFloat(2, view.getValor());
            prepStat.setString(3, view.getPrazoPagamento());
            prepStat.setString(4, view.getPrazoEntrega());
            prepStat.setString(5, view.getDataCadastro());
            prepStat.setInt(6, 0);
            prepStat.setInt(7, numeroPedido);
            prepStat.execute();
            numero = view.getNumero();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO: Problema ao adicionar a nota fiscal, provavel conflito de numeros");
            Logger.getLogger(NovosItensNotaFiscalControlador.class.getName()).log(Level.SEVERE, null, ex);
            //exit(0);
        } finally {
            EmpresaVarejista.fecharConexao();
        }
        return numero;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Salvar":
                int numeroNota = salvarNotaFiscal();
                if(numeroNota!=-1){
                    new ViewNovosItensDeEntrega(numeroPedido, numeroNota).setVisible(true);
                    view.dispose();
                }
                break;
            case "Cancelar":
                new ViewEntregas().setVisible(true);
                view.dispose();
                break;
        }
        
    }
    
}
