package br.com.ufg.impressao;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import javax.persistence.EntityManager;

import br.com.ufg.dto.ContaCorrenteDTO;
import br.com.ufg.dto.ExtratoBancarioDTO;
import br.com.ufg.dto.UsuarioDTO;
import br.com.ufg.util.JPAUtil;
import br.com.ufg.util.SistemaBancarioInterface;

public class TelaInicial extends UnicastRemoteObject implements SistemaBancarioInterface {

	private static final long serialVersionUID = 1L;

	protected TelaInicial() throws RemoteException {
		super();
	}

	public static void main(String[] args) throws RemoteException {
		System.out.println("|----------------------------- DBS ------------------------|");
		System.out.println("|-------------------------- Bem vindo  --------------------|");
		System.out.println("|----------------------------------------------------------|");
		System.out.println("|                                                          |");
		System.out.println("|--------------- Escolha uma das operações abaixo: --------|");
		System.out.println("|1 -------------------- Abertura de conta -----------------|");
		System.out.println("|2 ----------------- Consultar saldo da conta -------------|");
		System.out.println("|3 ---- Consultar extrado das movimentações financeiras -----|");
		System.out.println("|4 -- Realizar transferência entre contas do mesmo banco --|");
		System.out.println("|5 ------------------------ Saque -------------------------|");
		System.out.println("|6 ----------------------- Depósito -----------------------|");
		System.out.println("|0 ----------------------- Encerrar -----------------------|");
		System.out.print("|-----------------------------> ");

		UsuarioDTO usuario = new UsuarioDTO();

		ContaCorrenteDTO conta = new ContaCorrenteDTO();

		try {

			TelaInicial inicial = new TelaInicial();

			String localizacao = "//locahost/servico";
			Naming.rebind(localizacao, inicial);

			Scanner opcao = new Scanner(System.in);
			int selecionado = opcao.nextInt();

			switch (selecionado) {
			case 1:
				case1(usuario, conta, opcao, inicial);

				break;
			case 2:
				case2(conta, opcao, inicial);

				break;
			case 3:
				case3(inicial);
				break;
			case 4:
				case4(inicial);

				break;
			case 5:
				case5(inicial);

				break;
			case 6:
				case6(inicial);

				break;
			case 0:
				System.out.println("Saindo...");
				System.out.println("Saindo..");
				System.out.println("Saindo.");

				break;

			default:
				System.out.println("Não foi selecionada uma opção válida");
				break;
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void case1(UsuarioDTO usuario, ContaCorrenteDTO conta, Scanner opcao, TelaInicial inicial) {
		try {
			inicial.aberturaConta(usuario, conta, opcao);
		} catch (RemoteException e) {
			System.out.println("Ocorreu um erro ao realizar a abertura de conta!");
			System.out.println("Error: " + e.getMessage());
		}
	}

	// Consultar saldo
	private static void case2(ContaCorrenteDTO conta, Scanner opcao, TelaInicial inicial) {
		try {
			inicial.consultarSaldo(conta, opcao);
		} catch (RemoteException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	// Consultar extrato
	private static void case3(TelaInicial inicial) {
		try {
			inicial.consultarExtrato();
		} catch (RemoteException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	// Transferencia entre contas
	private static void case4(TelaInicial inicial) {
		try {
			inicial.transferirEntreContas();
		} catch (RemoteException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	// Sacar
	private static void case5(TelaInicial inicial) {
		try {
			inicial.sacar();
		} catch (RemoteException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	// Depositar
	private static void case6(TelaInicial inicial) {
		try {
			inicial.depositar();
		} catch (RemoteException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	@Override
	public void aberturaConta(UsuarioDTO usuario, ContaCorrenteDTO conta, Scanner opcao) throws RemoteException {
		System.out.println("Abertura de conta selecionado");
		System.out.print("Informe o nome completo: ");
		conta.setTitular(opcao.next());

		System.out.println("O número da agência é: 5331"); // Número fixo, necessário discutirmos a implementação
		conta.setAgencia(5331);
		Random random = new Random();
		conta.setNumero(random.nextInt(10000));
		System.out.println(conta.getNumero());
		System.out.println(usuario.getNome());

		EntityManager em = new JPAUtil().getEntityManager();

		em.getTransaction().begin();

		try {
			em.persist(conta);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(" --- Falha ao criar a conta! --- ");
		}

		em.close();

	}

	@Override
	public void consultarSaldo(ContaCorrenteDTO conta, Scanner opcao) throws RemoteException {

		System.out.println("Consulta de saldo selecionado");

		System.out.println("Informe a agência (somente números): ");
		conta.setAgencia(opcao.nextInt());

		System.out.println("Informe a Conta Corrente (somente números): ");
		conta.setNumero(opcao.nextInt());

		EntityManager em = new JPAUtil().getEntityManager();

		em.getTransaction().begin();

		// ContaCorrenteDTO contaRetorno = em.find(ContaCorrenteDTO.class,
		// conta.getNumero());

		em.getTransaction().commit();

		em.close();

	}

	@Override
	public void consultarExtrato() throws RemoteException {
		ExtratoBancarioDTO extratoDatas = new ExtratoBancarioDTO();

		Date dataInicial;
		Date dataFinal;

		System.out.println("Consulta de movitações financeiras selecionado");
		System.out.println("Informe a data inícial da consulta: ");
//			dataInicial = Date.parse(opcao.next());

	}

	@Override
	public void transferirEntreContas() throws RemoteException {
		System.out.println("-- Realizar transferencias entre contas do mesmo banco selecionado --");

	}

	@Override
	public void sacar() throws RemoteException {
		System.out.println("Saque selecionado");
	}

	@Override
	public void depositar() throws RemoteException {
		System.out.println("Depósito Selecionado");

	}

}
