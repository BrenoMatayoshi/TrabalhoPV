/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.iftm.brenomatayoshi.trabalhopvbreno.controller;

import br.edu.iftm.brenomatayoshi.trabalhopvbreno.dao.MovimentacaoDao;
import br.edu.iftm.brenomatayoshi.trabalhopvbreno.model.Movimentacao;

/**
 *
 * @author breno
 */
public class MovimentacaoController {
    private MovimentacaoDao movimentacaoDao = new MovimentacaoDao();
    
    public boolean cadastrarMovimentacao(Movimentacao movimentacao) {
        return movimentacaoDao.cadastrarMovimentacao(movimentacao);
    }
    
    public boolean excluirFornecedorHasProduto(int id) {
        return movimentacaoDao.excluirFornecedorHasProduto(id);
    }
}
