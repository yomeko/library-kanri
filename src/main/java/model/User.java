package model;


public class User {

	private String name;
	//private String id;
	private String pass;
	
	public User() {}
	public User(String name, /*String id,*/ String pass) {
		this.name = name;
		//this.id = id;
		this.pass = pass;
	}
	
	public String getName() {return name;}
	//public String getId() {return id;}
    public String getPass() {return pass;}
	
}
