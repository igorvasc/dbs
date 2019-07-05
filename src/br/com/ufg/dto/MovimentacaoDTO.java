package br.com.ufg.dto;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ManyToAny;

@Entity(name = "movimentacao")
public class MovimentacaoDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	private TipoMovimentacao operacao;
	private Integer contaOperador;
	private Integer contaDestino;
	private BigDecimal valor;

	@Temporal(TemporalType.DATE)
	private Calendar DataOperacao;

	@JoinColumn(unique=true)
	@ManyToOne
	private ContaCorrenteDTO conta;
	
	private String tipoMovimentacao;

	public String getTipoMovimentacao() {
		return tipoMovimentacao;
	}

	public void setTipoMovimentacao(String tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}

	public ContaCorrenteDTO getConta() {
		return conta;
	}

	public void setConta(ContaCorrenteDTO conta) {
		this.conta = conta;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TipoMovimentacao getOperacao() {
		return operacao;
	}

	public void setOperacao(TipoMovimentacao operacao) {
		this.operacao = operacao;
	}

	public Integer getContaOperador() {
		return contaOperador;
	}

	public void setContaOperador(Integer contaOperador) {
		this.contaOperador = contaOperador;
	}

	public Integer getContaDestino() {
		return contaDestino;
	}

	public void setContaDestino(Integer contaDestino) {
		this.contaDestino = contaDestino;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Calendar getDataOperacao() {
		return DataOperacao;
	}

	public void setDataOperacao(Calendar dataOperacao) {
		DataOperacao = dataOperacao;
	}

}
