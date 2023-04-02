package vn.edu.ptit.znine.service;

import vn.edu.ptit.znine.model.Account;

public interface AccountService {
	public Account findById(String username, String pass);
	public Account checkAccountName(String name);
	public Account checkAccountEmail(String email);
	public void addAccount(Account account);
	
}
