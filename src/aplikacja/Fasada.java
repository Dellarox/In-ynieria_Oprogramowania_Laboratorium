package aplikacja;

import aplikacja.entities.Klient;
import aplikacja.entities.Rezerwacja;
import aplikacja.entities.Film;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.exit;

public class Fasada {
    public ArrayList<Klient> listaKlientow = new ArrayList<Klient>();
    public ArrayList<Film> movieList = new ArrayList<Film>();
    public ArrayList<Rezerwacja> reservationList = new ArrayList<Rezerwacja>();
    public Klient aktualnyKlient;
    public Fasada() {
    }

    public static void main(String[] args) throws IOException {
        Fasada fasada = new Fasada();
        String moviesPath = "filmy.json";
        String clientsPath = "klienci.json";
        String reservationPath = "rezerwacje.json";
        fasada.readJson(clientsPath, moviesPath, reservationPath);
        boolean endLoop = false;
        while (true) {
            Scanner scan = new Scanner(System.in);
            int menuChoice = 3;
            System.out.println("\n1. Logowanie\n");
            System.out.println("2. Wyslij prosbe o zalozenie konta\n");
            System.out.println("3. Zamknij program\n");

            int menu1Choice = scan.nextInt();
            if(menu1Choice == 1)
            {
                scan = new Scanner(System.in);
                System.out.println("\n1. Wpisz login\n");
                String login = scan.nextLine();
                System.out.println("2. Wpisz haslo\n");
                String password = scan.nextLine();
                if(login.equals("admin") && password.equals("admin"))
                {
                    menuChoice = 2;
                }
                else {
                    boolean isFound=false;

                    for (int i = 0; i < fasada.listaKlientow.size(); i++) {
                        if ((fasada.listaKlientow.get(i).getLogin().equals(login) && fasada.listaKlientow.get(i).getHaslo().equals(password)) && fasada.listaKlientow.get(i).getState()){
                            menuChoice = 1;
                            fasada.aktualnyKlient = fasada.listaKlientow.get(i);
                            isFound = true;
                        }
                    }

                    if(!isFound)
                    {
                        System.out.println("Niepoprawne dane logowania");
                        menuChoice = 3;
                    }
                }
            }
            else if(menu1Choice == 2){
                fasada.sendRequestForNewAccount();
                menuChoice = 3;
            }
            else if(menu1Choice == 3){
                exit(0);
            }
            else
            {
                menuChoice = 3;
            }

            switch (menuChoice) {
                case 1:
                    do {
                        System.out.println("\nWypozyczalnia filmow\n");
                        System.out.println("1. Pokaz filmy\n");
                        System.out.println("2. Wypozycz film\n");
                        System.out.println("3. Zwroc film\n");
                        System.out.println("4. Zamknij\n");
                        System.out.println("5. Wyloguj\n");
                        scan = new Scanner(System.in);
                        menuChoice = scan.nextInt();
                        switch (menuChoice) {
                            case 1:
                                fasada.printMovies(fasada.movieList);
                                break;
                            case 2:
                                fasada.aktualnyKlient.rentMovie(fasada.movieList, fasada.reservationList);
                                break;
                            case 3:
                                fasada.aktualnyKlient.returnMovie(fasada.movieList);
                                break;
                            case 4:
                                fasada.saveJson(clientsPath, moviesPath, reservationPath, fasada.listaKlientow, fasada.movieList, fasada.reservationList);
                                exit(0);
                                break;
                            case 5:
                                fasada.saveJson(clientsPath, moviesPath, reservationPath, fasada.listaKlientow, fasada.movieList, fasada.reservationList);
                                endLoop = true;
                                break;
                        }
                    }while (!endLoop);
                    break;
                case 2:
                    do {
                        System.out.println("\nWypozyczalnia filmow\n");
                        System.out.println("1. Wyswietl klientow\n");
                        System.out.println("2. Weryfikacja danych\n");
                        System.out.println("3. Edycja danych klienta\n");
                        System.out.println("4. Pokaz filmy\n");
                        System.out.println("5. Dodaj film\n");
                        System.out.println("6. Edytuj film\n");
                        System.out.println("7. Usun film\n");
                        System.out.println("8. Zapisz do pliku\n");
                        System.out.println("9. Zamknij\n");
                        System.out.println("10. Wyloguj\n");
                        scan = new Scanner(System.in);
                        menuChoice = scan.nextInt();
                        switch (menuChoice) {
                            case 1:
                                fasada.showKlienci(fasada.listaKlientow);
                                break;
                            case 2:
                                fasada.verifyClients(fasada.listaKlientow);
                                break;
                            case 3:
                                fasada.editClients(fasada.listaKlientow);
                                break;
                            case 4:
                                fasada.printMovies(fasada.movieList);
                                break;
                            case 5:
                                fasada.addMovie(fasada.movieList);
                                break;
                            case 6:
                                fasada.editMovie(fasada.movieList);
                                break;
                            case 7:
                                fasada.removeMovie(fasada.movieList);
                                break;
                            case 8:
                                fasada.saveJson(clientsPath, moviesPath, reservationPath, fasada.listaKlientow, fasada.movieList, fasada.reservationList);
                                break;
                            case 9:
                                fasada.saveJson(clientsPath, moviesPath, reservationPath, fasada.listaKlientow, fasada.movieList, fasada.reservationList);
                                exit(0);
                                break;
                            case 10:
                                fasada.saveJson(clientsPath, moviesPath, reservationPath, fasada.listaKlientow, fasada.movieList, fasada.reservationList);
                                endLoop = true;
                                break;
                        }
                    }while (!endLoop);
                    break;
                case 3:
                    break;
            }

        }
    }

