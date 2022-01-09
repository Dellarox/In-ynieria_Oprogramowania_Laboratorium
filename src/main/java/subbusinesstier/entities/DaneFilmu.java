package main.java.subbusinesstier.entities;
import java.util.*;

public class DaneFilmu {

	private String tytul;
	private String gatunek;
	private String imie;
	private String nazwisko;
	private long rokProdukcji;
	private String wytwornia;
	private ArrayList<Film> filmy = new ArrayList<Film>();

	public void Tytul_filmu() {
	}

	public String getTytul() {return tytul;}

	public void setTytul(String tytul) {this.tytul = tytul;}

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