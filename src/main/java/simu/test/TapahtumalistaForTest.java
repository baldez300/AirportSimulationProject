package simu.test;

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

	public void removeUlkoTapahtumia(){
		lista.removeIf(Tapahtuma::isUlkomaanTyyppi);
	}
	public void removeTapahtumia(){
		lista.removeIf(tapahtuma -> !tapahtuma.isUlkomaanTyyppi());
	}
}

