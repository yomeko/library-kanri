package model;

public class User {

    private String name;
    private String pass;

    public User() {}

    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public String getName() { return name; }
    public String getPass() { return pass; }
}