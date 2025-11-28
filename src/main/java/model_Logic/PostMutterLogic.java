package model_Logic;

import DAO.MutterDAO;
import model.Mutter;

public class PostMutterLogic {
	public void execute(Mutter mutter) {
		MutterDAO dao = new MutterDAO();
		dao.create(mutter);
	}
}
