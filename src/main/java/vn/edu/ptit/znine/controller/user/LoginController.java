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
	public String viewLogin(Model model) {
		String name = cookie.getValue("nameP","");
		String pass = cookie.getValue("passP","");
		
		model.addAttribute("passC",pass);
		model.addAttribute("nameC",name);
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
	    
	    return "login_register";
	}
	@GetMapping("/logout")
	public String logout() {
		sesion.remove("usernameS");
		return "redirect:/webbook"; 
	}
	
	@PostMapping("/register")
	public String register(@Validated Account account, BindingResult errors, Model model, @RequestParam("fullname") String name, @RequestParam("password") String password,
			@RequestParam("password_comfirmation") String comPassword, @RequestParam("email") String email) {
//	    if(errors.hasErrors()) {
//	    	return "login_register";
//	    }
	    try {
			Account aName = accService.checkAccountName(name);
			Account aEmail = accService.checkAccountEmail(email);
			if(aName!= null) {
				model.addAttribute("mess", "Tên đã tồn tại!");
				
			}
			else if(aEmail!= null) {
				model.addAttribute("mess", "Email đã tồn tại!");
				
			}
			else {
				
				account.setUsername(name);
				account.setEmail(email);
				account.setPassword(password);
				accService.addAccount(account);
				return "redirect:/webbook"; 
			}
		} catch (Exception e) {
			model.addAttribute("mess", "Không thể đăng ký tài khoản!");
			 
		}
	    return "login_register";
	}


}
