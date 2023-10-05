package simu.framework;

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
		if (!lista.isEmpty()) return lista.peek().getAika();
		else return 0.0;
	}

	public Tapahtuma getSeuraava(){
		return lista.peek();
	}

	public int getKoko(){
		return lista.size();
	}

	// Poistetaan kaikki tulevat ulkomaan lennon tapahtumat
	public void removeUlkomaanTapahtumat(){
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

	public void removeKotimaanTapahtumat(){
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

