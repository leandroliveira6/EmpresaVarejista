/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.NotaFiscal;
import Model.NotaFiscalTable;
import View.ViewEntrega;
import View.ViewEntregas;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static java.lang.System.exit;
import java.text.ParseException;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author carlo
 */
public class NotasFiscaisTableControlador implements MouseListener  {
    ViewEntregas view;
    
    public NotasFiscaisTableControlador(ViewEntregas view){
        this.view = view;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if(e.getClickCount() > 1){
            JTable jTable = (JTable) e.getSource();
            NotaFiscalTable nota = (NotaFiscalTable) jTable.getModel();
            int row = jTable.getSelectedRow();
            NotaFiscal notaFiscal = nota.getNotaFiscal(row);
            ViewEntrega viewEntrega = new ViewEntrega(notaFiscal.getNumero());
            viewEntrega.setNumero(notaFiscal.getNumero());
            viewEntrega.setValor(notaFiscal.getValor());
            try {
                viewEntrega.setDataCadastro(notaFiscal.getData().toString());
                viewEntrega.setPrazoEntrega(notaFiscal.getPrazoEntrega().toString());
                viewEntrega.setPrazoPagamento(notaFiscal.getPrazoPagamento().toString());
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "ERRO: Problema ao converter a data em string");
                exit(0);
            }
            
            viewEntrega.setVisible(true);
            view.dispose();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
