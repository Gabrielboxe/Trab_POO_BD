package com.trabalhopratico.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.trabalhopratico.model.Bilheteria;
import com.trabalhopratico.persistence.ConnectionFactory;

public class BilheteriaDAO implements IDao<Bilheteria> {
    @Override
    public List<Bilheteria> selectAll() {
        List<Bilheteria> bilheteria = new ArrayList<>();
        String script = "SELECT id, preco, horario_fechamento, funcionamento, quantidade_disponivel FROM bilheteria ORDER BY id";

        try {   
            Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(script);

            while (res.next()) {
                bilheteria.add(
                    new Bilheteria(
                        res.getInt("id"),
                        res.getDouble("preco"),
                        res.getTime("horario_fechamento"),
                        res.getDate("funcionamento"),
                        res.getInt("quantidade_disponivel")
                    )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bilheteria;
    }

    @Override
    public Bilheteria selectById(int id) {
        Bilheteria bilheteria = null;
        String script = "SELECT id, preco, horario_fechamento, funcionamento, quantidade_disponivel FROM bilheteria WHERE id = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(script);
            ResultSet res = null;

            stmt.setInt(1, id);

            res = stmt.executeQuery();

            if (res.next()) {
                bilheteria = new Bilheteria(
                    res.getInt("id"),
                    res.getDouble("preco"),
                    res.getTime("horario_fechamento"),
                    res.getDate("funcionamento"),
                    res.getInt("quantidade_disponivel") 
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bilheteria;
    }

    @Override
    public void create(Bilheteria bilheteria) {
        String script = "INSERT INTO bilheteria (preco, horario_fechamento, funcionamento, quantidade_disponivel) VALUES (?, ?, ?, ?)";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(script);

            stmt.setDouble(1, bilheteria.getPreco());
            stmt.setTime(2, bilheteria.getHorarioFechamento());
            stmt.setDate(3, bilheteria.getFuncionamento());
            stmt.setInt(4, bilheteria.getQuantidadeDisponivel());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Bilheteria bilheteria) {
        String script = "UPDATE bilheteria SET preco = ?, horario_fechamento = ?, funcionamento = ?, quantidade_disponivel = ? WHERE id = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(script);

            stmt.setDouble(1, bilheteria.getPreco());
            stmt.setTime(2, bilheteria.getHorarioFechamento());
            stmt.setDate(3, bilheteria.getFuncionamento());
            stmt.setInt(4, bilheteria.getQuantidadeDisponivel());
            stmt.setInt(5, bilheteria.getID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String script = "DELETE FROM bilheteria WHERE id = ?";

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
