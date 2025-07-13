/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.iftm.brenomatayoshi.trabalhopvbreno.model;

/**
 *
 * @author breno
 */
public class Fornecedor {
    private int idFornecedor;
    private String nome;
    private String cnpj;
    private String telefone;
    private String email;
    private Endereco endereco;

    public Fornecedor(String nome, String cnpj, String telefone, String email, Endereco endereco) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
    }

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public String getNome() {
        return nome;
    }

    public String getCnpj() {
        return cnpj;
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

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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
    
    @Override
    public String toString() {
        return getNome();
    }
}
