package model;

public class Book {

    private int id;
    private String book;
    private int number;

    // ★ 追加：図書の詳細情報（あらすじ・説明など）
    private String detail;

    // ★ 既存：ログインユーザーが既に借りているか
    private boolean alreadyLent;

    // ---------- id ----------
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // ---------- book ----------
    public String getBook() {
        return book;
    }
    public void setBook(String book) {
        this.book = book;
    }

    // ---------- number ----------
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    // ---------- detail ----------
    // ★ 追加
    public String getDetail() {
        return detail;
    }

    // ★ 追加
    public void setDetail(String detail) {
        this.detail = detail;
    }

    // ---------- alreadyLent ----------
    public boolean isAlreadyLent() {
        return alreadyLent;
    }
    public void setAlreadyLent(boolean alreadyLent) {
        this.alreadyLent = alreadyLent;
    }
}