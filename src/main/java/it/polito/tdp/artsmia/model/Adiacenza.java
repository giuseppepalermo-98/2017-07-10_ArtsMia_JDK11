package it.polito.tdp.artsmia.model;

public class Adiacenza {

	private Integer oggetto1;
	private Integer oggetto2;
	private Integer peso;
	
	public Adiacenza(Integer oggetto1, Integer oggetto2, Integer peso) {
		super();
		this.oggetto1 = oggetto1;
		this.oggetto2 = oggetto2;
		this.peso = peso;
	}

	public Integer getOggetto1() {
		return oggetto1;
	}

	public void setOggetto1(Integer oggetto1) {
		this.oggetto1 = oggetto1;
	}

	public Integer getOggetto2() {
		return oggetto2;
	}

	public void setOggetto2(Integer oggetto2) {
		this.oggetto2 = oggetto2;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	
}
