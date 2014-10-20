package org.celllife.idart.gui.alert;

/**
 * Esta classe cria um objecto com atributos que irão compor a tabela de alerta de níveis de stock
 * @author EdiasJambaia
 * @since Setembro 2014
 */

public class RiscoRoptura {
	
	private String nome;
	private int amc;
	private int saldo;
	private int qtyOrdem;
	
	
	public int getAmc() {
		return amc;
	}
	public void setAmc(int amc) {
		this.amc = amc;
	}
	public int getSaldo() {
		return saldo;
	}
	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}
	public int getQtyOrdem() {
		return qtyOrdem;
	}
	public void setQtyOrdem(int qtyOrdem) {
		this.qtyOrdem = qtyOrdem;
	}
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public RiscoRoptura() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RiscoRoptura(String nome, int amc, int saldo, int qtyOrdem) {
		super();
		this.nome=nome;
		this.amc = amc;
		this.saldo = saldo;
		this.qtyOrdem = qtyOrdem;
	}
	


}
