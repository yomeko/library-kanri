package model;

public class Lend {

    private String name;
    private String bookname;

    public Lend() {}

    public Lend(String name, String bookname) {
        this.name = name;
        this.bookname = bookname;
    }

    public String getName() {
        return name;
    }

    public String getBookname() {
        return bookname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }
}