/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Victor Gadder
 */
public class Conta {
    
    private int id;
    private int idCliente;
    private double saldo;

    //metodos
    
    public enum TipoConta{
        Comum, Especial
    }
    
    
    
    public Conta(int id, int idCliente){
        this.idCliente = idCliente;    
        this.id = id;
        saldo = 0;
    }
    
    public Conta(int id) throws SQLException{
        this.id = id;
        try {
            this.saldo = ContaDAO.getInstance().retrieveById(id).getSaldo();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Conta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Conta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the idCliente
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * @return the saldo
     */
    public double getSaldo() {
        return saldo;
    }
    
    public void deposito(double valor){
        saldo += valor;
    }
    
    public boolean saque(double valor){
        saldo -= valor;
        return true;
    }
    
    public String toString(){
        return id + " - Saldo: " +  saldo ;
    }
    
    
}
