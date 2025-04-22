package com.trabalhopratico.model;

public enum FormaPagamento {
    PIX("pix"),
    CREDITO("credito"),
    DEBITO("debito"),
    DINHEIRO("dinheiro");

    private final String value;

    FormaPagamento(String value) {
        this.value = value;
    }   

    @Override
    public String toString() {
        return this.value;
    }

    public static FormaPagamento get(String value) {
        for (FormaPagamento fp: FormaPagamento.values())
            if (fp.toString().equals(value)) return fp;
        return DINHEIRO;
    }
}
