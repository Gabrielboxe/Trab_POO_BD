package com.trabalhopratico.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.trabalhopratico.model.Atracao;
import com.trabalhopratico.persistence.ConnectionFactory;

public class AtracaoDAO implements IDao<Atracao> {
    @Override
    public List<Atracao> selectAll() {
        List<Atracao> atracoes = new ArrayList<>();
        String script = "SELECT id, nome, descricao, horario_inicio, horario_fim, capacidade FROM atracao ORDER BY id";

        try {   
            Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(script);

            while (res.next()) {
                atracoes.add(
                    new Atracao(
                        res.getInt("id"),
                        res.getString("nome"),
                        res.getString("descricao"),
                        res.getTime("horario_inicio"),
                        res.getTime("horario_fim"),
                        res.getInt("capacidade")
                    )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return atracoes;
    }

    @Override
    public Atracao selectById(int id) {
        Atracao atracao = null;
        String script = "SELECT id, nome, descricao, horario_inicio, horario_fim, capacidade FROM atracao WHERE id = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(script);
            ResultSet res = null;

            stmt.setInt(1, id);

            res = stmt.executeQuery();

            if (res.next()) {
                atracao = new Atracao(
                    res.getInt("id"),
                    res.getString("nome"),
                    res.getString("descricao"),
                    res.getTime("horario_inicio"),
                    res.getTime("horario_fim"),
                    res.getInt("capacidade") 
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return atracao;
    }

    @Override
    public void create(Atracao atracao) {
        String script = "INSERT INTO atracao (nome, descricao, horario_inicio, horario_fim, capacidade) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(script);

            stmt.setString(1, atracao.getNome());
            stmt.setString(2, atracao.getDescricao());
            stmt.setTime(3, atracao.getHorarioInicio());
            stmt.setTime(4, atracao.getHorarioFim());
            stmt.setInt(5, atracao.getCapacidade());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Atracao atracao) {
        String script = "UPDATE atracao SET nome = ?, descricao = ?, horario_inicio = ?, horario_fim = ?, capacidade = ? WHERE id = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(script);

            stmt.setString(1, atracao.getNome());
            stmt.setString(2, atracao.getDescricao());
            stmt.setTime(3, atracao.getHorarioInicio());
            stmt.setTime(4, atracao.getHorarioFim());
            stmt.setInt(5, atracao.getCapacidade());
            stmt.setInt(6, atracao.getID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String script = "DELETE FROM atracao WHERE id = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(script);

            stmt.setInt(1, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
