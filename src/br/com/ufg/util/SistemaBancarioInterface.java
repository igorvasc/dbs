package br.com.ufg.util;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Scanner;

import br.com.ufg.dto.ContaCorrenteDTO;
import br.com.ufg.dto.UsuarioDTO;

public interface SistemaBancarioInterface extends Remote {

	//1
	public void aberturaConta(UsuarioDTO usuario, ContaCorrenteDTO conta, Scanner opcao) throws RemoteException;
	
	//2
	public void consultarSaldo(ContaCorrenteDTO conta, Scanner opcao) throws RemoteException;
	
	//3
	public void consultarExtrato() throws RemoteException;
	
	//4
	public void transferirEntreContas() throws RemoteException;
	
	//5
	public void sacar() throws RemoteException;
	
	//6
	public void depositar() throws RemoteException;

}
