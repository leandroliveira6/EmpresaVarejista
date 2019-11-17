/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.ItemNotaFiscalControlador;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author carlo
 */
public class ItemDaNotaFiscalTable extends AbstractTableModel {
    private ArrayList<ItemDaNotaFiscal> itensNota;
    private String[] colunas = {"Número", "Quantidade", "Valor"};
    private int numeroNotaFiscal;
    
    public ItemDaNotaFiscalTable(int numeroNotaFiscal) {
        this.numeroNotaFiscal = numeroNotaFiscal;
        itensNota = ItemNotaFiscalControlador.obterItens(numeroNotaFiscal);
    }
    
    public void adicionarItemNotaFiscal(ItemDaNotaFiscal itemNotaFiscal){
        itensNota.add(itemNotaFiscal);
        this.fireTableDataChanged();
    }
    
    @Override
    public String getColumnName(int columnIndex){
        return colunas[columnIndex];
    }

    @Override
    public int getRowCount() {
        return itensNota.size();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return itensNota.get(rowIndex).getNumero();
            case 1:
                return itensNota.get(rowIndex).getQuantidade();
            case 2:
                return itensNota.get(rowIndex).getValor();                
        }
        return null;
    }
    
}
