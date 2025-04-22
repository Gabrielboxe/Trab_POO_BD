package com.trabalhopratico.model;

import java.sql.Time;

public class Atracao {
    private Integer id;
    private String nome;
    private String descricao;
    private Time horarioInicio;
    private Time horarioFim;
    private Integer capacidade;

    public Atracao(Integer id, String nome, String descricao, Time horarioInicio, Time horarioFim, Integer capacicade) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
        this.capacidade = capacicade;
    }

    public Integer getID() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Time getHorarioInicio() {
        return horarioInicio;
    }

    public Time getHorarioFim() {
        return horarioFim;
    }
    
    public Integer getCapacidade() {
        return capacidade;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
