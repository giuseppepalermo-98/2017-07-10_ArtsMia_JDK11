package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {

	private ArtsmiaDAO dao;
	private Map<Integer, ArtObject> idMapObject;
	
	private Graph<ArtObject, DefaultWeightedEdge> grafo;
	
	private List<ArtObject> best;
	
	public Model() {
		dao = new ArtsmiaDAO();
		idMapObject = new HashMap<>();
	}
	
	public void creaGrafo() {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class); 
		
		dao.listObjects(idMapObject);
		
		Graphs.addAllVertices(this.grafo, idMapObject.values());
		
		for(Adiacenza a: dao.getAdiacenti()) {
			ArtObject o1 = idMapObject.get(a.getOggetto1());
			ArtObject o2 = idMapObject.get(a.getOggetto2());
			
			if(o1 != null && o2 != null && a.getPeso()>0)
				Graphs.addEdgeWithVertices(this.grafo, o1, o2, a.getPeso());
		}
		System.out.println(grafo.vertexSet().size());
		System.out.println(grafo.edgeSet().size());
	}
	
	public boolean isPresente(Integer ido) {
		if(idMapObject.get(ido) != null)
			return true;
		return false;
	}
	
	public Integer getConnessi(Integer ido) {
		List<ArtObject> vicini = Graphs.neighborListOf(grafo, idMapObject.get(ido));
		
		return vicini.size();
	}
	
	
	public List<ArtObject> getPercorso(Integer LUN, Integer idPartenza){
		ArtObject partenza = idMapObject.get(idPartenza);
		best = new ArrayList<>();
		
		List<ArtObject>parziale = new ArrayList<>();
		parziale.add(partenza);
		
		cerca(parziale, LUN, 0);
		
		return best;
	}
	
	private void cerca(List<ArtObject> parziale, Integer LUN, Integer pesoMax) {
		
		/*if(parziale.size() == LUN && calcolaPeso(parziale)>calcolaPeso(best)) {
			best = new ArrayList<>(parziale);
			return;
		}*/
		
		if(parziale.size() == LUN) {
			Integer pesoDiParziale=0;
			
			for(int i=1; i<parziale.size(); i++) 
				pesoDiParziale += (int) grafo.getEdgeWeight(grafo.getEdge(parziale.get(i-1), parziale.get(i)));
			
			if(pesoDiParziale>pesoMax) {
				pesoMax = pesoDiParziale;
				best = new ArrayList<>(parziale);
				return;
			}
			
		}
		
		
		for(ArtObject a: Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(a) && a.getClassification().equals(parziale.get(parziale.size()-1).getClassification())) {
				parziale.add(a);
				cerca(parziale, LUN, pesoMax);
				parziale.remove(parziale.size()-1);
			}
		}
	}

	public int calcolaPeso(List<ArtObject> percorso) {
		Integer peso=0;
		
		for(int i=1; i<percorso.size(); i++) {
			peso += (int) grafo.getEdgeWeight(grafo.getEdge(percorso.get(i-1), percorso.get(i)));
		}
		
		return peso;
	}
}
