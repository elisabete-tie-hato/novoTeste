/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.SQLException;

/**
 *
 * @author Victor Gadder
 */
public class ContaEspecial extends Conta{
    
    private double limite;
    
    public ContaEspecial(int id, int idCliente, double limite){
        super(id, idCliente);
        this.limite = limite;
    }
    
    public ContaEspecial(int id, double limite) throws SQLException{
        super(id);
        this.limite = limite;
    }

    /**
     * @return the limite
     */
    public double getLimite() {
        return limite;
    }

    /**
     * @param limite the limite to set
     */
    public void setLimite(double limite) {
        this.limite = limite;
    }
    
    public boolean saque(double valor){
        return ((valor > (super.getSaldo() + this.limite))?false:super.saque(valor));
    }
    
    public String toString(){
        return super.toString() + " - Limite: " +  limite  + " - Tipo: Especial";
    }
    
}
