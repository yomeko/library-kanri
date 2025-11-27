package model;

import DAO.MutterDAO;

public class LoginLogic {
	public boolean execute(User user) {
		MutterDAO dao = new MutterDAO();
		if (dao.checkUser(user)) {
			return true;
		}
		return false;
	}
}
