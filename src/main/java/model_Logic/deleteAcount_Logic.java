package model_Logic;

import dao.UserDao;

public class deleteAcount_Logic {

    public boolean execute(String name, String pass) {
        UserDao dao = new UserDao();
        return dao.deleteByNameAndPass(name, pass);
    }
}