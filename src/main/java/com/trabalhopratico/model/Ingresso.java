package com.trabalhopratico.model;

import java.sql.Date;

public class Ingresso {
    private Integer id;
    private Date dataVenda;
    private FormaPagamento formaPagamento;
    private Cliente cliente;
    private Bilheteria bilheteria;

    public Ingresso(Integer id, Date dataVenda, FormaPagamento formaPagamento, Cliente cliente, Bilheteria bilheteria) {
        this.id = id;
        this.dataVenda = dataVenda;
        this.formaPagamento = formaPagamento;
        this.cliente = cliente;
        this.bilheteria = bilheteria;
    }

    public Integer getID() {
        return id;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }
    
    public Bilheteria getBilheteria() {
        return bilheteria;
    }
}
