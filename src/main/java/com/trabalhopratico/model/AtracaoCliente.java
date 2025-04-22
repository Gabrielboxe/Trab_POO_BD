package com.trabalhopratico.model;

import java.sql.Timestamp;

public class AtracaoCliente {
    private Integer id;
    private Atracao atracao;
    private Ingresso ingresso;
    private Timestamp horarioUso;

    public AtracaoCliente(Integer id, Atracao atracao, Ingresso ingresso, Timestamp horarioUso) {
        this.id = id;
        this.atracao = atracao;
        this.ingresso = ingresso;
        this.horarioUso = horarioUso;
    }

    public Integer getID() {
        return id;
    }

    public Atracao getAtracao() {
        return atracao;
    }

    public Ingresso getIngresso() {
        return ingresso;
    }

    public Timestamp getHorarioUso() {
        return horarioUso;
    }   
}
