package com.trabalhopratico.model;

public class AtracaoReport {
    private String nome;
    private double porcentagemVisualizacao;

    public AtracaoReport(String nome, double porcentagem) {
        this.nome = nome;
        this.porcentagemVisualizacao = porcentagem;
    }

    public String getNome() {
        return nome;
    }

    public double getPorcentagemVisualizacao() {
        return porcentagemVisualizacao;
    }
}
