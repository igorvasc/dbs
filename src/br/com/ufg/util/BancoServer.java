package br.com.ufg.util;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;

public class BancoServer {

	public BancoServer() {
		try {
			System.setProperty("java.rmi.server.hotsname", "192.168.0.31");
			LocateRegistry.createRegistry(1099);
			SistemaBancarioInterface banco = new BancoImple();
			Naming.bind("BancoService", (Remote)banco);
		} catch (Exception e) {
			System.out.println("Error -> " + e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		new BancoServer();
	}
}
