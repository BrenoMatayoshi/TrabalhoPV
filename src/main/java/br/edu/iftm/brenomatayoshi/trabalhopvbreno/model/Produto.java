/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.iftm.brenomatayoshi.trabalhopvbreno.model;

import java.time.LocalDate;

/**
 *
 * @author breno
 */
public class Produto {
    private int idProduto;
    private Categoria categoria;
    private String nome;
    private String descricao;
    private int quantidadeEstoque;
    private LocalDate dataCadastro;
    private boolean ativo;

    public Produto(Categoria categoria, String nome, String descricao, int quantidadeEstoque, LocalDate dataCadastro, boolean ativo) {
        this.categoria = categoria;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidadeEstoque = quantidadeEstoque;
        this.dataCadastro = dataCadastro;
        this.ativo = ativo;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    
}
