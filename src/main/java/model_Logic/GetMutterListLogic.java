package model_Logic;

import java.util.List;

import DAO.MutterDAO;
import model.Mutter;

public class GetMutterListLogic {
	public List<Mutter> execute() {
		MutterDAO dao = new MutterDAO();
		List<Mutter> mutterList = dao.findAll();
		return mutterList;
	}
}
