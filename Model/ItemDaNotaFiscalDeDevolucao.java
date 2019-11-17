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
public class ItemDaNotaFiscalDeDevolucao {
    private int numero;
    private int quantidade;
    private float valor;
    private int numeroNotaDevolucao;
    private int numeroItemPedido;

    public ItemDaNotaFiscalDeDevolucao(int numero, float valor, int quantidade, int numeroNotaDevolucao, int numeroItemPedido) {
        this.numero = numero;
        this.quantidade = quantidade;
        this.valor = valor;
        this.numeroNotaDevolucao = numeroNotaDevolucao;
        this.numeroItemPedido = numeroItemPedido;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
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

    public int getNumeroNotaDevolucao() {
        return numeroNotaDevolucao;
    }

    public void setNumeroNotaDevolucao(int notaDevolucao) {
        this.numeroNotaDevolucao = notaDevolucao;
    }

    public int getNumeroItemPedido() {
        return numeroItemPedido;
    }

    public void setNumeroItemPedido(int numeroItemPedido) {
        this.numeroItemPedido = numeroItemPedido;
    }
}
