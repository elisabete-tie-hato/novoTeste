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
public class ContaComum extends Conta{
    
    public ContaComum(int id, int idCliente){
        super(id, idCliente);
    }
    
    public ContaComum(int id) throws SQLException{
        super(id);
    }
    
    public boolean saque(double valor){
        return ((valor > super.getSaldo())?false:super.saque(valor));
    }
    
    public String toString(){
        return super.toString() + " - Tipo: Comum" ;
    }
    
}
