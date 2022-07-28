package application;

public class Member {

	String Name, Id, Password, Hak, Ban, Bun;

	public Member(String name, String id, String password, String hak, String ban, String bun) {
		super();
		Name = name;
		Id = id;
		Password = password;
		Hak = hak;
		Ban = ban;
		Bun = bun;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getHak() {
		return Hak;
	}

	public void setHak(String hak) {
		Hak = hak;
	}

	public String getBan() {
		return Ban;
	}

	public void setBan(String ban) {
		Ban = ban;
	}

	public String getBun() {
		return Bun;
	}

	public void setBun(String bun) {
		Bun = bun;
	}
	
	
	
}
