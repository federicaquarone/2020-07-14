package it.polito.tdp.PremierLeague.model;

public class Classifica implements Comparable<Classifica> {
	Team t;
	int punteggio;
	public Classifica(Team t, int punteggio) {
		super();
		this.t = t;
		this.punteggio = punteggio;
	}
	public Team getT() {
		return t;
	}
	public void setT(Team t) {
		this.t = t;
	}
	public int getPunteggio() {
		return punteggio;
	}
	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}
	public void incrementaPunteggio(int i) {
		punteggio+= punteggio +1;
		
		
	}
	@Override
	public int compareTo(Classifica o) {
		// TODO Auto-generated method stub
		return this.punteggio-o.punteggio;
	}
	
	

}
