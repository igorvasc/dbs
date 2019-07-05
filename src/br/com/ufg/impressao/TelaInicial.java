package br.com.ufg.impressao;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.transform.Transformers;

import br.com.ufg.dto.ContaCorrenteDTO;
import br.com.ufg.dto.ExtratoBancarioDTO;
import br.com.ufg.dto.MovimentacaoDTO;
import br.com.ufg.dto.TipoMovimentacao;
import br.com.ufg.dto.UsuarioDTO;
import br.com.ufg.util.JPAUtil;
import br.com.ufg.util.SistemaBancarioInterface;

public class TelaInicial {

	public static void main(String[] args) throws RemoteException {

		System.out.println("======================================= ATENÇÃO =======================================");
		System.out.println("===== CONECTANDO A BASE DE DADOS E O SERVIDOR, POR FAVOR AGUARDE ALGUNS INSTANTES =====");
		System.out.println("========================= O MENU DE OPÇÕES APARECERÁ EM BREVE =========================");

		System.out.println("\n\n\n\n");
		EntityManager em = new JPAUtil().getEntityManager();
		System.out.println("\n\n\n\n");

		System.out.println("========================================================================================");
		System.out.println("========================================================================================");
		System.out.println("========================================================================================");
		System.out.println("========================================================================================");
		System.out.println("========================================================================================");
		System.out.println("|------------------------------------------ DBS ---------------------------------------|");
		System.out.println("|---------------------------------------- Bem vindo  ----------------------------------|");
		System.out.println("|--------------------------------------------------------------------------------------|");
		System.out.println("|                                                                                      |");
		System.out.println("|----------------------------- Escolha uma das operações abaixo: ----------------------|");
		System.out.println("|1 ------------------------------------ Abertura de conta -----------------------------|");
		System.out.println("|2 --------------------------------- Consultar saldo da conta -------------------------|");
		System.out.println("|3 ----------------- Consultar extrado das movimentações financeiras ------------------|");
		System.out.println("|4 ---------------- Realizar transferência entre contas do mesmo banco ----------------|");
		System.out.println("|5 --------------------------------------- Saque --------------------------------------|");
		System.out.println("|6 ------------------------------------- Depósito -------------------------------------|");
		System.out.println("|0 ------------------------------------- Encerrar -------------------------------------|");
		System.out.print("|-------------------------------------------> ");

		ContaCorrenteDTO conta = new ContaCorrenteDTO();

		try {

			SistemaBancarioInterface banco = (SistemaBancarioInterface) Naming.lookup("rmi://192.168.0.31:1099/BancoService");

			Scanner opcao = new Scanner(System.in);
			int selecionado = opcao.nextInt();

			switch (selecionado) {
			case 1:
				case1(conta, opcao, banco, em);

				break;
			case 2:
				case2(conta, opcao, banco, em);

				break;
			case 3:
				case3(banco, em, opcao);
				break;
			case 4:
				case4(banco, em, opcao);

				break;
			case 5:
				case5(conta, banco, em, opcao);

				break;
			case 6:
				case6(conta, banco, em, opcao);

				break;
			case 0:
				System.out.println("Saindo...");

				System.out.println("=================================================================================");
				System.out.println("=================================================================================");
				System.out.println("=================================================================================");
				System.out.println("=================================================================================");
				System.out.println("------ A APLICAÇÃO TERMINOU, FAVOR EXECUTAR NOVAMENTE PARA OUTRA OPERAÇÃO! ------");
				System.out.println("-------------------------------------- FIM --------------------------------------");
				System.out.println("=================================================================================");
				System.out.println("=================================================================================");
				System.out.println("=================================================================================");
				System.out.println("=================================================================================");

				break;

			default:
				System.out.println("********************* Não foi selecionada uma opção válida **********************");
				System.out.println("------ FAVOR EXECUTAR A APLICAÇÃO NOVAMENTE, PARA REALIZAR OUTRA OPERAÇÃO! ------");
				break;
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void case1(ContaCorrenteDTO conta, Scanner opcao, SistemaBancarioInterface banco,
			EntityManager em) {
		try {
			banco.aberturaConta( conta, opcao, em);
		} catch (RemoteException e) {
			System.out.println("Ocorreu um erro ao realizar a abertura de conta!");
			System.out.println("Error: " + e.getMessage());
		}
	}

	// Consultar saldo
	private static void case2(ContaCorrenteDTO conta, Scanner opcao, SistemaBancarioInterface banco, EntityManager em) {
		try {
			banco.consultarSaldo(conta, opcao, em);
		} catch (RemoteException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	// Consultar extrato
	private static void case3(SistemaBancarioInterface banco, EntityManager em, Scanner opcao) throws ParseException {
		try {
			banco.consultarExtrato(em, opcao);
		} catch (RemoteException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	// Transferencia entre contas
	private static void case4(SistemaBancarioInterface banco, EntityManager em, Scanner opcao) {
		try {
			banco.transferirEntreContas(em, opcao);
		} catch (RemoteException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	// Sacar
	private static void case5(ContaCorrenteDTO conta, SistemaBancarioInterface banco, EntityManager em, Scanner scan) {
		try {
			banco.sacar(conta, scan, em);
		} catch (RemoteException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	// Depositar
	private static void case6(ContaCorrenteDTO conta, SistemaBancarioInterface banco, EntityManager em, Scanner opcao) {
		try {
			banco.depositar(conta, opcao, em);
		} catch (RemoteException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

}
