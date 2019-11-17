/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ItemDaNotaFiscal;
import Model.ItemDaNotaFiscalDeDevolucao;
import Model.ItemDaNotaFiscalTable;
import Model.NotaFiscal;
import Model.NotaFiscalDeDevolucao;
import View.ViewEntrega;
import View.ViewEntregas;
import View.ViewNovosItensDeEntrega;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.System.exit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Leandro
 */
public class NovosItensNotaFiscalControlador implements ActionListener {
    private ViewNovosItensDeEntrega view;
    private ItemDaNotaFiscalTable tabela;
    private int numeroNotaFiscal;
    private int numeroPedido;
    private int estadoGeral; // 1 por default, 0 quando a entrega será recusada
    private NotaFiscalDeDevolucao notaDevolucao;
    private ArrayList<ItemDaNotaFiscalDeDevolucao> itensDevolucao;

    public NovosItensNotaFiscalControlador(ViewNovosItensDeEntrega view, ItemDaNotaFiscalTable tabela, int numeroPedido, int numeroNotaFiscal) {
        this.view = view;
        this.tabela = tabela;
        this.numeroNotaFiscal = numeroNotaFiscal;
        this.numeroPedido = numeroPedido;
        estadoGeral = 1; // Tudo ok até que se prove o contrario
        notaDevolucao = null;
        itensDevolucao = new ArrayList();
    }
    
    private void atualizarValorNota(Connection conexao, int numeroNota, float valor) throws SQLException{
        PreparedStatement prepStat = conexao.prepareStatement(
            "UPDATE NotaFiscal SET Valor=Valor+? WHERE Numero=?"
        );
        prepStat.setFloat(1, valor);
        prepStat.setInt(2, numeroNota);
        prepStat.execute();
    }
    
    private ItemDaNotaFiscal salvarItemDaNotaFiscal(){
        ItemDaNotaFiscal itemNota = null;
        Connection conexao = EmpresaVarejista.obterConexao();
        PreparedStatement prepStat;
        try {
            prepStat = conexao.prepareStatement(
                "INSERT INTO ITEMDANOTAFISCAL VALUES (?,?,?,?,?)"
            );
            prepStat.setLong(1, view.getNumero());
            prepStat.setFloat(2, view.getValor());
            prepStat.setInt(3, view.getQuantidade());
            prepStat.setInt(4, numeroNotaFiscal);
            prepStat.setInt(5, view.getNumero()); // numero do item nota = numero do item pedido
            prepStat.execute();
            
            itemNota = new ItemDaNotaFiscal(view.getNumero(), view.getValor(), view.getQuantidade(), numeroNotaFiscal, view.getNumero());
            tabela.adicionarItemNotaFiscal(itemNota);
            atualizarValorNota(conexao, numeroNotaFiscal, view.getValor());
        } catch (SQLException ex) {
            Logger.getLogger(NovosItensNotaFiscalControlador.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "ERRO: Problema na inserção do item, provavel conflito de numeros");
            estadoGeral = 0;
        } finally {
            EmpresaVarejista.fecharConexao();
        }
        return itemNota;
    }
    
    private NotaFiscalDeDevolucao getNotaDevolucao(){
        if(notaDevolucao == null){
            notaDevolucao = new NotaFiscalDeDevolucao(numeroNotaFiscal, 0, Date.from(Instant.now()), numeroPedido);
        }
        return notaDevolucao;
    }
        
