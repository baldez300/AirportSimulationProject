package simu.test;

import java.util.Iterator;
import java.util.PriorityQueue;
import simu.framework.Tapahtuma;

/** Tapatumanlistan test -luokka */
public class TapahtumalistaForTest {

	/** Tapahtumalista */
	private PriorityQueue<Tapahtuma> lista = new PriorityQueue<Tapahtuma>();

	/** Tapahtumalista Test - luokan Konstruktori */
	public Tapahtuma poista(){
		return lista.remove();
	}

	/** Metodo joka lisaa tapahtuman listaan */
	public void lisaa(Tapahtuma t){
		lista.add(t);
	}

	/** Metodo joka palauttaa listan seuraavan tapahtuman ajan */
	public double getSeuraavanAika(){
		return lista.peek().getAika();
	}

	/** Metodo joka palauttaa listan seuraavan tapahtuman */
	public Tapahtuma getSeuraava(){
		return lista.peek();
	}

	/** Metodi palauttaa tapantumalistan koon */
	public int getKoko(){
		return lista.size();
	}

	/** Metodi poistaa ulkomaan tapahtumat tapahtumalistasta */
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

	/** Metodi poistaa kotimaan tapahtumat tapahtumalistasta */
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

