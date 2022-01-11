package aplikacja.entities;

public class Rezerwacja {

	private String title;
	private String login;

	public Rezerwacja(String title, String login) {
		this.title = title;
		this.login = login;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}