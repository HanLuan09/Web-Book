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
	public void addAccount(Account account) {
		dao.postAccount(account);
	}
	@Override
	public void saveImageAccount(String imageA, int idA) {
		dao.saveImageAccount(imageA, idA);
	}
}
