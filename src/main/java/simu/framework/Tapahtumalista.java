package simu.framework;

import simu.model.Asiakas;

import java.util.Iterator;
import java.util.PriorityQueue;

public class Tapahtumalista {
	private PriorityQueue<Tapahtuma> lista = new PriorityQueue<Tapahtuma>();

	public Tapahtuma poista(){
		Trace.out(Trace.Level.INFO,"Tapahtumalistasta poisto " + lista.peek().getTyyppi()+" Ulkomaalle:"+ lista.peek().isUlkomaanlento() + " " + lista.peek().getAika() );
		return lista.remove();
	}

	public void lisaa(Tapahtuma t){
		Trace.out(Trace.Level.INFO,"Tapahtumalistaan lisätään uusi " + t.getTyyppi() + " Ulkomaalle:"+ t.isUlkomaanlento() + " " + t.getAika());
		lista.add(t);
	}

	public double getSeuraavanAika(){
		return lista.peek().getAika();
	}

	public Tapahtuma getSeuraava(){
		return lista.peek();
	}

	public int getKoko(){
		return lista.size();
	}

	public void removeUlkoTapahtumia(){
		PriorityQueue<Tapahtuma> uusiLista = new PriorityQueue<Tapahtuma>();

		while (lista.size()>0) {
			Tapahtuma t = lista.poll();
			if(t.getAika()>0)
				if (!t.isUlkomaanlento()) {
					uusiLista.add(t);
				}
		}
		lista = uusiLista;
	}
	public void removeTapahtumia(){
		PriorityQueue<Tapahtuma> uusiLista = new PriorityQueue<Tapahtuma>();
		Iterator<Tapahtuma> iterator = lista.iterator();
		while (iterator.hasNext()) {
			Tapahtuma t = iterator.next();
			if (t.isUlkomaanlento()) {
				uusiLista.add(t);
			}
		}
		lista = uusiLista;
	}

}

