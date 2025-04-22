package com.trabalhopratico.model;

import java.sql.Date;
import java.sql.Time;

public class Bilheteria {
    private Integer id;
    private Double preco;
    private Time horarioFechamento;
    private Date funcionamento;
    private Integer quantidadeDisponivel;

    public Bilheteria(Integer id, Double preco, Time horarioFuncionamento, Date funcionamento,
            Integer quantidadeDisponivel) {
        this.id = id;
        this.preco = preco;
        this.horarioFechamento = horarioFuncionamento;
        this.funcionamento = funcionamento;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public Integer getID() {
        return id;
    }

    public Double getPreco() {
        return preco;
    }

    public Time getHorarioFechamento() {
        return horarioFechamento;
    }

    public Date getFuncionamento() {
        return funcionamento;
    }

    public Integer getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(Integer quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }
}
