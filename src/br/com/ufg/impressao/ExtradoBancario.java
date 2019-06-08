package br.com.ufg.impressao;

import java.util.Date;
import java.util.List;

public class ExtradoBancario {

	public void imprimeExtradoBancario(String nome, Integer numero, Integer agencia, List<Date> datas, 
			Date dataInicio, Date dataFim) {
		
		System.out.println("=================== Extrato Banc�rio ===================");
		System.out.println("=== Per�odo " + dataInicio + " at� " + dataFim + " ===");
		System.out.println("=== Cliente: " + nome + " =========================");
		System.out.println("=== Ag�ncia: " + agencia + " ======================");
		System.out.println("=== Conta Corrente: " + numero + " ================");
		if (!datas.isEmpty() && datas.size() > 0) {
			for (Date date : datas) {
				System.out.println("Data: " + date); //Inserir o valor que foi realizado na transa��o
				
			}
		}
		
	}
}
