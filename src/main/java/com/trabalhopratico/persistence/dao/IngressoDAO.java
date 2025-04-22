package com.trabalhopratico.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.trabalhopratico.model.Bilheteria;
import com.trabalhopratico.model.Cliente;
import com.trabalhopratico.model.FormaPagamento;
import com.trabalhopratico.model.Ingresso;
import com.trabalhopratico.persistence.ConnectionFactory;

public class IngressoDAO implements IDao<Ingresso> {
    private ClienteDAO clienteDAO;
    private BilheteriaDAO bilheteriaDAO;

    public IngressoDAO() {
        clienteDAO = new ClienteDAO();
        bilheteriaDAO = new BilheteriaDAO();
    }
 
    @Override
    public List<Ingresso> selectAll() {
        List<Ingresso> ingresso = new ArrayList<>();
        String script = "SELECT id, id_cliente, id_bilheteria, data_venda, pagamento FROM ingresso ORDER BY id";

        try {   
            Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(script);

            while (res.next()) {
                Cliente cliente = this.clienteDAO.selectById(res.getInt("id_cliente"));
                Bilheteria bilheteria = this.bilheteriaDAO.selectById(res.getInt("id_bilheteria"));

                ingresso.add(
                    new Ingresso(
                        res.getInt("id"),
                        res.getDate("data_venda"),
                        FormaPagamento.get(res.getString("pagamento")),
                        cliente,
                        bilheteria
                    )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingresso;
    }

    @Override
    public Ingresso selectById(int id) {
        Ingresso ingresso = null;
        String script = "SELECT id, id_cliente, id_bilheteria, data_venda, pagamento FROM ingresso WHERE id = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(script);
            ResultSet res = null;

            stmt.setInt(1, id);

            res = stmt.executeQuery();

            if (res.next()) {
                Cliente cliente = this.clienteDAO.selectById(res.getInt("id_cliente"));
                Bilheteria bilheteria = this.bilheteriaDAO.selectById(res.getInt("id_bilheteria"));

                ingresso = new Ingresso(
                    res.getInt("id"),
                    res.getDate("data_venda"),
                    FormaPagamento.get(res.getString("pagamento")),
                    cliente,
                    bilheteria
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingresso;
    }

    @Override
    public void create(Ingresso ingresso) {
        String script = "INSERT INTO ingresso (id_cliente, id_bilheteria, data_venda, pagamento) VALUES (?, ?, ?, CAST(? AS forma_pagamento))";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(script);

            stmt.setInt(1, ingresso.getCliente().getID());
            stmt.setInt(2, ingresso.getBilheteria().getID());
            stmt.setDate(3, ingresso.getDataVenda());
            stmt.setString(4, ingresso.getFormaPagamento().toString());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Ingresso ingresso) {
        String script = "UPDATE ingresso SET id_cliente = ?, id_bilheteria = ?, data_venda = ?, pagamento = ? WHERE id = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(script);

            stmt.setInt(1, ingresso.getCliente().getID());
            stmt.setInt(2, ingresso.getBilheteria().getID());
            stmt.setDate(3, ingresso.getDataVenda());
            stmt.setString(4, ingresso.getFormaPagamento().toString());
            stmt.setInt(5, ingresso.getID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String script = "DELETE FROM ingresso WHERE id = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(script);

            stmt.setInt(1, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int countIngressos() {
        int count = 0;
    
        String sql = "SELECT COUNT(*) FROM ingresso";
    
        try (Connection conn = ConnectionFactory.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql); 
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                count = rs.getInt(1);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return count;
    }

    public Ingresso selectByClientIdIfNotInAtracaoCliente(int clientId) {
        Ingresso ingresso = null;
        String script = "SELECT i.id, i.id_cliente, i.id_bilheteria, i.data_venda, i.pagamento " +
                        "FROM ingresso i " +
                        "LEFT JOIN atracao_cliente ac ON i.id = ac.id_ingresso " +
                        "WHERE i.id_cliente = ? AND ac.id_ingresso IS NULL";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(script);
            ResultSet res = null;

            stmt.setInt(1, clientId);

            res = stmt.executeQuery();

            if (res.next()) {
                Cliente cliente = this.clienteDAO.selectById(res.getInt("id_cliente"));
                Bilheteria bilheteria = this.bilheteriaDAO.selectById(res.getInt("id_bilheteria"));

                ingresso = new Ingresso(
                    res.getInt("id"),
                    res.getDate("data_venda"),
                    FormaPagamento.get(res.getString("pagamento")),
                    cliente,
                    bilheteria
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingresso;
    }
}
