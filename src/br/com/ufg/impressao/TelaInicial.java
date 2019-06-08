package br.com.ufg.impressao;

import java.util.Random;
import java.util.Scanner;

import br.com.ufg.dto.ContaCorrenteDTO;
import br.com.ufg.dto.UsuarioDTO;

public class TelaInicial {
	
	public static void main(String[] args) {
		System.out.println(" ------------ DBS ------------");
		
		System.out.println(" Escolha uma das opera��es abaixo: ");
		System.out.println("1 -- Abertura de conta --");
		System.out.println("2 -- Consultar saldo da conta --");
		System.out.println("3 -- Consultar saldo das movimenta��es financeiras --");
		System.out.println("4 -- Realizar transfer�ncia entre contas do mesmo banco --");
		System.out.println("5 -- Saque --");
		System.out.println("6 -- Dep�sito --");
		System.out.println("0 -- Encerrar --");
		System.out.print("-------> ");
		
		UsuarioDTO usuario = new UsuarioDTO();
		
		ContaCorrenteDTO conta = new ContaCorrenteDTO();
		
		Scanner opcao = new Scanner(System.in);
		int selecionado = opcao.nextInt();
		
		switch (selecionado) {
		case 1:
			case1(usuario, conta, opcao);
			
			break;
		case 2:
			System.out.println("Consulta de saldo selecionado");
			
			break;
		case 3:
			System.out.println("Consulta de movita��es financeiras selecionado");
			ExtradoBancario extrato = new ExtradoBancario();
			
			break;
		case 4:
			System.out.println("Realizar transferencias entre contas do mesmo banco selecionado");
			
			break;
		case 5:
			System.out.println("Saque selecionado");
			
			break;
		case 6:
			System.out.println("Dep�sito Selecionado");
			
			break;
		case 0:
			System.out.println("Saindo...");
			System.out.println("Saindo..");
			System.out.println("Saindo.");
			
			break;

		default:
			System.out.println("N�o foi selecionada uma op��o v�lida");
			break;
		}
	}

	private static void case1(UsuarioDTO usuario, ContaCorrenteDTO conta, Scanner opcao) {
		System.out.println("Abertura de conta selecionado");
		System.out.print("Informe o nome completo: ");
		usuario.setNome(opcao.next());
		
		System.out.println("O n�mero da ag�ncia �: 5331");
		conta.setAgencia(5331);
		Random random = new Random();
		conta.setNumero(random.nextInt(10000));
		System.out.println(conta.getNumero());
		System.out.println(usuario.getNome());
	}

}
