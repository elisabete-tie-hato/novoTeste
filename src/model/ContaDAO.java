/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.Conta.TipoConta;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aluno
 */
public class ContaDAO extends GenericDAO{
    
    private static ContaDAO instance;
    
    private ContaDAO() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        super();
    }
    
    public static ContaDAO getInstance() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        if (instance == null) {
            instance = new ContaDAO();
        }
        return instance;
    }
    
    public void create(int idCliente) throws SQLException{
        this.create(TipoConta.Comum.ordinal(), idCliente, 0.0);
    }
    
    public void create(int idCliente, double limite) throws SQLException{
        this.create(TipoConta.Especial.ordinal(), idCliente, limite);
    }
    
    public void create(int tipo, int idCliente, double limite)throws SQLException{
        PreparedStatement stmt;
        String str;
        str = new String("INSERT INTO conta(tipo, fk_idcliente, saldo, limite) VALUES (?,?,?,?)");
        stmt = con.prepareStatement(str);
        stmt.setInt(1, tipo);
        stmt.setInt(2, idCliente);
        stmt.setDouble(3, 0);
        stmt.setDouble(4, limite);
        stmt.executeUpdate();
    }
    
    @Override
    protected Object buildObject(ResultSet rs){
        Conta obj = null;
        try {
            switch(rs.getInt("tipo")){
                case 0:
                    obj = new ContaComum(rs.getInt("id"), rs.getInt("fk_idcliente"));
                    break;
                case 1:
                    obj = new ContaEspecial(rs.getInt("id"), rs.getInt("fk_idcliente"), rs.getDouble("limite"));
                    break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (obj != null) {
            try {
                obj.deposito(rs.getDouble("saldo"));
            } catch (SQLException ex) {
                Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return obj;
    }
    
    public ArrayList<Object> retrieveAll() throws SQLException{
        return retrieveListOfObjects("SELECT * FROM conta");
    }
    
    public ArrayList<Object> retrieveByIdCliente(int idCliente) throws SQLException{
        return retrieveListOfObjects("SELECT * FROM conta WHERE fk_idcliente = " + idCliente);
    }
    
    public Conta retrieveById(int id) throws SQLException{
        return (Conta) retrieveById(id, "conta");
    }
    
    public Conta retrieveLastId() throws SQLException{
        return (Conta) retrieveLastId("conta");
    }
    
    public boolean update(Conta conta) throws SQLException{
        PreparedStatement stmt;
        //if (conta instanceof ContaComum) {
            stmt = con.prepareStatement("UPDATE conta SET saldo = ? WHERE id = ?");
            stmt.setDouble(1, conta.getSaldo());
            stmt.setInt(2, conta.getId());
        /*}else{
            stmt = con.prepareStatement("UPDATE conta SET saldo = ?, limite = ? WHERE id = ?");
            stmt.setDouble(1, conta.getSaldo());
            stmt.setDouble(2, ((ContaEspecial) conta).getLimite());
            stmt.setInt(3, conta.getId());
        }*/
        int update = stmt.executeUpdate();
        if (update == 1 ) {
            return true;
        }
        return false;
    }
    
    public void delete(Conta conta) throws SQLException{
        this.delete(conta.getId(), "conta");
    }
    
}
