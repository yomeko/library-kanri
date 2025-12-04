package model_Logic;

import DAO.newAcountDAO;
import model.User;

public class newAcountLogic {

    private String errorMessage;

    public boolean execute(User user) {
        try {
            newAcountDAO dao = new newAcountDAO();
            return dao.create(user);   // ← ここで DB に登録
        } catch (IllegalStateException e) {
            // 重複エラーなどの場合
            this.errorMessage = e.getMessage();
            return false;
        }
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
