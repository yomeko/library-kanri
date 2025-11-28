package model_Logic;

import DAO.MutterDAO;
import model.User;

public class LoginLogic {
	public boolean execute(User user) {
		MutterDAO dao = new MutterDAO();
		if (dao.checkUser(user)) {
			return true;
		}
		return false;
	}
}
