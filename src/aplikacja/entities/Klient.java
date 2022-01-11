package aplikacja.entities;

import java.util.ArrayList;
import java.util.Scanner;

public class Klient {

    private String login;
    private String haslo;
    private String imie;
    private String nazwisko;
    private long wiek;
    private boolean state;
    private final ArrayList<Rezerwacja> rezerwacje = new ArrayList<Rezerwacja>();

    public Klient(String login, String haslo, String imie, String nazwisko, long wiek, boolean state) {
        this.login = login;
        this.haslo = haslo;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.wiek = wiek;
        this.state = state;
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


    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public long getWiek() {
        return wiek;
    }

    public void setWiek(long wiek) {
        this.wiek = wiek;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void rentMovie(ArrayList<Film> movieList, ArrayList<Rezerwacja> reservationList) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj tytul filmu, ktory chcesz wypozyczyc: ");
        String title = scanner.nextLine();

        for (int i = 0; i < movieList.size(); i++) {
            if (movieList.get(i).getTytul().equals(title)) {
                System.out.println("Dostepne " + movieList.get(i).getIloscEgzemplarzy() + " egzemplarzy.");
                if (movieList.get(i).getIloscEgzemplarzy() == 0) {
                    System.out.println("Nie ma dostepnych egzemplarzy");
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("Czy chcesz wyslac prosbe o sprowadzenie z innej placowki? (T/N)");
                    char sentRequest = scanner1.next().charAt(0);

                    if (sentRequest == 'T' || sentRequest == 't') {
                        movieList.get(i).setIloscEgzemplarzy(1);
                    }
                    break;
                } else {
                    System.out.println("Wypozyczono film.");
                    movieList.get(i).setIloscEgzemplarzy(movieList.get(i).getIloscEgzemplarzy() - 1);
                    Rezerwacja rezerwacja = new Rezerwacja(movieList.get(i).getTytul(),login);
                    reservationList.add(rezerwacja);

                    break;
                }
            }
        }
    }

    public void returnMovie(ArrayList<Film> movieList) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj tytul filmu, ktory chcesz oddac: ");
        String title = scanner.nextLine();

        for (int i = 0; i < movieList.size(); i++) {
            if (movieList.get(i).getTytul().equals(title)) {
                movieList.get(i).setIloscEgzemplarzy(movieList.get(i).getIloscEgzemplarzy() + 1);
                break;
            }
        }
    }
}