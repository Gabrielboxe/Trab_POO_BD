package com.trabalhopratico.model;

public class Cliente {
    private Integer id;
    private String nome;
    private String email;
    private String usuario;
    private String senha;
    
    public Cliente(Integer id, String nome, String email, String usuario, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.usuario = usuario;
        this.senha = senha;
    }

    public Integer getID() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getUsuario() {
        return usuario;
    }
    
    public String getSenha() {
        return senha;
    }
}
