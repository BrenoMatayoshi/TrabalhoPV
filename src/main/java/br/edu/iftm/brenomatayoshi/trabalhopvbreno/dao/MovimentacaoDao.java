/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.iftm.brenomatayoshi.trabalhopvbreno.dao;

import br.edu.iftm.brenomatayoshi.trabalhopvbreno.model.Categoria;
import br.edu.iftm.brenomatayoshi.trabalhopvbreno.model.Endereco;
import br.edu.iftm.brenomatayoshi.trabalhopvbreno.model.Fornecedor;
import br.edu.iftm.brenomatayoshi.trabalhopvbreno.model.Movimentacao;
import br.edu.iftm.brenomatayoshi.trabalhopvbreno.model.Produto;
import br.edu.iftm.brenomatayoshi.trabalhopvbreno.model.Usuario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author breno
 */
public class MovimentacaoDao {

    private boolean verificarFornecedorExiste(int idFornecedor) {
        String sql = "SELECT COUNT(*) FROM fornecedor WHERE id_fornecedor = ?";
        try (Connection conn = ConexaoDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        try (Connection conn = ConexaoDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        try (Connection conn = ConexaoDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        try (Connection conn = ConexaoDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, movimentacao.getUsuario().getId());
            stmt.setInt(2, movimentacao.getQuantidadeMovimentado());
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
            System.err.println(
                    "Erro: Fornecedor com ID " + movimentacao.getFornecedor().getIdFornecedor() + " não existe.");
            return false;
        }

        if (!verificarProdutoExiste(movimentacao.getProduto().getIdProduto())) {
            System.err.println("Erro: Produto com ID " + movimentacao.getProduto().getIdProduto() + " não existe.");
            return false;
        }

        int idFornecedorHasProduto = verificarFornecedorHasProdutoExiste(
                movimentacao.getFornecedor().getIdFornecedor(),
                movimentacao.getProduto().getIdProduto());

        if (idFornecedorHasProduto == -1) {
            idFornecedorHasProduto = inserirFornecedorHasProduto(
                    movimentacao.getFornecedor().getIdFornecedor(),
                    movimentacao.getProduto().getIdProduto());

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
        try (Connection conn = ConexaoDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Movimentacao> movimentacoes() {
        String sql = "select m.id_movimentacao, m.quantidade_movimentado, m.data, m.tipo, u.id_usuario, u.nome as nome_usuario, u.identificacao, u.telefone as telefone_usuario, u.email as email_usuario, u.senha, e1.id_endereco as id_endereco_1, e1.rua as rua_1, e1.numero as numero_1, e1.bairro as bairro_1, e1.complemento as complemento_1, e1.cidade as cidade_1, e1.estado as estado_1, p.id_produto, p.nome as nome_produto, p.descricao as descricao_produto, p.quantidade_estoque, p.data_cadastro, p.ativo, c.id_categoria, c.nome as nome_categoria, c.descricao as descricao_categoria, f.id_fornecedor, f.nome as nome_fornecedor, f.cnpj, f.telefone as telefone_fornecedor, f.email as email_fornecedor, e2.id_endereco as id_endereco_2, e2.rua as rua_2, e2.numero as numero_2, e2.bairro as bairro_2, e2.complemento as complemento_2, e2.cidade as cidade_2, e2.estado as estado_2 from movimentacao m left join usuario u on u.id_usuario = m.id_usuario left join endereco e1 on e1.id_endereco = u.id_endereco left join fornecedor_has_produto fp on fp.id_fornecedor_has_produto = m.id_fornecedor_has_produto left join produto p on p.id_produto = fp.id_produto left join categoria c on c.id_categoria = p.id_categoria left join fornecedor f on f.id_fornecedor = fp.id_fornecedor  left join endereco e2 on e2.id_endereco = f.id_endereco;";
        
        try (Connection conn = ConexaoDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            List<Movimentacao> movimentacoes = new ArrayList<>();
            while (rs.next()) {
                Movimentacao movimentacao = new Movimentacao(new Usuario(rs.getString("identificacao"), rs.getString("senha"), rs.getString("nome_usuario"), rs.getString("telefone_usuario"), rs.getString("email_usuario"), new Endereco(rs.getString("rua_1"), rs.getString("numero_1"), rs.getString("bairro_1"), rs.getString("complemento_1"), rs.getString("cidade_1"), rs.getString("estado_1"))), rs.getInt("quantidade_movimentado"), rs.getDate("data").toLocalDate(), rs.getString("tipo"), new Produto(new Categoria(rs.getString("nome_categoria"), rs.getString("descricao_categoria")), rs.getString("nome_produto"), rs.getString("descricao_produto"), rs.getInt("quantidade_estoque"), rs.getDate("data_cadastro").toLocalDate(), rs.getBoolean("ativo")), new Fornecedor(rs.getString("nome_fornecedor"), rs.getString("cnpj"), rs.getString("telefone_fornecedor"), rs.getString("email_fornecedor"), new Endereco(rs.getString("rua_2"), rs.getString("numero_2"), rs.getString("bairro_2"), rs.getString("complemento_2"), rs.getString("cidade_2"), rs.getString("estado_2"))));
                movimentacao.setIdMovimentacao(rs.getInt("id_movimentacao"));
                movimentacao.getUsuario().setId(rs.getInt("id_usuario"));
                movimentacao.getUsuario().getEndereco().setId(rs.getInt("id_endereco_1"));
                movimentacao.getProduto().setIdProduto(rs.getInt("id_produto"));
                movimentacao.getFornecedor().setIdFornecedor(rs.getInt("id_fornecedor"));
                movimentacao.getFornecedor().getEndereco().setId(rs.getInt("id_endereco_2"));
                movimentacoes.add(movimentacao);
            }
            return movimentacoes;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
