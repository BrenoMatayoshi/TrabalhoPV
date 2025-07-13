/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.iftm.brenomatayoshi.trabalhopvbreno.model;

/**
 *
 * @author breno
 */
public class Usuario {
    private int id;
    private String identificacao;
    private String senha;
    private String nome;
    private String telefone;
    private String email;
    private Endereco endereco;

    public Usuario(String identificacao, String senha, String nome, String telefone, String email, Endereco endereco) {
        this.identificacao = identificacao;
        this.senha = senha;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
    }

    public int getId() {
        return id;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public String getSenha() {
        return senha;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
