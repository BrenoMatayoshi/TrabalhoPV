/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.iftm.brenomatayoshi.trabalhopvbreno.controller;

import br.edu.iftm.brenomatayoshi.trabalhopvbreno.dao.CategoriaDao;
import br.edu.iftm.brenomatayoshi.trabalhopvbreno.model.Categoria;
import java.util.List;

/**
 *
 * @author breno
 */
public class CategoriaController {
    CategoriaDao categoriaDao = new CategoriaDao();
    
    public boolean cadastrarCategoria(Categoria categoria) {
        return categoriaDao.cadastrarCategoria(categoria);
    }
    
    public List<Categoria> categorias() {
        return categoriaDao.categorias();
    }
}
