package simu.framework;

import java.util.PriorityQueue;

public class Tapahtumalista {
	private PriorityQueue<Tapahtuma> lista = new PriorityQueue<Tapahtuma>();

	public Tapahtuma poista(){
		Trace.out(Trace.Level.INFO,"Tapahtumalistasta poisto " + lista.peek().getTyyppi() + " " + lista.peek().getAika() );
		return lista.remove();
	}

	public void lisaa(Tapahtuma t){
		Trace.out(Trace.Level.INFO,"Tapahtumalistaan lisätään uusi " + t.getTyyppi() + " " + t.getAika());
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

