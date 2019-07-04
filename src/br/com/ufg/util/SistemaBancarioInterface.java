package br.com.ufg.util;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Scanner;

import javax.persistence.EntityManager;

import br.com.ufg.dto.ContaCorrenteDTO;
import br.com.ufg.dto.UsuarioDTO;

public interface SistemaBancarioInterface extends Remote {

	//1
	public void aberturaConta(UsuarioDTO usuario, ContaCorrenteDTO conta, Scanner opcao, EntityManager em) throws RemoteException;
	
	//2
	public void consultarSaldo(ContaCorrenteDTO conta, Scanner opcao, EntityManager em) throws RemoteException;
	
	//3
	public void consultarExtrato(EntityManager em) throws RemoteException;
	
	//4
	public void transferirEntreContas(EntityManager em) throws RemoteException;
	
	//5
	public void sacar(EntityManager em) throws RemoteException;
	
	//6
	public void depositar(EntityManager em) throws RemoteException;

}
