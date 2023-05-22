package vn.edu.ptit.znine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.ptit.znine.dao.RatingDao;
import vn.edu.ptit.znine.model.RatingAccount;
import vn.edu.ptit.znine.model.RatingCount;
import vn.edu.ptit.znine.model.RatingProduct;
import vn.edu.ptit.znine.model.UserProductDetail;

@Service
public class RatingServiceImpl implements RatingService {
	@Autowired
	private RatingDao daoRating;
	@Override
	public List<RatingAccount> getRatingAccountBook(String idB) {
		return daoRating.getRatingAccountBook(idB);
	}
	@Override
	public List<RatingCount> getRatingCountProduct(String idB) {
		return daoRating.getRatingCountProduct(idB);
	}
	@Override
	public UserProductDetail getRatingByIdOanhIdB(String idO, String idB) {
		return  daoRating.getRatingByIdOanhIdB(idO, idB);
	}
	@Override
	public RatingProduct getRatingOneProductBook(String ida, String idB, String idO) {
		return daoRating.getRatingOneProductBook(ida, idB, idO);
	}
	@Override
	public int addRatingProduct(RatingProduct rP) {
		return daoRating.addRatingProduct(rP);
	}
	@Override
	public int saveRatingProduct(RatingProduct rP) {
		return daoRating.saveRatingProduct(rP);
	}
	@Override
	public int timeRating(int IdA, int IdO, int IdB) {
		return daoRating.timeRating(IdA, IdO, IdB);
	}
}
