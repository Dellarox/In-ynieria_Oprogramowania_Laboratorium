package main.java.subbusinesstier.entities;

import java.util.ArrayList;
import java.util.Scanner;

public class Klient {

	private String login;
	private String haslo;
	private String imie;
	private String nazwisko;
	private long wiek;
	private boolean state;
	private ArrayList<subbusinesstier.entities.Rezerwacja> rezerwacje = new ArrayList<subbusinesstier.entities.Rezerwacja>();

	public Klient(String login, String haslo, String imie, String nazwisko, long wiek,boolean state)
	{
		this.login=login;
		this.haslo=haslo;
		this.imie=imie;
		this.nazwisko=nazwisko;
		this.wiek=wiek;
		this.state=state;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getHaslo() {
		return haslo;
	}

	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}


	public String getImie() {return imie;}

	public void setImie(String imie) {this.imie = imie;}

	public String getNazwisko() {return nazwisko;}

	public void setNazwisko(String nazwisko) {this.nazwisko = nazwisko;}

	public long getWiek() {return wiek;}

	public void setWiek(long wiek) {this.wiek = wiek;}

	public boolean getState() {return state;}

	public void setState(boolean state) {this.state = state;}


	public void sendRequestForNewAccount()
	{
		Scanner scan = new Scanner(System.in);
		String nazwisko = scan.nextLine();
		String imie = scan.nextLine();
		String login = scan.nextLine();
		String haslo = scan.nextLine();
		Short wiek = scan.nextShort();
		Klient klient = new Klient(login,haslo,imie,nazwisko,wiek,false);
		System.out.println("Wysylano prosbe o zalozenie konta");
		System.out.println("Sprawdzanie poprawności danych");
		if (!imie.isEmpty()  && !nazwisko.isEmpty() && !login.isEmpty() && !haslo.isEmpty() && wiek > 0)
		{
			System.out.println("Dane poprawne");
			System.out.println("Klient został dodany do bazy dancyh");
		}
		else
		{
			System.out.println("Dane niepoprawne");
			System.out.println("Prośba została odrzucona");
		}
	}

	public void startLogin()
	{
		String podanyLogin, podaneHaslo;
		Scanner scan = new Scanner(System.in);

		System.out.println("Rozpoczęcie logowania.");
		int i;
		for(i = 0; i < 3; i++) {
			System.out.println("Podaj login i hasło:");
			podanyLogin = scan.nextLine();
			podaneHaslo = scan.nextLine();
			System.out.println("Sprawdzanie poprawności danych.");
			if (login == podanyLogin && haslo == podaneHaslo) {
				//loginToSystem();
			} else {
				//if (counter() == 2) {
				//	loginFailed();
				//}
			}
		}
	}
}