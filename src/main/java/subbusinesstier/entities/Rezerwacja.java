package subbusinesstier.entities;

import main.java.subbusinesstier.entities.Film;
import main.java.subbusinesstier.entities.Klient;

import java.time.LocalDate;
import java.util.ArrayList;

public class Rezerwacja {

	private int idRezerwacji;
	private LocalDate data;
	private ArrayList<Film> filmy = new ArrayList<Film>();
	private Klient klient;

	public int getIdRezerwacji() {
		return idRezerwacji;
	}

	public void setIdRezerwacji(int idRezerwacji) {
		this.idRezerwacji = idRezerwacji;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {this.data = data;}

	public ArrayList<Film> getFilm() {return filmy;}

	public void setFilm(ArrayList<Film> filmy) {this.filmy = filmy;}

	public Klient getKlient() {return klient;}

	public void setKlient(Klient klient) {
	}

}