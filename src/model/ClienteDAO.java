/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Victor Gadder
 */
public class ClienteDAO extends GenericDAO{
    
    private static ClienteDAO instance;
    
    private ClienteDAO() {
        
    }
    
    public static ClienteDAO getInstance() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
         if (instance == null) {
             instance = new ClienteDAO();
         }
         return instance;
}
    
    @Override
    protected Object buildObject(ResultSet rs){
        Cliente obj = null;
        try {
            obj = new Cliente(rs.getInt("id"), rs.getString("nome"), rs.getString("email"));
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obj;
    }
    
    public void create(String nome, String email) throws SQLException{
        PreparedStatement stmt;
        stmt = con.prepareStatement("INSERT INTO cliente (nome, email) VALUES (?,?)");
        stmt.setString(1, nome);
        stmt.setString(2, email);
        stmt.executeUpdate();
    }
    
    public List<Object> retrieveAll() throws SQLException{
        return retrieveListOfObjects("SELECT * FROM cliente ORDER BY nome");
    }
    
    public List<Object> retrieveLike(String nome) throws SQLException{
        return retrieveListOfObjects("SELECT * FROM cliente WHERE nome LIKE '%" +nome+"%'");
    }
    
    public Cliente retrieveById(int id) throws SQLException{
        return (Cliente) retrieveById(id, "cliente");
    }
    
    public Cliente retrieveLastId() throws SQLException{
        return (Cliente) retrieveLastId("cliente");
    }
    
    public boolean update(Cliente cliente) throws SQLException{
        PreparedStatement stmt;
        stmt = con.prepareStatement("UPDATE cliente SET nome = ?, email = ? WHERE id = ?");
        stmt.setString(1, cliente.getNome());
        stmt.setString(2, cliente.getEmail());
        return (stmt.executeUpdate() == 1);
    }
    
    public void delete(Conta conta) throws SQLException{
        this.delete(conta.getId(), "cliente");
    }
    
}