    private void verificarItemDaNotaFiscal(ItemDaNotaFiscal itemNota){
        Connection conexao = EmpresaVarejista.obterConexao();
        try {
            PreparedStatement ps = conexao.prepareStatement(
                "SELECT * FROM ItemDoPedido WHERE Numero = ?"
            );
            ps.setInt(1, itemNota.getNumero());
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            if(resultSet.getRow()>0){
                int numeroItemPedido = resultSet.getInt(1);
                float valorItemPedido = resultSet.getFloat(2);
                int quantidadeItemPedido = resultSet.getInt(3);
                System.out.println("Numero do pedido? "+numeroItemPedido);
                boolean adicionarItemDevolucao = false; // true quando o item de devolução é necessario
                ItemDaNotaFiscalDeDevolucao itemNotaDevolucao = new ItemDaNotaFiscalDeDevolucao(
                        numeroItemPedido,
                        itemNota.getValor(),
                        itemNota.getQuantidade(), 
                        getNotaDevolucao().getNumero(),
                        numeroItemPedido
                );
                
                
                if(itemNota.getQuantidade() > quantidadeItemPedido){ // quantidade a maior
                    estadoGeral = 0;
                } 
                else if(itemNota.getQuantidade() < quantidadeItemPedido){ // a menor
                    itemNotaDevolucao.setQuantidade(quantidadeItemPedido-itemNota.getQuantidade());
                    adicionarItemDevolucao = true;
                }
                
                if(itemNota.getValor() > valorItemPedido){ // valor a maior
                    itemNotaDevolucao.setValor(itemNota.getValor()-valorItemPedido);
                    adicionarItemDevolucao = true;
                } 
                else if(itemNota.getValor() < valorItemPedido){ // a menor
                    estadoGeral = 0;
                }
                
                if(estadoGeral == 1 && adicionarItemDevolucao){ // só adiciona notas de devolução se entrega não será recusada
                    itensDevolucao.add(itemNotaDevolucao);
                }
            } else { // Item cadastrado não corresponde a um item de pedido
                estadoGeral = 0;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO: Problema na verificação do item da nota fiscal");
            Logger.getLogger(NovosItensNotaFiscalControlador.class.getName()).log(Level.SEVERE, null, ex);
            exit(0);
        } finally {
            EmpresaVarejista.fecharConexao();
        }
    }
    
    private void salvarNotaDeDevolucao(){
        Connection conexao = EmpresaVarejista.obterConexao();
        try {
            PreparedStatement prepStat = conexao.prepareStatement(
                "INSERT INTO NotaFiscalDeDevolucao VALUES (?,?,TO_DATE(?,'YYYY-MM-DD'),?)"
            );
            System.out.println(notaDevolucao.getNumeroPedido());
            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
            prepStat.setInt(1, notaDevolucao.getNumero());
            prepStat.setFloat(2, notaDevolucao.getValor());
            prepStat.setString(3, formatter.format(notaDevolucao.getData()));
            prepStat.setInt(4, notaDevolucao.getNumeroPedido());
            prepStat.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO: Problema ao adicionar uma nova nota fiscal de devolução");
            Logger.getLogger(NovosItensNotaFiscalControlador.class.getName()).log(Level.SEVERE, null, ex);
            exit(0);
        } finally {
            EmpresaVarejista.fecharConexao();
        }
    }
    
    // As notas começam com valor zero e vão aumentando conforme itens são adicionados
    private void atualizarValorNotaDeDevolucao(Connection conexao, int numeroNotaDevolucao, float valor) throws SQLException{
        PreparedStatement prepStat = conexao.prepareStatement(
            "UPDATE NotaFiscalDeDevolucao SET Valor=Valor+? WHERE Numero=?"
        );
        prepStat.setFloat(1, valor);
        prepStat.setInt(2, numeroNotaDevolucao);
        prepStat.execute();
    }
    
    private void salvarItensNotaDeDevolucao(){
        Connection conexao = EmpresaVarejista.obterConexao();
        try {
            for(ItemDaNotaFiscalDeDevolucao item : itensDevolucao){
                PreparedStatement prepStat = conexao.prepareStatement(
                    "INSERT INTO ItemDaNotaFiscalDeDevolucao VALUES (?,?,?,?,?)"
                );

                prepStat.setInt(1, item.getNumero());
                prepStat.setFloat(2, item.getValor());
                prepStat.setInt(3, item.getQuantidade());
                prepStat.setInt(4, item.getNumeroNotaDevolucao());
                prepStat.setInt(5, item.getNumeroItemPedido());
                prepStat.execute();
                atualizarValorNotaDeDevolucao(conexao, item.getNumeroNotaDevolucao(), item.getValor()*item.getQuantidade());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO: Problema ao salvar itens de nota fiscal de devolução ou atualizar valor da nota fiscal");
            Logger.getLogger(NovosItensNotaFiscalControlador.class.getName()).log(Level.SEVERE, null, ex);
            exit(0);
        } finally {
            EmpresaVarejista.fecharConexao();
        
        }
    }
    
    // Uma nota fiscal com estado = 1 é uma nota fiscal fechada
    private void fecharNotaFiscal(){
        Connection conexao = EmpresaVarejista.obterConexao();
        try {
            PreparedStatement prepStat = conexao.prepareStatement(
                "UPDATE NotaFiscal SET Estado=1 WHERE Numero=?"
            );
            prepStat.setInt(1, numeroNotaFiscal);
            prepStat.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO: Problema ao fechar nota fiscal");
            Logger.getLogger(NovosItensNotaFiscalControlador.class.getName()).log(Level.SEVERE, null, ex);
            exit(0);
        } finally {
            EmpresaVarejista.fecharConexao();
        }
    }
    
    // Um pedido com estado = 1 é um pedido entregue
    private void fecharPedido(){
        Connection conexao = EmpresaVarejista.obterConexao();
        try {
            PreparedStatement prepStat = conexao.prepareStatement(
                "UPDATE Pedido SET Estado=1 WHERE Numero=?"
            );
            prepStat.setInt(1, numeroNotaFiscal);
            prepStat.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO: Problema ao fechar pedido");
            Logger.getLogger(NovosItensNotaFiscalControlador.class.getName()).log(Level.SEVERE, null, ex);
            exit(0);
        } finally {
            EmpresaVarejista.fecharConexao();
        }
    }
    
    private void desfazerAlteracoes(){
        Connection conexao = EmpresaVarejista.obterConexao();
        int contador = 0;
        try {
            PreparedStatement prepStat = conexao.prepareStatement("DELETE FROM NotaFiscal WHERE Numero=?");
            prepStat.setInt(1, numeroNotaFiscal);
            prepStat.execute();
            if(itensDevolucao != null && itensDevolucao.size()>0){
                prepStat = conexao.prepareStatement("DELETE FROM NotaFiscalDeDevolucao WHERE Numero=?");
                prepStat.setInt(1, numeroNotaFiscal);
                prepStat.execute();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO: Problema ao remover alterações");
            Logger.getLogger(NovosItensNotaFiscalControlador.class.getName()).log(Level.SEVERE, null, ex);
            exit(0);
        } finally {
            EmpresaVarejista.fecharConexao();
        }
    }
    
    private void inserirValoresViewEntrega(ViewEntrega viewEntrega){
        Connection conexao = EmpresaVarejista.obterConexao();
        try {
            PreparedStatement ps = conexao.prepareStatement(
                "SELECT * FROM NotaFiscal WHERE Numero = ?"
            );
            ps.setInt(1, numeroNotaFiscal);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            viewEntrega.setNumero(resultSet.getInt(1));
            viewEntrega.setValor(resultSet.getFloat(2));
            viewEntrega.setDataCadastro(resultSet.getTimestamp(3).toString());
            viewEntrega.setPrazoEntrega(resultSet.getTimestamp(4).toString());
            viewEntrega.setPrazoPagamento(resultSet.getTimestamp(5).toString());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO: Problema ao obter uma nota fiscal");
            exit(0);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "ERRO: Problema ao converter a data em string");
        } finally {
            EmpresaVarejista.fecharConexao();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Salvar":
                ItemDaNotaFiscal itemNota = salvarItemDaNotaFiscal();
                if(itemNota!=null){
                    verificarItemDaNotaFiscal(itemNota);
                }
                break;
            case "Finalizar":
                if(estadoGeral == 0){
                    JOptionPane.showMessageDialog(null, "Existem problemas no cadastro da entrega e todas as alterações serão desfeitas");
                    desfazerAlteracoes();
                    new ViewEntregas().setVisible(true);                
                }
                else{
                    if(itensDevolucao != null && itensDevolucao.size()>0) {
                        salvarNotaDeDevolucao();
                        salvarItensNotaDeDevolucao();
                        JOptionPane.showMessageDialog(null, "Aperte OK para imprimir uma nota fiscal de devolução e terminar a entrega");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Entrega finalizada");
                    }
                    // A nota fiscal e o pedido sempre são finalizados caso se vá aceitar a entrega
                    fecharNotaFiscal();
                    fecharPedido();
                    
                    // Preparando os dados da nota para exibição
                    ViewEntrega viewEntrega = new ViewEntrega(numeroNotaFiscal);
                    inserirValoresViewEntrega(viewEntrega);
                    viewEntrega.setVisible(true);
                }
                view.dispose();
                break;
        }
        
    }
}
