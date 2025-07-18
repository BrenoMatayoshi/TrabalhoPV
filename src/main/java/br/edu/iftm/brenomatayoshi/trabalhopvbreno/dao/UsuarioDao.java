/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.iftm.brenomatayoshi.trabalhopvbreno.dao;

import br.edu.iftm.brenomatayoshi.trabalhopvbreno.controller.EnderecoController;
import br.edu.iftm.brenomatayoshi.trabalhopvbreno.model.Endereco;
import br.edu.iftm.brenomatayoshi.trabalhopvbreno.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author breno
 */
public class UsuarioDao {
    EnderecoController enderecoController = new EnderecoController();

    public boolean cadastrarUsuario(Usuario usuario) {
        int idEndereco = enderecoController.cadastrarEndereco(usuario.getEndereco());
        if (idEndereco == -1) {
            return false;
        }
        String sql = "insert into usuario (nome, identificacao, telefone, email, id_endereco, senha) VALUES (?, ?, ?, ?, ?, ?);";
        try (Connection conn = ConexaoDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getIdentificacao());
            stmt.setString(3, usuario.getTelefone());
            stmt.setString(4, usuario.getEmail());
            stmt.setInt(5, idEndereco);
            stmt.setString(6, usuario.getSenha());
            return stmt.executeUpdate() == 1;
            
        } catch (SQLException e) {
            e.printStackTrace();
            enderecoController.excluirEndereco(idEndereco);
            return false;
        }
    }
    
    public Usuario logar(String login, String senha) {
        String sql = "select u.id_usuario, u.nome, u.identificacao, u.telefone, u.email, u.senha, e.id_endereco, e.endereco, e.cidade, e.estado from usuario u left join endereco e on e.id_endereco = u.id_usuario where (u.identificacao = ? or u.email = ?) and u.senha = ?;";
        Usuario usuario = null;
        try (Connection conn = ConexaoDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            stmt.setString(2, login);
            stmt.setString(3, senha);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id_usuario");
                String nome = rs.getString("nome");
                String identificacao = rs.getString("identificacao");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");
                String senhaBanco = rs.getString("senha");
                int idEndereco = rs.getInt("id_endereco");
                String endereco = rs.getString("endereco");
                String cidade = rs.getString("cidade");
                String estado = rs.getString("estado");
                usuario = new Usuario(nome, identificacao, telefone, email, senhaBanco, new Endereco(endereco, cidade, estado));
                usuario.setId(id);
                usuario.getEndereco().setId(idEndereco);
                return usuario;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }
}
