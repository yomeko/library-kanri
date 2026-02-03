package model;

public class User {

    private int id;         // ←追加
    private String name;
    private String pass;

    public User() {}

    public User(int id, String name, String pass) {
        this.id = id;
        this.name = name;
        this.pass = pass;
    }

    // id getter/setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    // name getter/setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // pass getter/setter
    public String getPass() { return pass; }
    public void setPass(String pass) { this.pass = pass; }
}