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
        String sql = "insert into endereco (endereco, cidade, estado) VALUES (?, ?, ?);";
        try (Connection conn = ConexaoDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, endereco.getEndereco());
            stmt.setString(2, endereco.getCidade());
            stmt.setString(3, endereco.getEstado());
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

    public int editarEndereco(Endereco endereco) {
        String sql = "UPDATE endereco SET endereco = ?, cidade = ?, estado = ? WHERE id_endereco = ?";
        try (Connection conn = ConexaoDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, endereco.getEndereco());
            stmt.setString(2, endereco.getCidade());
            stmt.setString(3, endereco.getEstado());
            stmt.setInt(4, endereco.getId());
            return endereco.getId();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
