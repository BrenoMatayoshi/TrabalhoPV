/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.iftm.brenomatayoshi.trabalhopvbreno.controller;

import br.edu.iftm.brenomatayoshi.trabalhopvbreno.dao.UsuarioDao;
import br.edu.iftm.brenomatayoshi.trabalhopvbreno.model.Usuario;

/**
 *
 * @author breno
 */
public class UsuarioController {
    UsuarioDao usuarioDao = new UsuarioDao();

    public boolean cadastrarUsuario(Usuario usuario) {
        return usuarioDao.cadastrarUsuario(usuario);
    }
    
    public Usuario logar(String login, String senha) {
        return usuarioDao.logar(login, senha);
    }
}
