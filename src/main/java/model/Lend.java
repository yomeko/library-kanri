package model;

import java.sql.Date;

/**
 * 貸出台帳モデル
 * ・誰が
 * ・どの本を
 * ・いつ借りたか
 * を管理するクラス
 */
public class Lend {

    /** ユーザ名 */
    private String name;

    /** 書籍名 */
    private String bookname;

    /** 借りた日（lend テーブルの lend_date） */
    private Date lendDate;   // ★追加

    /**
     * ユーザ名を取得
     * @return ユーザ名
     */
    public String getName() {
        return name;
    }

    /**
     * ユーザ名を設定
     * @param name ユーザ名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 書籍名を取得
     * @return 書籍名
     */
    public String getBookname() {
        return bookname;
    }

    /**
     * 書籍名を設定
     * @param bookname 書籍名
     */
    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    /**
     * 借りた日を取得
     * @return 借りた日
     */
    public Date getLendDate() {
        return lendDate;
    }

    /**
     * 借りた日を設定
     * @param lendDate 借りた日
     */
    public void setLendDate(Date lendDate) {
        this.lendDate = lendDate;
    }
}