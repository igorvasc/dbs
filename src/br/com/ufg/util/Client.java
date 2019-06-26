package br.com.ufg.util;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

	SistemaBancarioInterface sistema = null;

	public Client(SistemaBancarioInterface sistema) {
		super();
		this.sistema = sistema;
		String localizacao = "//locahost/servico";
		try {
			sistema = (SistemaBancarioInterface) Naming.lookup(localizacao);
		} catch (MalformedURLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (RemoteException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (NotBoundException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	
}
