package br.com.ufg.dto;

import java.util.Date;
import java.util.List;

public class ContaCorrenteDTO {

	private Integer numero;
	private Integer agencia;
	private Integer identificador;
	private List<Date> dataOperacao;

	public List<Date> getDataOperacao() {
		return dataOperacao;
	}

	public void setDataOperacao(List<Date> dataOperacao) {
		this.dataOperacao = dataOperacao;
	}

	public Integer getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getAgencia() {
		return agencia;
	}

	public void setAgencia(Integer agencia) {
		this.agencia = agencia;
	}

}
