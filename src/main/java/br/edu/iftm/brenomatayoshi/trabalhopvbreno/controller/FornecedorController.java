/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.iftm.brenomatayoshi.trabalhopvbreno.controller;

import br.edu.iftm.brenomatayoshi.trabalhopvbreno.dao.FornecedorDao;
import br.edu.iftm.brenomatayoshi.trabalhopvbreno.model.Fornecedor;
import java.util.List;

/**
 *
 * @author breno
 */
public class FornecedorController {
    private FornecedorDao fornecedorDao = new FornecedorDao();
    
    public boolean cadastrarFornecedor(Fornecedor fornecedor) {
        return fornecedorDao.cadastrarFornecedor(fornecedor);
    }
    
    public List<Fornecedor> fornecedores() {
        return fornecedorDao.fornecedores();
    }
}
