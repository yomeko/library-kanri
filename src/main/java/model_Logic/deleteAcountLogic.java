package model_Logic;

import DAO.deleteAcountDAO;
import model.User;

public class deleteAcountLogic {
	
	private String errorMessage;

	public boolean deletem(User user) {
		try {
			deleteAcountDAO dao = new deleteAcountDAO();
			return dao.deletem(user);
		} catch (Exception e) {
			this.errorMessage = "アカウント削除中にエラーが発生しました。";
			e.printStackTrace();
			return false;
		}
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
