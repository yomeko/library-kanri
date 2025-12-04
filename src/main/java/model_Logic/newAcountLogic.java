package model_Logic;

import DAO.newAcountDAO;
import model.User;

public class newAcountLogic {

    public boolean execute(User user) {
        newAcountDAO dao = new newAcountDAO();
        return dao.create(user);   // ← ここで DB に登録
    }
}
