package com.trabalhopratico.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.trabalhopratico.model.Atracao;
import com.trabalhopratico.model.AtracaoCliente;
import com.trabalhopratico.model.Ingresso;
import com.trabalhopratico.persistence.ConnectionFactory;

public class AtracaoClienteDAO implements IDao<AtracaoCliente> {
    private AtracaoDAO atracaoDao;
    private IngressoDAO ingressoDao;

    public AtracaoClienteDAO() {
        atracaoDao = new AtracaoDAO();
        ingressoDao = new IngressoDAO();
    }
 
    @Override
    public List<AtracaoCliente> selectAll() {
        List<AtracaoCliente> atracaoCliente = new ArrayList<>();
        String script = "SELECT id, id_atracao, id_ingresso, horario_uso FROM atracao_cliente ORDER BY id";

        try {   
            Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(script);

            while (res.next()) {
                Atracao atracao = this.atracaoDao.selectById(res.getInt("id_atracao"));
                Ingresso ingresso = this.ingressoDao.selectById(res.getInt("id_ingresso"));

                atracaoCliente.add(
                    new AtracaoCliente(
                        res.getInt("id"),
                        atracao,
                        ingresso,
                        res.getTimestamp("horario_uso")
                    )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return atracaoCliente;
    }

    @Override
    public AtracaoCliente selectById(int id) {
        AtracaoCliente atracaoCliente = null;
        String script = "SELECT id, id_atracao, id_ingresso, horario_uso FROM atracao_cliente WHERE id = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(script);
            ResultSet res = null;

            stmt.setInt(1, id);

            res = stmt.executeQuery();

            if (res.next()) {
                Atracao atracao = this.atracaoDao.selectById(res.getInt("id_atracao"));
                Ingresso ingresso = this.ingressoDao.selectById(res.getInt("id_ingresso"));

                atracaoCliente = new AtracaoCliente(
                    res.getInt("id"),
                    atracao, 
                    ingresso,
                    res.getTimestamp("horario_uso")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return atracaoCliente;
    }

    @Override
    public void create(AtracaoCliente atracaoCliente) {
        String script = "INSERT INTO atracao_cliente (id_atracao, id_ingresso, horario_uso) VALUES (?, ?, ?)";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(script);

            stmt.setInt(1, atracaoCliente.getAtracao().getID());
            stmt.setInt(2, atracaoCliente.getIngresso().getID());
            stmt.setTimestamp(3, atracaoCliente.getHorarioUso());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(AtracaoCliente atracaoCliente) {
        String script = "UPDATE atracao_cliente SET id_atracao = ?, id_ingresso = ?, horario_uso = ? WHERE id = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(script);

            stmt.setInt(1, atracaoCliente.getAtracao().getID());
            stmt.setInt(2, atracaoCliente.getIngresso().getID());
            stmt.setTimestamp(3, atracaoCliente.getHorarioUso());
            stmt.setInt(4, atracaoCliente.getID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String script = "DELETE FROM atracao_cliente WHERE id = ?";

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
