/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.iftm.brenomatayoshi.trabalhopvbreno.dao;

import br.edu.iftm.brenomatayoshi.trabalhopvbreno.model.Endereco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author breno
 */
public class EnderecoDao {

    public int cadastrarEndereco(Endereco endereco) {
        String sql = "insert into endereco (rua, numero, bairro, complemento, cidade, estado) VALUES (?, ?, ?, ?, ?, ?);";
        try (Connection conn = ConexaoDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, endereco.getRua());
            stmt.setString(2, endereco.getNumero());
            stmt.setString(3, endereco.getBairro());
            stmt.setString(4, endereco.getComplemento());
            stmt.setString(5, endereco.getCidade());
            stmt.setString(6, endereco.getEstado());
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

    public boolean excluirEndereco(int idEndereco) {
        String sql = "DELETE FROM endereco WHERE id_endereco = ?";
        try (Connection conn = ConexaoDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEndereco);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
