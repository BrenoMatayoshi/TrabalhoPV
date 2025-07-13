/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.iftm.brenomatayoshi.trabalhopvbreno.dao;

import br.edu.iftm.brenomatayoshi.trabalhopvbreno.model.Categoria;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author breno
 */
public class CategoriaDao {
    public boolean cadastrarCategoria(Categoria categoria) {
        String sql = "insert into categoria (nome, descricao) values (?, ?);";
        try (Connection conn = ConexaoDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getDescricao());
            return stmt.executeUpdate() == 1;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Categoria> categorias() {
        String sql = "select * from categoria";
        try (Connection conn = ConexaoDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            List<Categoria> categorias = new ArrayList<>();
            while (rs.next()) {
                Categoria categoria = new Categoria(rs.getString("nome"), rs.getString("descricao"));
                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categorias.add(categoria);
            }
            return categorias;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
