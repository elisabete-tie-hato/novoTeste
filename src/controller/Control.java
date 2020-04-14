/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.ClienteDAO;
import model.Conta;
import model.ContaDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.ContaComum;
import model.ContaEspecial;
/**
 *
 * @author aluno
 */
public class Control {
// Control Clientes:

    public static void addCliente(String nome, String email) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        ClienteDAO.getInstance().create(nome, email);
        System.out.println("controller.Control.addCliente()");
        
    }

    public static ArrayList<Object> getAllClientes() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        return (ArrayList<Object>) ClienteDAO.getInstance().retrieveAll();
    }

    public static ArrayList<Object> getAllClientesLike(String nome) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        return (ArrayList<Object>) ClienteDAO.getInstance().retrieveLike(nome);
    }
// Control Contas:

    public static void addContaComum(int idCliente) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        ContaDAO.getInstance().create(idCliente);
    }

    public static void addContaEspecial(int idCliente, double limite) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        ContaDAO.getInstance().create(idCliente, limite);
    }

    public static ArrayList<Object> getAllContasByCliente(int idCliente) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        return ContaDAO.getInstance().retrieveByIdCliente(idCliente);
    }

    public static void depositar(int idConta, double valor) throws SQLException{
        Conta c = new Conta(idConta);
        c.deposito(valor);
        try {
            updateConta(c);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
        
    public static void sacar(int idConta, double valor, int tipo, String limite) throws SQLException{
        
        boolean podeSacar;
        
        if (tipo == 0) {
            ContaComum cc = new ContaComum(idConta);
            podeSacar = cc.saque(valor);
            
        }else{
            ContaEspecial ce = new ContaEspecial(idConta, Double.parseDouble(limite));
            podeSacar = ce.saque(valor);
        }
        
        try {
            if( podeSacar ){
                Conta c = new Conta(idConta);
                c.saque(valor);
                updateConta(c);
            }else{
                JOptionPane.showMessageDialog(null, "Saldo insulficiente para saque!!!");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void updateConta(Conta conta) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        ContaDAO.getInstance().update(conta);
    }
}
