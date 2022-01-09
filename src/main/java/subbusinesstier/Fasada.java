package main.java.subbusinesstier;

import main.java.subbusinesstier.entities.Film;
import main.java.subbusinesstier.entities.Klient;
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
	Fabryka fabryka = new Fabryka();
	public Fasada() {}

	public static void main(String[] args) throws IOException {
		Fasada fasada = new Fasada();
		fasada.readJson("klienci.json", "filmy.json");
		while(true)
		{
			System.out.println("Wypozyczalnia filmow\n");
			System.out.println("1.Wyslij prosbe o zalozenie konta\n");
			System.out.println("2.Wyswietl klientow\n");
			System.out.println("3.Zapisz do pliku\n");
			System.out.println("4.Weryfikacja danych\n");
			System.out.println("5.Edycja danych klienta\n");
			System.out.println("6.Film\n");
			System.out.println("9.Zamknij\n");
			Scanner scan = new Scanner(System.in);
			int menuChoice = scan.nextInt();
			switch(menuChoice){
				case 1:
					fasada.sendRequestForNewAccount();
					break;
				case 2:
					fasada.showKlienci(fasada.listaKlientow);
					break;
				case 3:
					fasada.saveJson("klienci.json","film.json",fasada.listaKlientow, fasada.movieList);
					break;
				case 4:
					fasada.verifyClients(fasada.listaKlientow);
					break;
				case 5:
					fasada.editClients(fasada.listaKlientow);
					break;
				case 6:
					System.out.println("Wypozyczalnia filmow\n");
					System.out.println("1.Pokaz filmy\n");
					int filmChoice = scan.nextInt();
					switch(filmChoice) {
						case 1:
							fasada.printMovies(fasada.movieList);
							break;
						case 2:
							//fasada.addMovie(fasada.movieList);
							break;
						case 3:
						//	fasada.editMovie(fasada.movieList);
							break;
					}
					break;
				case 9:
					fasada.saveJson("klienci.json","filmy.json",fasada.listaKlientow,fasada.movieList);
					exit(0);
					break;
			}
		}
	}

	private void saveJson(String clientsPath,String moviesPath,ArrayList<Klient> clientList,ArrayList<Film> movieList) {
		JSONArray clientsJsonList = new JSONArray();
		clientList.forEach(klient -> saveClientToJson(klient,clientsJsonList));

		try (FileWriter file = new FileWriter("klienci.json")) {
			//We can write any JSONArray or JSONObject instance to the file
			file.write(clientsJsonList.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONArray moviesJsonList = new JSONArray();
		movieList.forEach(movie -> saveMovieToJson(movie,moviesJsonList));

		try (FileWriter file = new FileWriter("film.json")) {
			//We can write any JSONArray or JSONObject instance to the file
			file.write(moviesJsonList.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveClientToJson(Klient klient,JSONArray klienciJsonList) {
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

	private void saveMovieToJson(Film film,JSONArray moviesJsonList) {
		JSONObject movieDetails = new JSONObject();
		movieDetails.put("tytul", (String) film.getTytul());
		movieDetails.put("gatunek", (String) film.getGatunek());
		movieDetails.put("imie", (String) film.getImie());
		movieDetails.put("nazwisko", (String) film.getNazwisko());
		movieDetails.put("rok produkcji", film.getRokProdukcji());
		movieDetails.put("wytwornia", (String) film.getWytwornia());

		JSONObject movieObject = new JSONObject();
		movieObject.put("film", movieDetails);


		moviesJsonList.add(movieObject);
	}

	private void showKlienci(ArrayList<Klient> listaKlientow) {
		for(int i = 0; i < listaKlientow.size(); i++) {
			Klient klient = listaKlientow.get(i);
			printKlientDetails(klient);
		}
	}
	private void printKlientDetails(Klient klient) {
		System.out.print(klient.getImie() + " ");
		System.out.print(klient.getNazwisko()+ " ");
		System.out.print(klient.getLogin()+ " ");
		System.out.print(klient.getHaslo()+ " ");
		System.out.print(klient.getWiek()+ " ");
		System.out.print(klient.getState()+ "\n");
	}

	private void printMovies(ArrayList<Film> movieList) {
		System.out.println(movieList.size());
		for(int i = 0; i < movieList.size(); i++) {
			Film film = movieList.get(i);
			printMovieDetails(film);

		}
	}

	private void printMovieDetails(Film film) {
		System.out.print(film.getTytul()+ " ");
		System.out.print(film.getGatunek()+ " ");
		System.out.print(film.getImie() + " ");
		System.out.print(film.getNazwisko()+ " ");
		System.out.print(film.getRokProdukcji()+ " ");
		System.out.print(film.getWytwornia()+ "\n");
	}




	public void readJson(String clientsPath,String moviesPath)
	{
		//JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();

		try (FileReader reader = new FileReader(clientsPath))
		{
			//Read JSON file
			Object obj = jsonParser.parse(reader);

			JSONArray klienciList = (JSONArray) obj;


			//Iterate over employee array
			klienciList.forEach( emp -> parseClientObject( (JSONObject) emp ) );

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		//JSON parser object to parse read file
		jsonParser = new JSONParser();

		try (FileReader reader = new FileReader(moviesPath))
		{
			//Read JSON file
			Object obj = jsonParser.parse(reader);

			JSONArray moviesList = (JSONArray) obj;


			//Iterate over employee array
			moviesList.forEach( emp -> parseMovieObject( (JSONObject) emp ) );

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void parseMovieObject(JSONObject movie) {
		//Get employee object within list
		JSONObject movieObject = (JSONObject) movie.get("film");

		String title = (String) movieObject.get("tytul");

		String genre = (String) movieObject.get("gatunek");

		String firstName = (String) movieObject.get("imie");

		String lastName = (String) movieObject.get("nazwisko");

		long year = (long) movieObject.get("rok produkcji");

		String works = (String) movieObject.get("wytwornia");

		Film film = new Film(title,genre,firstName,lastName,year,works);
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

		Klient klient = new Klient(login,haslo,imie,nazwisko,wiek,state);
		listaKlientow.add(klient);
		System.out.println("Dodano klienta");
	}

	public void sendRequestForNewAccount()
	{
		Scanner scan = new Scanner(System.in);
		String nazwisko = scan.nextLine();
		String imie = scan.nextLine();
		String login = scan.nextLine();
		String haslo = scan.nextLine();
		long wiek = scan.nextInt();
		fabryka.stworzKlienta();
		//Klient klient = new Klient(login,haslo,imie,nazwisko,wiek,false);
		Klient klient = new Klient(login, haslo, imie, nazwisko, wiek, false);
		listaKlientow.add(klient);
		System.out.println("Wysylano prosbe o zalozenie konta.");
	}

	public void verifyClients(ArrayList<Klient> listaKlientow) throws IOException {
		char endVerify = 'T';
		char isVerified;
		Scanner scan = new Scanner(System.in);
		int i = 0;
		do{
			if (!listaKlientow.get(i).getState()) {
				printKlientDetails(listaKlientow.get(i));
				System.out.println("Czy potwierdzasz weryfikacje? (T/N)");
				isVerified = scan.next().charAt(0);
				if (isVerified == 'T' || isVerified == 't') {
					listaKlientow.get(i).setState(true);
				}
				else
				{
					listaKlientow.remove(i);
				}
				System.out.println("Kontynuowac? (T/N)");
				endVerify = scan.next().charAt(0);
			}else{
				endVerify = 'T';
			}
			i++;

		}while((endVerify == 'T' || endVerify == 't') && i < listaKlientow.size() - 1);
		System.out.println("Wszyscy klienci zostali zweryfikowani");
		char ch = (char) System.in.read();
	}

	public void editClients(ArrayList<Klient> listaKlientow) {
		String login;
		char endEdit = 'T';
		do{
			Scanner scan = new Scanner(System.in);
			System.out.println("Podaj login klienta, ktorego dane chcesz edytować: ");
			login = scan.nextLine();

			boolean isFound = false;
			for(int i = 0; i < listaKlientow.size(); i++){
				System.out.println(listaKlientow.get(i).getLogin() + " " + login);
				if(listaKlientow.get(i).getLogin().equals(login))
				{
					Scanner scanner=new Scanner(System.in);
					System.out.println("Podaj nowe nazwisko: ");
					String nazwisko = scanner.nextLine();
					listaKlientow.get(i).setNazwisko(nazwisko);
					System.out.println("Podaj nowe imie: ");
					String imie = scanner.nextLine();
					listaKlientow.get(i).setImie(imie);
					System.out.println("Podaj nowy login: ");
					String newLogin = scanner.nextLine();
					listaKlientow.get(i).setLogin(newLogin);
					System.out.println("Podaj nowe haslo: ");
					String haslo = scanner.nextLine();
					listaKlientow.get(i).setHaslo(haslo);
					System.out.println("Podaj nowy wiek: ");
					Long wiek = scanner.nextLong();
					listaKlientow.get(i).setWiek(wiek);
					isFound = true;
					break;
				}
			}

			if(!isFound)
				System.out.println("Nie znaleziono klienta o takim loginie.");

			System.out.println("Kontynuowac? (T/N)");
			endEdit = scan.next().charAt(0);

		}while(endEdit == 'T' || endEdit == 't');
	}
}