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
public class Movimentacao {
    private int idMovimentacao;
    private Usuario usuario;
    private int quantidadeMovimentado;
    private LocalDate data;
    private String tipo;
    private Produto produto;
    private Fornecedor fornecedor;

    public Movimentacao(Usuario usuario, int quantidadeMovimentado, LocalDate data, String tipo, Produto produto, Fornecedor fornecedor) {
        this.usuario = usuario;
        this.quantidadeMovimentado = quantidadeMovimentado;
        this.data = data;
        this.tipo = tipo;
        this.produto = produto;
        this.fornecedor = fornecedor;
    }

    public int getIdMovimentacao() {
        return idMovimentacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public int getQuantidadeMovimentado() {
        return quantidadeMovimentado;
    }

    public LocalDate getData() {
        return data;
    }

    public String getTipo() {
        return tipo;
    }

    public Produto getProduto() {
        return produto;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setIdMovimentacao(int idMovimentacao) {
        this.idMovimentacao = idMovimentacao;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setQuantidadeMovimentado(int quantidadeMovimentado) {
        this.quantidadeMovimentado = quantidadeMovimentado;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }
}
