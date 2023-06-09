package vn.edu.ptit.znine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.ptit.znine.dao.AccountDao;
import vn.edu.ptit.znine.model.Account;

@Service

public class AccountServiceImpl implements AccountService{
	@Autowired
	private AccountDao dao;
	@Override
	public Account findById(String username, String pass) {
		Account a = dao.getAccount(username, pass);
		if(a!= null) return a;
		return null;
	}
	@Override
	public Account checkAccountName(String name) {
		Account a = dao.checkAccount(name);
		if(a!= null) return a;
		return null;
	}
	@Override
	public Account checkAccountEmail(String email) {
		Account a = dao.checkAccount(email);
		if(a!= null) return a;
		return null;
	}
	@Override
	public int addAccount(Account account) {
		return dao.postAccount(account);
	}
	@Override
	public int saveImageAccount(String imageA, int idA) {
		return dao.saveImageAccount(imageA, idA);
	}
	@Override
	public Account checkPassAccount(String pass, int idA) {
		return dao.checkPassAccount(pass, idA);
	}
	@Override
	public int savePassAccount(String pass, int idA) {
		return dao.savePassAccount(pass, idA);
	}
}
