/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.NotaFiscalControlador;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author carlo
 */
public class NotaFiscalTable extends AbstractTableModel {
    
    private ArrayList<NotaFiscal> notas;
    private String[] colunas = {"Número", "Valor", "Prazo de Pagamento", "Prazo de Entrega", "Data de Entrada", "Estado"};
    
    public NotaFiscalTable() {
        notas = NotaFiscalControlador.obterNotas();
    }

    public NotaFiscal getNotaFiscal(int rowIndex){
        return notas.get(rowIndex);
    }
    
    @Override
    public String getColumnName(int columnIndex){
        return colunas[columnIndex];
    }

    @Override
    public int getRowCount() {
        return notas.size();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        switch(columnIndex){
            case 0:
                return notas.get(rowIndex).getNumero();
            case 1:
                return notas.get(rowIndex).getValor();
            case 2:
                return formatter.format(notas.get(rowIndex).getPrazoPagamento());
            case 3:
                return formatter.format(notas.get(rowIndex).getPrazoEntrega());
            case 4:
                return formatter.format(notas.get(rowIndex).getData());
            case 5:
                return notas.get(rowIndex).getEstado() == 0 ? "Recusada" : "Fechada";
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return null;
    }
    
}
