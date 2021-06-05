package it.polito.tdp.nobel.model;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {
	private List<Esame> partenza;
	private Set<Esame> soluzioneMigliore;
	private double mediaSoluzioneMigliore;
	private int casiTestati = 0;
	
	public Model() {
		EsameDAO dao = new EsameDAO();
		this.partenza = dao.getTuttiEsami();
		
	}

	
	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		Set<Esame> parziale = new LinkedHashSet<Esame>();
		soluzioneMigliore = new HashSet<Esame>();
		mediaSoluzioneMigliore = 0;
		casiTestati = 0;
		cerca(parziale, 0, numeroCrediti);
		return null;	
	}

	
	private void cerca(Set<Esame> parziale, int L, int m) {
		casiTestati ++;
		
		int crediti = sommaCrediti(parziale);
		if(crediti > m)
			return;
		
		if (crediti == m) {
			double media = calcolaMedia(parziale);
			if(media > mediaSoluzioneMigliore) {
				soluzioneMigliore = new HashSet<>(parziale);
				mediaSoluzioneMigliore = media;
			}
			return;
		}
		
		if (L == partenza.size()) 
			return;
		
		parziale.add(partenza.get(L));
		cerca(parziale, L + 1, m);
		
		parziale.remove(partenza.get(L));
		cerca(parziale, L+1, m);
		
	}


	public double calcolaMedia(Set<Esame> esami) {
		
		int crediti = 0;
		int somma = 0;
		
		for(Esame e : esami){
			crediti += e.getCrediti();
			somma += (e.getVoto() * e.getCrediti());
		}
		
		return somma/crediti;
	}
	
	public int sommaCrediti(Set<Esame> esami) {
		int somma = 0;
		
		for(Esame e : esami)
			somma += e.getCrediti();
		
		return somma;
	}

}
