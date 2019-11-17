/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author carlo
 */
public class Pedido {
    private int numero;
    private float valor;
    private Date prazoPagamento;
    private Date prazoEntrega;
    private Date data;
    private int estado;

    public Pedido(int numero, float valor, Date prazoPagamento, Date prazoEntrega, Date data, int estado) {
        this.numero = numero;
        this.valor = valor;
        this.prazoPagamento = prazoPagamento;
        this.prazoEntrega = prazoEntrega;
        this.data = data;
        this.estado = estado;
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

    public Date getPrazoPagamento() {
        return prazoPagamento;
    }

    public void setPrazoPagamento(Date prazoPagamento) {
        this.prazoPagamento = prazoPagamento;
    }

    public Date getPrazoEntrega() {
        return prazoEntrega;
    }

    public void setPrazoEntrega(Date prazoEntrega) {
        this.prazoEntrega = prazoEntrega;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
        
}
