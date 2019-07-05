package br.com.ufg.util;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.ufg.dto.ContaCorrenteDTO;
import br.com.ufg.dto.ExtratoBancarioDTO;
import br.com.ufg.dto.MovimentacaoDTO;
import br.com.ufg.dto.TipoMovimentacao;

public class BancoImple extends UnicastRemoteObject implements SistemaBancarioInterface {

	protected BancoImple() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = 1L;

	@Override
	public void aberturaConta(ContaCorrenteDTO conta, Scanner opcao, EntityManager em)
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

		conta.setSaldo(300.0);
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

		List<ContaCorrenteDTO> listRetorno = consultarConta(conta, em);

		imprimirContaConsultada(conta, listRetorno);

		em.getTransaction().commit();

		em.close();

		finalizadorExecutacaoMensagem();

	}

	@Override
	public void consultarExtrato(EntityManager em, Scanner opcao) throws RemoteException, ParseException {
		ExtratoBancarioDTO extratoDatas = new ExtratoBancarioDTO();

		System.out.println("Consulta de movita��es financeiras selecionado");
		System.out.println("Informe a data in�cial da consulta: ");
		String dataInicial = opcao.nextLine();
		System.out.println("Informe a data final da consulta: ");
		String dataFinal = opcao.nextLine();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		Date dateInicial = dateFormat.parse(dataInicial);
		Date dateFinal = dateFormat.parse(dataFinal);
		
//		String query = "SELECT  u.saldo, u.titular, u.numero, u.email, u.agencia FROM contacorrente u where u.numero= :numero ";
//		Query q = em.createNativeQuery(query);
//		q.setParameter("numero", conta.getNumero());
//
//		Object[] listTemp = (Object[]) q.getResultList().get(0);
//
//		List<ContaCorrenteDTO> listRetorno = popularObjeto(listTemp);
//		return listRetorno;
		

		finalizadorExecutacaoMensagem();

	}

	@Override
	public void transferirEntreContas(EntityManager em, Scanner opcao) throws RemoteException {
		System.out.println("-- Realizar transferencias entre contas do mesmo banco selecionado --");

		finalizadorExecutacaoMensagem();

	}

	@Override
	public void sacar(ContaCorrenteDTO conta, Scanner opcao, EntityManager em) throws RemoteException {
		System.out.println("------ Saque selecionado -----");
		TipoMovimentacao saida = TipoMovimentacao.SAIDA;

		System.out.println("----- Informe o n�mero da conta: ");
		conta.setNumero(opcao.nextInt());

		List<ContaCorrenteDTO> listRetorno = consultarConta(conta, em);

		ContaCorrenteDTO contaBuscada = imprimirContaConsultada(conta, listRetorno);

		System.out.println("----- Informe o valor que se deseja sacar: ");
		Double valorSacar = opcao.nextDouble();

		if (valorSacar > contaBuscada.getSaldo()) {
			
		} else {
			System.out.println(
					" ==== O valor informado � superior ao que possui em saldo, por isso n�o foi poss�vel realizar o saque ===");
		}

		em.getTransaction().commit();
		em.close();

		finalizadorExecutacaoMensagem();

	}

	@Override
	public void depositar(ContaCorrenteDTO conta, Scanner opcao, EntityManager em) throws RemoteException {
		System.out.println("Dep�sito Selecionado");
		TipoMovimentacao entrada = TipoMovimentacao.ENTRADA;

		System.out.println("----- Informe o n�mero da conta: ");
		conta.setNumero(opcao.nextInt());

		List<ContaCorrenteDTO> listRetorno = consultarConta(conta, em);

		ContaCorrenteDTO contaBuscada = imprimirContaConsultada(conta, listRetorno);

		System.out.println("----- Informe o valor que se deseja sacar: ");
		Double valorSacar = opcao.nextDouble();

		if (valorSacar > contaBuscada.getSaldo()) {
			MovimentacaoDTO movimentacao = new MovimentacaoDTO();

			movimentacao.setContaDestino(conta.getNumero());
			movimentacao.setTipoMovimentacao(entrada.toString());
		} else {
			System.out.println(
					" ==== O valor informado � superior ao que possui em saldo, por isso n�o foi poss�vel realizar o saque ===");
		}

		em.getTransaction().commit();
		em.close();

		
		finalizadorExecutacaoMensagem();

	}
	
//==================================================================================================================================================
//==================================================================================================================================================
//==================================================================================================================================================
//=========================================== M�TODOS ABAIXO CRIADOS PARA FACILITAR O TRATAMENTO DE DADOS ==========================================
//==================================================================================================================================================
//==================================================================================================================================================
//==================================================================================================================================================

	private ContaCorrenteDTO imprimirContaConsultada(ContaCorrenteDTO conta, List<ContaCorrenteDTO> listRetorno) {
		if (listRetorno != null && !listRetorno.isEmpty()) {
			System.out.println("======= ATEN��O: FORAM ENCONTRADAS A SEGUINTE QUANTIDADE DE CONTAS COM O N�MERO "
					+ conta.getNumero() + ": " + listRetorno.size() + " ========");
			for (ContaCorrenteDTO contaRetorno : listRetorno) {
				System.out.println("============ CONFIRA ABAIXO OS DADOS DA SUA CONTA E SALDO ============");
				System.out.println("============ Ag�ncia: " + contaRetorno.getAgencia());
				System.out.println("============ N�mero da conta: " + contaRetorno.getNumero());
				System.out.println("============ Nome do titular: " + contaRetorno.getTitular());
				System.out.println("============ SALDO DA CONTA -----> R$ " + contaRetorno.getSaldo());
				System.out.println("=======================================================================");
				System.out.println("=======================================================================");
				return contaRetorno;
			}

		} else {
			System.out.println("----- Sua Conta/Ag�ncia n�o foi encontrada! -----");
			System.out.println("----- Favor entrar em contato com a sua ag�ncia! -----");
		}
		return null;
	}

	private List<ContaCorrenteDTO> consultarConta(ContaCorrenteDTO conta, EntityManager em) {
		em.getTransaction().begin();

		String query = "SELECT  u.saldo, u.titular, u.numero, u.email, u.agencia FROM contacorrente u where u.numero= :numero ";
		Query q = em.createNativeQuery(query);
		q.setParameter("numero", conta.getNumero());

		Object[] listTemp = (Object[]) q.getResultList().get(0);

		List<ContaCorrenteDTO> listRetorno = popularObjeto(listTemp);
		return listRetorno;
	}

	private List<ContaCorrenteDTO> popularObjeto(Object[] listTemp) {
		List<ContaCorrenteDTO> listRetorno = new ArrayList<ContaCorrenteDTO>();

		if (listTemp != null && listTemp.length > 0) {
			ContaCorrenteDTO contaTemp = new ContaCorrenteDTO();
			contaTemp.setSaldo((Double) listTemp[0]);
			contaTemp.setTitular((String) listTemp[1]);
			contaTemp.setNumero((Integer) listTemp[2]);
			contaTemp.setEmail((String) listTemp[3]);
			contaTemp.setAgencia((Integer) listTemp[4]);
			listRetorno.add(contaTemp);
		}
		return listRetorno;
	}

	public void finalizadorExecutacaoMensagem() {
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
