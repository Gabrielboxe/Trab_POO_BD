package com.trabalhopratico.model;

public class BilheteriaReport {
    private String nome;
    private double porcentagemCompra;

    public BilheteriaReport(String nome, double porcentagemCompra) {
        this.nome = nome;
        this.porcentagemCompra = porcentagemCompra;
    }

    public String getNome() {
        return nome;
    }

    public double getPorcentagemCompra() {
        return porcentagemCompra;
    }
}