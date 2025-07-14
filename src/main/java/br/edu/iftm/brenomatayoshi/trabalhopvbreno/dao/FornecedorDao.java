/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.iftm.brenomatayoshi.trabalhopvbreno.dao;

import br.edu.iftm.brenomatayoshi.trabalhopvbreno.controller.EnderecoController;
import br.edu.iftm.brenomatayoshi.trabalhopvbreno.model.Endereco;
import br.edu.iftm.brenomatayoshi.trabalhopvbreno.model.Fornecedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author breno
 */
public class FornecedorDao {
    private EnderecoController enderecoController = new EnderecoController();
    
    public boolean cadastrarFornecedor(Fornecedor fornecedor) {
        int idEndereco = enderecoController.cadastrarEndereco(fornecedor.getEndereco());
        if (idEndereco == -1) {
            return false;
        }
        String sql = "insert into fornecedor (nome, cnpj, telefone, email, id_endereco) value (?, ?, ?, ?, ?);";
        try (Connection conn = ConexaoDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fornecedor.getNome());
            stmt.setString(2, fornecedor.getCnpj());
            stmt.setString(3, fornecedor.getTelefone());
            stmt.setString(4, fornecedor.getEmail());
            stmt.setInt(5, idEndereco);
            return stmt.executeUpdate() == 1;
            
        } catch (SQLException e) {
            e.printStackTrace();
            enderecoController.excluirEndereco(idEndereco);
            return false;
        }
    }
    
    public List<Fornecedor> fornecedores() {
        String sql = "select f.id_fornecedor, f.nome, f.cnpj, f.telefone, f.email, e.id_endereco, e.rua, e.numero, e.bairro, e.complemento, e.cidade, e.estado from fornecedor f left join endereco e on f.id_endereco = e.id_endereco;";
        try (Connection conn = ConexaoDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            List<Fornecedor> fornecedores = new ArrayList<>();
            while (rs.next()) {
                Fornecedor fornecedor = new Fornecedor(rs.getString("nome"), rs.getString("cnpj"), rs.getString("telefone"), rs.getString("email"), new Endereco(rs.getString("rua"), rs.getString("numero"), rs.getString("bairro"), rs.getString("complemento"), rs.getString("cidade"), rs.getString("estado")));
                fornecedor.setIdFornecedor(rs.getInt("id_fornecedor"));
                fornecedor.getEndereco().setId(rs.getInt("id_endereco"));
                fornecedores.add(fornecedor);
            }
            return fornecedores;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean editarFornecedor(Fornecedor fornecedor) {
        int idEndereco = enderecoController.editarEndereco(fornecedor.getEndereco());
        if (idEndereco == -1) {
            return false;
        }
        String sql = "update fornecedor set nome = ?, cnpj = ?, telefone = ?, email = ?, id_endereco = ? where id_fornecedor = ?;";
        try (Connection conn = ConexaoDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fornecedor.getNome());
            stmt.setString(2, fornecedor.getCnpj());
            stmt.setString(3, fornecedor.getTelefone());
            stmt.setString(4, fornecedor.getEmail());
            stmt.setInt(5, idEndereco);
            stmt.setInt(6, fornecedor.getIdFornecedor());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            enderecoController.excluirEndereco(idEndereco);
            return false;
        }
    }

    public Fornecedor fornecedor(int idFornecedor) {
        String sql = "select f.id_fornecedor, f.nome, f.cnpj, f.telefone, f.email, e.id_endereco, e.rua, e.numero, e.bairro, e.complemento, e.cidade, e.estado from fornecedor f left join endereco e on f.id_endereco = e.id_endereco where f.id_fornecedor = ?;";
        try (Connection conn = ConexaoDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFornecedor);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Fornecedor fornecedor = new Fornecedor(rs.getString("nome"), rs.getString("cnpj"), rs.getString("telefone"), rs.getString("email"), new Endereco(rs.getString("rua"), rs.getString("numero"), rs.getString("bairro"), rs.getString("complemento"), rs.getString("cidade"), rs.getString("estado")));
                    fornecedor.setIdFornecedor(rs.getInt("id_fornecedor"));
                    fornecedor.getEndereco().setId(rs.getInt("id_endereco"));
                    return fornecedor;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
