/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author carlo
 */
public class NotaFiscalDeDevolucao {
    private int numero;
    private float valor;
    private Date data;
    private int numeroPedido;

    public NotaFiscalDeDevolucao(int numero, float valor, Date data, int numeroPedido) {
        this.numero = numero;
        this.valor = valor;
        this.data = data;
        this.numeroPedido = numeroPedido;
    }

    public NotaFiscalDeDevolucao(int numeroNotaFiscal, int i, Date from) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }
    
}
