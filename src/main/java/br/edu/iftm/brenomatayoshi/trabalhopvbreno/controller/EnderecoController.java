package br.edu.iftm.brenomatayoshi.trabalhopvbreno.controller;

import br.edu.iftm.brenomatayoshi.trabalhopvbreno.dao.EnderecoDao;
import br.edu.iftm.brenomatayoshi.trabalhopvbreno.model.Endereco;

public class EnderecoController {
    EnderecoDao enderecoDao = new EnderecoDao();

    public int cadastrarEndereco(Endereco endereco) {
        return enderecoDao.cadastrarEndereco(endereco);
    }
    
    public boolean excluirEndereco(int idEndereco) {
        return enderecoDao.excluirEndereco(idEndereco);
    }
}
