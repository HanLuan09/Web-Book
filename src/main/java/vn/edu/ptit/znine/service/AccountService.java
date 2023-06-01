package vn.edu.ptit.znine.service;

import vn.edu.ptit.znine.model.Account;

public interface AccountService {
	public Account findById(String username, String pass);
	public Account checkAccountName(String name);
	public Account checkAccountEmail(String email);
	public int addAccount(Account account);
	public int saveImageAccount(String imageA, int idA);
	public Account checkPassAccount(String pass, int idA );
	public int savePassAccount(String pass, int idA);
}
