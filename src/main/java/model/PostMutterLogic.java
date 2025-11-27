package model;

import DAO.MutterDAO;

public class PostMutterLogic {
	public void execute(Mutter mutter) {
		MutterDAO dao = new MutterDAO();
		dao.create(mutter);
	}
}
