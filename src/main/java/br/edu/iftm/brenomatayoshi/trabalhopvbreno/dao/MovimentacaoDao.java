/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.iftm.brenomatayoshi.trabalhopvbreno.dao;

import br.edu.iftm.brenomatayoshi.trabalhopvbreno.model.Movimentacao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author breno
 */
public class MovimentacaoDao {

    private boolean verificarFornecedorExiste(int idFornecedor) {
        String sql = "SELECT COUNT(*) FROM fornecedor WHERE id_fornecedor = ?";
        try (Connection conn = ConexaoDao.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFornecedor);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean verificarProdutoExiste(int idProduto) {
        String sql = "SELECT COUNT(*) FROM produto WHERE id_produto = ?";
        try (Connection conn = ConexaoDao.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProduto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int verificarFornecedorHasProdutoExiste(int idFornecedor, int idProduto) {
        String sql = "SELECT id_fornecedor_has_produto FROM fornecedor_has_produto WHERE id_fornecedor = ? AND id_produto = ?";
        try (Connection conn = ConexaoDao.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFornecedor);
            stmt.setInt(2, idProduto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_fornecedor_has_produto");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int inserirFornecedorHasProduto(int idFornecedor, int idProduto) {
        String sql = "INSERT INTO fornecedor_has_produto (id_fornecedor, id_produto) VALUES (?, ?)";
        try (Connection conn = ConexaoDao.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, idFornecedor);
            stmt.setInt(2, idProduto);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private boolean inserirMovimentacao(Movimentacao movimentacao, int idFornecedorHasProduto) {
        String sql = "INSERT INTO movimentacao (id_usuario, quantidade_movimentado, data, tipo, id_fornecedor_has_produto) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoDao.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, movimentacao.getUsuario().getId());
            stmt.setInt(2, movimentacao.getProduto().getQuantidadeEstoque());
            stmt.setDate(3, Date.valueOf(movimentacao.getData()));
            stmt.setString(4, movimentacao.getTipo());
            stmt.setInt(5, idFornecedorHasProduto);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean cadastrarMovimentacao(Movimentacao movimentacao) {
        if (!verificarFornecedorExiste(movimentacao.getFornecedor().getIdFornecedor())) {
            System.err.println("Erro: Fornecedor com ID " + movimentacao.getFornecedor().getIdFornecedor() + " não existe.");
            return false;
        }

        if (!verificarProdutoExiste(movimentacao.getProduto().getIdProduto())) {
            System.err.println("Erro: Produto com ID " + movimentacao.getProduto().getIdProduto() + " não existe.");
            return false;
        }

        int idFornecedorHasProduto = verificarFornecedorHasProdutoExiste(
            movimentacao.getFornecedor().getIdFornecedor(), 
            movimentacao.getProduto().getIdProduto()
        );

        if (idFornecedorHasProduto == -1) {
            idFornecedorHasProduto = inserirFornecedorHasProduto(
                movimentacao.getFornecedor().getIdFornecedor(), 
                movimentacao.getProduto().getIdProduto()
            );
            
            if (idFornecedorHasProduto == -1) {
                System.err.println("Erro: Falha ao criar relação fornecedor_has_produto.");
                return false;
            }
        }

        boolean sucesso = inserirMovimentacao(movimentacao, idFornecedorHasProduto);
        
        if (!sucesso) {
            System.err.println("Erro: Falha ao inserir movimentação.");
        }
        
        return sucesso;
    }

    public boolean excluirFornecedorHasProduto(int id) {
        String sql = "DELETE FROM fornecedor_has_produto WHERE id_fornecedor_has_produto = ?";
        try (Connection conn = ConexaoDao.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
