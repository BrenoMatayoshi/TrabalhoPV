/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.iftm.brenomatayoshi.trabalhopvbreno.controller;

import br.edu.iftm.brenomatayoshi.trabalhopvbreno.dao.ProdutoDao;
import br.edu.iftm.brenomatayoshi.trabalhopvbreno.model.Produto;
import java.util.List;

/**
 *
 * @author breno
 */
public class ProdutoController {
    private ProdutoDao produtoDao = new ProdutoDao();
    
    public boolean cadastrarProduto(Produto produto) {
        return produtoDao.cadastrarProduto(produto);
    }
    
    public List<Produto> produtos() {
        return produtoDao.produtos();
    }
    
    public Produto buscarProdutoPorId(int id) {
        return produtoDao.buscarProdutoPorId(id);
    }
    
    public boolean exluirProduto(Produto produto) {
        return produtoDao.excluirProduto(produto);
    }
    
    public boolean alterarProduto(Produto produto) {
        return produtoDao.alterarProduto(produto);
    }
}
