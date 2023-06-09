package vn.edu.ptit.znine.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.edu.ptit.znine.dao.AccountDao;
import vn.edu.ptit.znine.model.Account;
import vn.edu.ptit.znine.service.AccountService;
import vn.edu.ptit.znine.service.CookieService;
import vn.edu.ptit.znine.service.SessionService;

@Controller
public class LoginController {
	@Autowired
	AccountService accService;
	
	@Autowired
	SessionService sesion;
	@Autowired
	CookieService cookie;
	
	@GetMapping("/login")
	public String viewLogin(Model model, @RequestParam(value = "login", required = false) String login) {
		int check = 1;
		if(login == null || !login.equals("1")) {	
			String name = cookie.getValue("nameP","");
			String pass = cookie.getValue("passP","");
			check = 0;
			model.addAttribute("passC",pass);
			model.addAttribute("nameC",name);
		}
		
		model.addAttribute("checkLogin", check);
		return "login_register";
	}
	@PostMapping("/login")
	public String login(Model model, @RequestParam("name") String name, @RequestParam("password") String password) {
	    try {
			Account a = accService.findById(name,password);
			if(a==null) {
				model.addAttribute("mess", "Tên đăng nhập hoặc mật khẩu không đúng!");
			}
			else {
				String uri = sesion.get("security-uri");
				cookie.addCookie("nameP", name, 60);
				cookie.addCookie("passP", password, 60);
				if(uri != null) {
					return "redirect:/home";
				}else {
//					model.addAttribute("mess", "Sesion null!");
					if(a.getIsAdmin()==1) sesion.set("accountAdmin", a);
					sesion.set("usernameS", name);
					sesion.set("account", a);
					return "redirect:/home"; 
				}
				
			}
		} catch (Exception e) {
			model.addAttribute("mess", "Không thể đăng nhập!");
		}
	    model.addAttribute("checkLogin", 0);
	    return "login_register";
	}
	@GetMapping("/logout")
	public String logout() {
		sesion.remove("account");
		sesion.remove("usernameS");
		return "redirect:/webbook"; 
	}
	
	@PostMapping("/register")
	public String register(@Validated Account account, BindingResult errors, Model model, @RequestParam("fullname") String name, @RequestParam("password") String password,
			@RequestParam("password_comfirmation") String comPassword, @RequestParam("email") String email) {
	    try {
			Account aName = accService.checkAccountName(name);
			Account aEmail = accService.checkAccountEmail(email);
			if(aName!= null) {
				model.addAttribute("mess", "Tên người dùng đã tồn tại!");
				
			}
			else if(aEmail!= null) {
				model.addAttribute("mess", "Email người dùng đã tồn tại!");
				
			}
			else {
				
				account.setUsername(name);
				account.setEmail(email);
				account.setPassword(password);
				int result = accService.addAccount(account);
				if(result == 0) return "error";
				cookie.removeCookie("nameP");
				cookie.removeCookie("passP");
				return "redirect:/login"; 
			}
		} catch (Exception e) {
			model.addAttribute("mess", "Không thể đăng ký tài khoản!");
			 
		}
	    model.addAttribute("checkLogin", 1);
	    return "login_register";
	}


}
