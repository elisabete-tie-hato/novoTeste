/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.*;
import java.util.*;

/**
 *
 * @author Victor Gadder
 */
public abstract class GenericDAO extends Conexao {
    
    protected GenericDAO() {
        Conexao.getConnection();
    }
    
    protected abstract Object buildObject(ResultSet rs);
    
    protected ArrayList<Object> retrieveListOfObjects(String query) throws SQLException{
        PreparedStatement stmt;
        List<Object> list = new ArrayList<Object>();
        ResultSet rs;
        stmt = con.prepareStatement(query);
        rs = stmt.executeQuery();
        while (rs.next()) {
            list.add(buildObject(rs));            
        }
        rs.close();
        return (ArrayList<Object>) list;
    }
    
    protected Object retrieveById(int id, String tablename) throws SQLException{
        PreparedStatement stmt;
        Object obj = null;
        ResultSet rs;
        stmt = con.prepareStatement("SELECT * FROM " + tablename + " where id = ?");
        stmt.setInt(1, id);
        rs = stmt.executeQuery();
        if (rs.next()) {
            obj = buildObject(rs);
        }
        rs.close();
        return obj;
    }
    
    protected Object retrieveLastId(String tablename) throws SQLException{
        int id = lastId(tablename, "id");
        return retrieveById(id, tablename);
    }
    
    protected void delete(int id, String tablename) throws SQLException{
        PreparedStatement stmt;
        stmt = con.prepareStatement("DELETE FROM " +tablename+ "WHERE id = ?");
        stmt.setInt(1,id);
        stmt.executeUpdate();
    }
    
    protected int lastId(String tablename, String primaryKey) throws SQLException{
        Statement s;
        ResultSet rs;
        int lastId = -1;
        s = con.createStatement();
        s.executeQuery("SELECT MAX (" + primaryKey + ") AS \" key \" FROM " + tablename);
        rs = s.getResultSet();
        if (rs.next()) {
            lastId = rs.getInt("key");
        }
        return lastId;
    }
    
}
