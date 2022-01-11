package aplikacja.entities;

import java.util.ArrayList;

public class Film {

	private int idFilmu;
	private Rezerwacja rezerwacja;
	private String tytul;
	private String gatunek;
	private String imie;
	private String nazwisko;
	private long rokProdukcji;
	private String wytwornia;

	public long getIloscEgzemplarzy() {
		return iloscEgzemplarzy;
	}

	public void setIloscEgzemplarzy(long iloscEgzemplarzy) {
		this.iloscEgzemplarzy = iloscEgzemplarzy;
	}

	private long iloscEgzemplarzy;
	private ArrayList<Film> filmy = new ArrayList<Film>();

	public Film(String tytul, String gatunek, String imie, String nazwisko, long rokProdukcji, String wytwornia, long iloscEgzemplarzy) {
		this.tytul = tytul;
		this.gatunek = gatunek;
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.rokProdukcji = rokProdukcji;
		this.wytwornia = wytwornia;
		this.iloscEgzemplarzy = iloscEgzemplarzy;
	}

	public int getIdFilmu() {return idFilmu;}

	public void setIdFilmu(int idFilmu) {
		this.idFilmu = idFilmu;
	}

	public String getTytul() {return tytul;}

	public void setTytul(String tytul) {this.tytul = tytul;}

	public String getGatunek(){
		return gatunek;
	}

	public void setGatunek(String gatunek){
		this.gatunek = gatunek;
	}

	public String getImie() {return imie;}

	public void setImie(String imie) {this.imie = imie;}

	public String getNazwisko() {return nazwisko;}

	public void setNazwisko(String nazwisko) {this.nazwisko = nazwisko;}

	public long getRokProdukcji() {return rokProdukcji;}

	public void setRokProdukcji(long rokProdukcji) {this.rokProdukcji = rokProdukcji;}

	public String getWytwornia() {return wytwornia;}

	public void setWytwornia(String wytwornia) {this.wytwornia = wytwornia;}

	public String toString() {return "test";}
}