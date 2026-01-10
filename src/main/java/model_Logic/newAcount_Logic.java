package model_Logic;

import dao.UserDao;
import model.User;

public class newAcount_Logic {

    public boolean execute(User user) {
        UserDao dao = new UserDao();
        return dao.create(user);
    }
}