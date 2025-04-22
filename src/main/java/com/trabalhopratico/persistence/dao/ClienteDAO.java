package com.trabalhopratico.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.trabalhopratico.model.Cliente;
import com.trabalhopratico.persistence.ConnectionFactory;

public class ClienteDAO implements IDao<Cliente> {
@Override
    public List<Cliente> selectAll() {
        List<Cliente> cliente = new ArrayList<>();
        String script = "SELECT id, nome, email, usuario, senha FROM cliente ORDER BY id";

        try {   
            Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(script);

            while (res.next()) {
                cliente.add(
                    new Cliente(
                        res.getInt("id"),
                        res.getString("nome"),
                        res.getString("email"),
                        res.getString("usuario"),
                        res.getString("senha")
                    )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cliente;
    }

    @Override
    public Cliente selectById(int id) {
        Cliente cliente = null;
        String script = "SELECT id, nome, email, usuario, senha FROM cliente WHERE id = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(script);
            ResultSet res = null;

            stmt.setInt(1, id);

            res = stmt.executeQuery();

            if (res.next()) {
                cliente = new Cliente(
                    res.getInt("id"),
                    res.getString("nome"),
                    res.getString("email"),
                    res.getString("usuario"),
                    res.getString("senha") 
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cliente;
    }

    @Override
    public void create(Cliente cliente) {
        String script = "INSERT INTO cliente (nome, email, usuario, senha) VALUES (?, ?, ?, ?)";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(script);

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getUsuario());
            stmt.setString(4, cliente.getSenha());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Cliente cliente) {
        String script = "UPDATE cliente SET nome = ?, email = ?, usuario = ?, senha = ? WHERE id = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(script);

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getUsuario());
            stmt.setString(4, cliente.getSenha());
            stmt.setInt(5, cliente.getID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String script = "DELETE FROM cliente WHERE id = ?";

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
