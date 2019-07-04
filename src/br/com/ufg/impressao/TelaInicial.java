package br.com.ufg.impressao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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
import br.com.ufg.dto.UsuarioDTO;
import br.com.ufg.util.JPAUtil;
import br.com.ufg.util.SistemaBancarioInterface;

public class TelaInicial extends UnicastRemoteObject implements SistemaBancarioInterface {

	private static final long serialVersionUID = 1L;

	protected TelaInicial() throws RemoteException {
		super();
	}

	public static void main(String[] args) throws RemoteException {

		System.out.println("======================================= ATEN��O =======================================");
		System.out.println("===== CONECTANDO A BASE DE DADOS E O SERVIDOR, POR FAVOR AGUARDE ALGUNS INSTANTES =====");
		System.out.println("========================= O MENU DE OP��ES APARECER� EM BREVE =========================");

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
		System.out.println("|----------------------------- Escolha uma das opera��es abaixo: ----------------------|");
		System.out.println("|1 ------------------------------------ Abertura de conta -----------------------------|");
		System.out.println("|2 --------------------------------- Consultar saldo da conta -------------------------|");
		System.out.println("|3 ----------------- Consultar extrado das movimenta��es financeiras ------------------|");
		System.out.println("|4 ---------------- Realizar transfer�ncia entre contas do mesmo banco ----------------|");
		System.out.println("|5 --------------------------------------- Saque --------------------------------------|");
		System.out.println("|6 ------------------------------------- Dep�sito -------------------------------------|");
		System.out.println("|0 ------------------------------------- Encerrar -------------------------------------|");
		System.out.print("|-------------------------------------------> ");

		UsuarioDTO usuario = new UsuarioDTO();

		ContaCorrenteDTO conta = new ContaCorrenteDTO();

		try {

			TelaInicial inicial = new TelaInicial();

//			String localizacao = "//locahost/servico";
//			Naming.rebind(localizacao, inicial);

			Scanner opcao = new Scanner(System.in);
			int selecionado = opcao.nextInt();

			switch (selecionado) {
			case 1:
				case1(usuario, conta, opcao, inicial, em);

				break;
			case 2:
				case2(conta, opcao, inicial, em);

				break;
			case 3:
				case3(inicial, em);
				break;
			case 4:
				case4(inicial, em);

				break;
			case 5:
				case5(inicial, em);

				break;
			case 6:
				case6(inicial, em);

				break;
			case 0:
				System.out.println("Saindo...");

				System.out.println("=================================================================================");
				System.out.println("=================================================================================");
				System.out.println("=================================================================================");
				System.out.println("=================================================================================");
				System.out.println("------ A APLICA��O TERMINOU, FAVOR EXECUTAR NOVAMENTE PARA OUTRA OPERA��O! ------");
				System.out.println("-------------------------------------- FIM --------------------------------------");
				System.out.println("=================================================================================");
				System.out.println("=================================================================================");
				System.out.println("=================================================================================");
				System.out.println("=================================================================================");

				break;

			default:
				System.out.println("********************* N�o foi selecionada uma op��o v�lida **********************");
				System.out.println("------ FAVOR EXECUTAR A APLICA��O NOVAMENTE, PARA REALIZAR OUTRA OPERA��O! ------");
				break;
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void case1(UsuarioDTO usuario, ContaCorrenteDTO conta, Scanner opcao, TelaInicial inicial,
			EntityManager em) {
		try {
			inicial.aberturaConta(usuario, conta, opcao, em);
		} catch (RemoteException e) {
			System.out.println("Ocorreu um erro ao realizar a abertura de conta!");
			System.out.println("Error: " + e.getMessage());
		}
	}

	// Consultar saldo
	private static void case2(ContaCorrenteDTO conta, Scanner opcao, TelaInicial inicial, EntityManager em) {
		try {
			inicial.consultarSaldo(conta, opcao, em);
		} catch (RemoteException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	// Consultar extrato
	private static void case3(TelaInicial inicial, EntityManager em) {
		try {
			inicial.consultarExtrato(em);
		} catch (RemoteException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	// Transferencia entre contas
	private static void case4(TelaInicial inicial, EntityManager em) {
		try {
			inicial.transferirEntreContas(em);
		} catch (RemoteException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	// Sacar
	private static void case5(TelaInicial inicial, EntityManager em) {
		try {
			inicial.sacar(em);
		} catch (RemoteException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	// Depositar
	private static void case6(TelaInicial inicial, EntityManager em) {
		try {
			inicial.depositar(em);
		} catch (RemoteException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	@Override
	public void aberturaConta(UsuarioDTO usuario, ContaCorrenteDTO conta, Scanner opcao, EntityManager em)
			throws RemoteException {
		System.out.println("---- Abertura de conta selecionado ----");
		System.out.print("Informe o nome completo: ");
		conta.setTitular(opcao.nextLine());

		System.out.println("O n�mero da ag�ncia �: 5331, por favor anote!"); // N�mero fixo, necess�rio discutirmos a
																				// implementa��o
		conta.setAgencia(5331);
		Random random = new Random(); // Gera o n�mero da ag�ncia aleat�rio
		conta.setNumero(random.nextInt(10000));

		System.out.println("Informe o e-mail do usu�rio: ");
		conta.setEmail(opcao.nextLine());

		conta.setSaldo(300);
		System.out.println(
				"Parab�ns, vc foi premiado com um saldo inicial de R$ 300,00 em sua conta, aproveite porque nem chegou o Natal!");

		em.getTransaction().begin();

		try {
			em.persist(conta);
			em.getTransaction().commit();

			System.out.println("O n�mero da ag�ncia �: 5331");
			System.out.println("O n�mero da conta �: " + conta.getNumero());
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(" --- Falha ao criar a conta! --- ");
		} finally {
			em.close();
		}

		finalizadorExecutacaoMensagem();
	}

	@Override
	public void consultarSaldo(ContaCorrenteDTO conta, Scanner opcao, EntityManager em) throws RemoteException {

		System.out.println("Consulta de saldo selecionado");

		System.out.println("Informe a ag�ncia (somente n�meros): ");
		conta.setAgencia(opcao.nextInt());

		System.out.println("Informe a Conta Corrente (somente n�meros): ");
		conta.setNumero(opcao.nextInt());

		em.getTransaction().begin();

		String query = "SELECT  u.saldo, u.titular, u.numero, u.email, u.agencia FROM contacorrente u where u.numero= :numero ";
		Query q = em.createNativeQuery(query);
		q.setParameter("numero", conta.getNumero());
		
		Object[] listTemp = (Object[]) q.getResultList().get(0);
		List<ContaCorrenteDTO> listRetorno = new ArrayList<ContaCorrenteDTO>();
				
		if (listTemp != null && listTemp.length > 0) {
			ContaCorrenteDTO contaTemp = new ContaCorrenteDTO();
			contaTemp.setSaldo((Integer) listTemp[0]);
			contaTemp.setTitular((String) listTemp[1]);
			contaTemp.setNumero((Integer) listTemp[2]);
			contaTemp.setEmail((String) listTemp[3]);
			contaTemp.setAgencia((Integer) listTemp[4]);
			listRetorno.add(contaTemp);
		}
		

		if (listRetorno != null && !listRetorno.isEmpty()) {
			System.out.println("======= ATEN��O: FORAM ENCONTRADAS A SEGUINTE QUANTIDADE DE CONTAS COM ESSE N�MERO: "
					+ listRetorno.size() + " ========");
			for (ContaCorrenteDTO contaRetorno : listRetorno) {
				System.out.println("============ CONFIRA ABAIXO OS DADOS DA SUA CONTA E SALDO ============");
				System.out.println("============ Ag�ncia: " + contaRetorno.getAgencia());
				System.out.println("============ N�mero da conta: " + contaRetorno.getNumero());
				System.out.println("============ Nome do titular: " + contaRetorno.getTitular());
				System.out.println("============ SALDO DA CONTA -----> " + contaRetorno.getSaldo());
				System.out.println("=======================================================================");
				System.out.println("=======================================================================");
			}

		} else {
			System.out.println("----- Sua Conta/Ag�ncia n�o foi encontrada! -----");
			System.out.println("----- Favor entrar em contato com a sua ag�ncia! -----");
		}

		em.getTransaction().commit();

		em.close();

		finalizadorExecutacaoMensagem();

	}

	@Override
	public void consultarExtrato(EntityManager em) throws RemoteException {
		ExtratoBancarioDTO extratoDatas = new ExtratoBancarioDTO();

		Date dataInicial;
		Date dataFinal;

		System.out.println("Consulta de movita��es financeiras selecionado");
		System.out.println("Informe a data in�cial da consulta: ");
//			dataInicial = Date.parse(opcao.next());

		finalizadorExecutacaoMensagem();

	}

	@Override
	public void transferirEntreContas(EntityManager em) throws RemoteException {
		System.out.println("-- Realizar transferencias entre contas do mesmo banco selecionado --");

		finalizadorExecutacaoMensagem();
	}

	@Override
	public void sacar(EntityManager em) throws RemoteException {
		System.out.println("Saque selecionado");

		finalizadorExecutacaoMensagem();
	}

	@Override
	public void depositar(EntityManager em) throws RemoteException {
		System.out.println("Dep�sito Selecionado");

		finalizadorExecutacaoMensagem();
	}

	private void finalizadorExecutacaoMensagem() {
		System.out.println("=================================================================================");
		System.out.println("=================================================================================");
		System.out.println("=================================================================================");
		System.out.println("=================================================================================");
		System.out.println("------ A APLICA��O TERMINOU, FAVOR EXECUTAR NOVAMENTE PARA OUTRA OPERA��O! ------");
		System.out.println("-------------------------------------- FIM --------------------------------------");
		System.out.println("=================================================================================");
		System.out.println("=================================================================================");
		System.out.println("=================================================================================");
		System.out.println("=================================================================================");
	}

}
