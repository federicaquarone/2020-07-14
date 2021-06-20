package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	PremierLeagueDAO dao;
	private Graph<Team, DefaultWeightedEdge> grafo;
	private Map<Integer,Team> idMap;
	private List<Classifica> classifica;
	
	public Model() {
		this.dao= new PremierLeagueDAO();
		this.idMap= new HashMap<Integer,Team>();
		this.dao.listAllTeams(idMap);
		this.classifica= new LinkedList<>();
	}
	
	public void creaGrafo() {
		this.grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//aggiungo vertici
		Graphs.addAllVertices(grafo, idMap.values());
		
		
		//aggiungo archi
		/*List<Adiacenza> archi= dao.getArchi(idMap);
		for(Adiacenza a: archi) {
			DefaultWeightedEdge e= grafo.addEdge(a.getT1(), a.getT2());
			grafo.setEdgeWeight(e, a.getPeso());
		}*/
		List<Match> partite= dao.listAllMatches();
		for(Team t: this.grafo.vertexSet()) {
			int punti=0;
			for(Match p: partite) {
			if(t.getTeamID().equals(p.getTeamHomeID())) {
				if(p.getReaultOfTeamHome()==0) {
					punti+=1;
				}else if(p.getReaultOfTeamHome()==-1) {
					punti+=0;
				}else if(p.getReaultOfTeamHome()==1) {
					punti+=3;
				}
				
			}
			if(t.getTeamID().equals(p.getTeamAwayID())) {
				if(p.getReaultOfTeamHome()==0) {
					punti+=1;
				}else if(p.getReaultOfTeamHome()==-1) {
					punti+=3;
				}else if(p.getReaultOfTeamHome()==1) {
					punti+=0;
				}
			}
		}
			classifica.add(new Classifica(t, punti));
		}
		Collections.sort(classifica);
		for(Classifica t1: classifica) {
			for(Classifica t2: classifica) {
				if(t1.getPunteggio()-t2.getPunteggio()>0) {
					Graphs.addEdgeWithVertices(this.grafo, t1.getT(),t2.getT(), t1.getPunteggio()-t2.getPunteggio());
				}
				if(t1.getPunteggio()-t2.getPunteggio()<0) {
					Graphs.addEdgeWithVertices(this.grafo, t2.getT(), t1.getT(),t2.getPunteggio()-t1.getPunteggio());
				}
			}
		}
	}
	
	
	public int getNvertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getnArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public int incrementaPunteggio(int x) {
		int punteggio=0;
		return punteggio+= punteggio +x;
	}
	
}
