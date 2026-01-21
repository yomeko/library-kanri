package model;

/**
 * 貸出台帳モデル
 */
public class Lend {

    private String name;      // ユーザ名
    private String bookname;  // 書籍名

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }
}