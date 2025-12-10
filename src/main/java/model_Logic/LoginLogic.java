package model_Logic;

import DAO.MutterDAO;
import model.User;

public class LoginLogic {
	public boolean loginL(User user) {
		MutterDAO dao = new MutterDAO();
		if (dao.checkUser(user)) {
			return true;
		} else {
			return false;
		}
	}
}
