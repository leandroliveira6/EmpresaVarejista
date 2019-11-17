/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author carlo
 */
public class ItemDaNotaFiscal {
    private int numero;
    private int quantidade;
    private float valor;
    private int numeroNota;
    private int numeroItemPedido;

    public ItemDaNotaFiscal(int numero, float valor, int quantidade, int numeroNota, int numeroItemPedido) {
        this.numero = numero;
        this.quantidade = quantidade;
        this.valor = valor;
        this.numeroItemPedido = numeroItemPedido;
        this.numeroNota = numeroNota;
    }

    public int getNumeroNota() {
        return numeroNota;
    }

    public void setNumeroNota(int numeroNota) {
        this.numeroNota = numeroNota;
    }

    public int getNumeroItemPedido() {
        return numeroItemPedido;
    }

    public void setNumeroItemPedido(int numeroItemPedido) {
        this.numeroItemPedido = numeroItemPedido;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    
}
