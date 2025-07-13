/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.iftm.brenomatayoshi.trabalhopvbreno.dao;

import br.edu.iftm.brenomatayoshi.trabalhopvbreno.model.Categoria;
import br.edu.iftm.brenomatayoshi.trabalhopvbreno.model.Produto;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author breno
 */
public class ProdutoDao {

    public boolean cadastrarProduto(Produto produto) {
        String sql = "insert into produto (id_categoria, nome, descricao, quantidade_estoque, data_cadastro, ativo) values (?, ?, ?, ?, ?, ?);";
        try (Connection conn = ConexaoDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, produto.getCategoria().getIdCategoria());
            stmt.setString(2, produto.getNome());
            stmt.setString(3, produto.getDescricao());
            stmt.setInt(4, produto.getQuantidadeEstoque());
            stmt.setDate(5, Date.valueOf(produto.getDataCadastro()));
            stmt.setBoolean(6, produto.isAtivo());
            return stmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Produto> produtos() {
        String sql = "select p.id_produto, p.nome, p.descricao, p.quantidade_estoque, p.data_cadastro, p.ativo, c.id_categoria, c.nome as nome_categoria, c.descricao as descricao_categoria from produto p left join categoria c on c.id_categoria = p.id_categoria;";
        try (Connection conn = ConexaoDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            List<Produto> produtos = new ArrayList<>();
            while (rs.next()) {
                Produto produto = new Produto(new Categoria(rs.getString("nome_categoria"), rs.getString("descricao_categoria")), rs.getString("nome"), rs.getString("descricao"), rs.getInt("quantidade_estoque"), rs.getDate("data_cadastro").toLocalDate(), rs.getBoolean("ativo"));
                produto.setIdProduto(rs.getInt("id_produto"));
                produto.getCategoria().setIdCategoria(rs.getInt("id_categoria"));
                produtos.add(produto);
            }
            return produtos;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Produto buscarProdutoPorId(int id) {
        String sql = "select p.id_produto, p.nome, p.descricao, p.quantidade_estoque, p.data_cadastro, p.ativo, c.id_categoria, c.nome as nome_categoria, c.descricao as descricao_categoria from produto p left join categoria c on c.id_categoria = p.id_categoria where p.id_produto = ?";
        try (Connection conn = ConexaoDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Produto produto = new Produto(new Categoria(rs.getString("nome_categoria"), rs.getString("descricao_categoria")), rs.getString("nome"), rs.getString("descricao"), rs.getInt("quantidade_estoque"), rs.getDate("data_cadastro").toLocalDate(), rs.getBoolean("ativo"));
                produto.setIdProduto(rs.getInt("id_produto"));
                produto.getCategoria().setIdCategoria(rs.getInt("id_categoria"));
                return produto;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean excluirProduto(Produto produto) {
        String sql = "update produto set ativo = 0 where id_produto = ?;";
        try (Connection conn = ConexaoDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, produto.getIdProduto());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean alterarProduto(Produto produto) {
        String sql = "update produto set id_categoria = ?, nome = ?, descricao = ?, quantidade_estoque = ?, ativo = ? where id_produto = ?;";
        try (Connection conn = ConexaoDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, produto.getCategoria().getIdCategoria());
            stmt.setString(2, produto.getNome());
            stmt.setString(3, produto.getDescricao());
            stmt.setInt(4, produto.getQuantidadeEstoque());
            stmt.setBoolean(5, produto.isAtivo());
            stmt.setInt(6, produto.getIdProduto());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
