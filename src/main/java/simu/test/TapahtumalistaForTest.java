package simu.test;

import java.util.Iterator;
import java.util.PriorityQueue;
import simu.framework.Tapahtuma;

public class TapahtumalistaForTest {
	private PriorityQueue<Tapahtuma> lista = new PriorityQueue<Tapahtuma>();

	public Tapahtuma poista(){
		return lista.remove();
	}

	public void lisaa(Tapahtuma t){
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

