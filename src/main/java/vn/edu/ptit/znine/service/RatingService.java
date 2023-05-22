package vn.edu.ptit.znine.service;

import java.util.List;

import vn.edu.ptit.znine.model.RatingAccount;
import vn.edu.ptit.znine.model.RatingCount;
import vn.edu.ptit.znine.model.RatingProduct;
import vn.edu.ptit.znine.model.UserProductDetail;

public interface RatingService {
	public List<RatingAccount> getRatingAccountBook(String idB);
	public List<RatingCount> getRatingCountProduct(String idB);
	public UserProductDetail getRatingByIdOanhIdB(String idO, String idB);
	public RatingProduct getRatingOneProductBook(String ida, String idB, String idO);
	public int addRatingProduct(RatingProduct rP);
	public int saveRatingProduct(RatingProduct rP);
	public int timeRating(int IdA, int IdO, int IdB);
}