    private void saveJson(String clientsPath, String moviesPath, String reservationPath, ArrayList<Klient> clientList, ArrayList<Film> movieList, ArrayList<aplikacja.entities.Rezerwacja> reservationList) {
        JSONArray clientsJsonList = new JSONArray();
        clientList.forEach(klient -> saveClientToJson(klient, clientsJsonList));

        try (FileWriter file = new FileWriter("klienci.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(clientsJsonList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONArray moviesJsonList = new JSONArray();
        movieList.forEach(movie -> saveMovieToJson(movie, moviesJsonList));

        try (FileWriter file = new FileWriter("filmy.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(moviesJsonList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONArray reservationJsonList = new JSONArray();
        reservationList.forEach(reservation -> saveReservationToJson(reservation, reservationJsonList));

        try (FileWriter file = new FileWriter("rezerwacje.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(reservationJsonList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveReservationToJson(aplikacja.entities.Rezerwacja reservation, JSONArray reservationJsonList) {
        JSONObject reservationDetails = new JSONObject();
        reservationDetails.put("title", (String) reservation.getTitle());
        reservationDetails.put("login", (String) reservation.getLogin());


        JSONObject klientObject = new JSONObject();
        klientObject.put("rezerwacja", reservationDetails);


        reservationJsonList.add(klientObject);
    }

    private void saveClientToJson(Klient klient, JSONArray klienciJsonList) {
        JSONObject klientDetails = new JSONObject();
        klientDetails.put("imie", (String) klient.getImie());
        klientDetails.put("nazwisko", (String) klient.getNazwisko());
        klientDetails.put("login", (String) klient.getLogin());
        klientDetails.put("haslo", (String) klient.getHaslo());
        klientDetails.put("wiek", klient.getWiek());
        klientDetails.put("state", klient.getState());

        JSONObject klientObject = new JSONObject();
        klientObject.put("klient1", klientDetails);


        klienciJsonList.add(klientObject);
    }

    private void saveMovieToJson(Film film, JSONArray moviesJsonList) {
        JSONObject movieDetails = new JSONObject();
        movieDetails.put("tytul", (String) film.getTytul());
        movieDetails.put("gatunek", (String) film.getGatunek());
        movieDetails.put("imie", (String) film.getImie());
        movieDetails.put("nazwisko", (String) film.getNazwisko());
        movieDetails.put("rok produkcji", film.getRokProdukcji());
        movieDetails.put("wytwornia", (String) film.getWytwornia());
        movieDetails.put("ilosc egzemplarzy", film.getIloscEgzemplarzy());

        JSONObject movieObject = new JSONObject();
        movieObject.put("film", movieDetails);


        moviesJsonList.add(movieObject);
    }

    private void showKlienci(ArrayList<Klient> listaKlientow) {
        for (int i = 0; i < listaKlientow.size(); i++) {
            Klient klient = listaKlientow.get(i);
            printKlientDetails(klient);
        }
    }

    private void printKlientDetails(Klient klient) {
        System.out.print(klient.getImie() + " ");
        System.out.print(klient.getNazwisko() + " ");
        System.out.print(klient.getLogin() + " ");
        System.out.print(klient.getHaslo() + " ");
        System.out.print(klient.getWiek() + " ");
        System.out.print(klient.getState() + "\n");
    }

    private void printMovies(ArrayList<Film> movieList) {
        System.out.println(movieList.size());
        for (int i = 0; i < movieList.size(); i++) {
            Film film = movieList.get(i);
            printMovieDetails(film);

        }
    }

    private void printMovieDetails(Film film) {
        System.out.print(film.getTytul() + " ");
        System.out.print(film.getGatunek() + " ");
        System.out.print(film.getImie() + " ");
        System.out.print(film.getNazwisko() + " ");
        System.out.print(film.getRokProdukcji() + " ");
        System.out.print(film.getWytwornia() + "\n");
    }

    private void readJson(String clientsPath, String moviesPath, String reservationPath) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(clientsPath)) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray klienciList = (JSONArray) obj;


            //Iterate over employee array
            klienciList.forEach(emp -> parseClientObject((JSONObject) emp));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //JSON parser object to parse read file
        jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(moviesPath)) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray moviesList = (JSONArray) obj;


            //Iterate over employee array
            moviesList.forEach(emp -> parseMovieObject((JSONObject) emp));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //JSON parser object to parse read file
        jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(reservationPath)) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray reservationList = (JSONArray) obj;


            //Iterate over employee array
            reservationList.forEach(emp -> parseReservationObject((JSONObject) emp));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void parseReservationObject(JSONObject reservation) {
        JSONObject reservationObject = (JSONObject) reservation.get("rezerwacja");

        String title = (String) reservationObject.get("tytul");

        String login = (String) reservationObject.get("login");

        aplikacja.entities.Rezerwacja rezerwacja = new Rezerwacja(title, login);
        reservationList.add(rezerwacja);
        System.out.println("Dodano rezerwacje");
    }

    private void parseMovieObject(JSONObject movie) {

        JSONObject movieObject = (JSONObject) movie.get("film");

        String title = (String) movieObject.get("tytul");

        String genre = (String) movieObject.get("gatunek");

        String firstName = (String) movieObject.get("imie");

        String lastName = (String) movieObject.get("nazwisko");

        long year = (long) movieObject.get("rok produkcji");

        String works = (String) movieObject.get("wytwornia");

        long howMuch = (long) movieObject.get("ilosc egzemplarzy");

        Film film = new Film(title, genre, firstName, lastName, year, works, howMuch);
        movieList.add(film);
        System.out.println("Dodano film");
    }

    private void parseClientObject(JSONObject klienci) {
        //Get employee object within list
        JSONObject klientObject = (JSONObject) klienci.get("klient1");

        String imie = (String) klientObject.get("imie");

        String nazwisko = (String) klientObject.get("nazwisko");

        String login = (String) klientObject.get("login");

        String haslo = (String) klientObject.get("haslo");

        long wiek = (long) klientObject.get("wiek");

        Boolean state = (Boolean) klientObject.get("state");

        Klient klient = new Klient(login, haslo, imie, nazwisko, wiek, state);
        listaKlientow.add(klient);
        System.out.println("Dodano klienta");
    }

    private void sendRequestForNewAccount() {
        Scanner scan = new Scanner(System.in);
        String nazwisko = scan.nextLine();
        String imie = scan.nextLine();
        String login = scan.nextLine();
        String haslo = scan.nextLine();
        long wiek = scan.nextInt();
        Klient klient = new Klient(login, haslo, imie, nazwisko, wiek, false);
        listaKlientow.add(klient);
        System.out.println("Wysylano prosbe o zalozenie konta.");
    }

    private void verifyClients(ArrayList<Klient> listaKlientow) throws IOException {
        char endVerify = 'T';
        char isVerified;
        Scanner scan = new Scanner(System.in);
        int i = 0;
        do {
            if (!listaKlientow.get(i).getState()) {
                printKlientDetails(listaKlientow.get(i));
                System.out.println("Czy potwierdzasz weryfikacje? (T/N)");
                isVerified = scan.next().charAt(0);
                if (isVerified == 'T' || isVerified == 't') {
                    listaKlientow.get(i).setState(true);
                } else {
                    listaKlientow.remove(i);
                }
                System.out.println("Kontynuowac? (T/N)");
                endVerify = scan.next().charAt(0);
            } else {
                endVerify = 'T';
            }
            i++;

        } while ((endVerify == 'T' || endVerify == 't') && i < listaKlientow.size() - 1);
        System.out.println("Wszyscy klienci zostali zweryfikowani");
        char ch = (char) System.in.read();
    }

    private void editClients(ArrayList<Klient> listaKlientow) {
        String login;
        char endEdit = 'T';
        do {
            Scanner scan = new Scanner(System.in);
            System.out.println("Podaj login klienta, ktorego dane chcesz edytować: ");
            login = scan.nextLine();

            boolean isFound = false;
            for (int i = 0; i < listaKlientow.size(); i++) {
                if (listaKlientow.get(i).getLogin().equals(login)) {
                    Scanner scanEdit = new Scanner(System.in);
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Co chcesz edytowac: ");

                    System.out.println("1. Nazwisko");
                    System.out.println("2. Imie");
                    System.out.println("3. Login");
                    System.out.println("4. Haslo");
                    System.out.println("5. Wiek");
                    int whatEdit = scanEdit.nextInt();

                    switch (whatEdit) {
                        case 1:
                            System.out.println("Podaj nowe nazwisko: ");
                            String nazwisko = scanner.nextLine();
                            listaKlientow.get(i).setNazwisko(nazwisko);
                            break;
                        case 2:
                            System.out.println("Podaj nowe imie: ");
                            String imie = scanner.nextLine();
                            listaKlientow.get(i).setImie(imie);
                            break;
                        case 3:
                            System.out.println("Podaj nowy login: ");
                            String newLogin = scanner.nextLine();
                            listaKlientow.get(i).setLogin(newLogin);
                            break;
                        case 4:
                            System.out.println("Podaj nowe haslo: ");
                            String haslo = scanner.nextLine();
                            listaKlientow.get(i).setHaslo(haslo);
                            break;
                        case 5:
                            System.out.println("Podaj nowy wiek: ");
                            long wiek = scanner.nextLong();
                            listaKlientow.get(i).setWiek(wiek);
                            break;
                    }
                    isFound = true;
                    break;
                }
            }

            if (!isFound)
                System.out.println("Nie znaleziono klienta o takim loginie.");

            System.out.println("Kontynuowac? (T/N)");
            endEdit = scan.next().charAt(0);

        } while (endEdit == 'T' || endEdit == 't');
    }

    private void editMovie(ArrayList<Film> movieList) {
        String title;
        char endEdit = 'T';
        do {
            Scanner scan = new Scanner(System.in);
            System.out.println("Podaj tytul filmu, ktory chcesz edytować: ");
            title = scan.nextLine();

            boolean isFound = false;
            for (int i = 0; i < movieList.size(); i++) {
                System.out.println(movieList.get(i).getTytul() + " " + title);
                if (movieList.get(i).getTytul().equals(title)) {
                    Scanner scanEdit = new Scanner(System.in);
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Co chcesz edytowac: ");

                    System.out.println("1. Tytul");
                    System.out.println("2. Gatunek");
                    System.out.println("3. Imie rezysera");
                    System.out.println("4. Nazwisko rezysera");
                    System.out.println("5. Rok produkcji");
                    System.out.println("6. Nazwe wytworni");
                    int whatEdit = scanEdit.nextInt();

                    switch (whatEdit) {
                        case 1:
                            System.out.println("Podaj nowy tytul: ");
                            String newTitle = scanner.nextLine();
                            movieList.get(i).setTytul(newTitle);
                            break;
                        case 2:
                            System.out.println("Podaj nowy gatunek: ");
                            String genre = scanner.nextLine();
                            movieList.get(i).setGatunek(genre);
                            break;
                        case 3:
                            System.out.println("Podaj nowe imie rezysera: ");
                            String name = scanner.nextLine();
                            movieList.get(i).setImie(name);
                            break;
                        case 4:
                            System.out.println("Podaj nowe nazwisko rezysera: ");
                            String surname = scanner.nextLine();
                            movieList.get(i).setNazwisko(surname);
                            break;
                        case 5:
                            System.out.println("Podaj nowy rok produkcji: ");
                            long year = scanner.nextLong();
                            movieList.get(i).setRokProdukcji(year);
                            break;
                        case 6:
                            System.out.println("Podaj nowa wytwornie: ");
                            String works = scanner.nextLine();
                            movieList.get(i).setWytwornia(works);
                            break;
                    }
                    isFound = true;
                    break;
                }
            }

            if (!isFound)
                System.out.println("Nie znaleziono filmu o takim tytule.");

            System.out.println("Kontynuowac? (T/N)");
            endEdit = scan.next().charAt(0);

        } while (endEdit == 'T' || endEdit == 't');
    }

    private void addMovie(ArrayList<Film> movieList) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj tytul: ");
        String title = scanner.nextLine();
        System.out.println("Podaj gatunek: ");
        String genre = scanner.nextLine();
        System.out.println("Podaj imie rezysera: ");
        String firstName = scanner.nextLine();
        System.out.println("Podaj nazwisko rezysera: ");
        String lastName = scanner.nextLine();
        System.out.println("Podaj wytwornie: ");
        String works = scanner.nextLine();
        System.out.println("Podaj rok produkcji: ");
        long year = scanner.nextLong();
        System.out.println("Podaj ilosc egzemplarzy: ");
        long howMuch = scanner.nextLong();

        Film film = new Film(title, genre, firstName, lastName, year, works, howMuch);
        movieList.add(film);
        System.out.println("Dodano film");
    }

    private void removeMovie(ArrayList<Film> movieList) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj tytul filmu, ktory chcesz usunac: ");
        String title = scanner.nextLine();

        boolean isFound = false;
        for (int i = 0; i < movieList.size(); i++) {
            if (movieList.get(i).getTytul().equals(title)) {
                movieList.remove(i);
                isFound = true;
            }
        }

        if (!isFound)
            System.out.println("Nie ma takiego filmu.");
    }

}