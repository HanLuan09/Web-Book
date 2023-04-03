package vn.edu.ptit.znine.controller.admin;

import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.ptit.znine.context.DbContext;
import vn.edu.ptit.znine.dao.AccountDao;
import vn.edu.ptit.znine.dao.AdminDao;
import vn.edu.ptit.znine.dao.CategoryDao;
import vn.edu.ptit.znine.dao.ProductDao;
import vn.edu.ptit.znine.model.Account;
import vn.edu.ptit.znine.model.Category;
import vn.edu.ptit.znine.model.Product;
import vn.edu.ptit.znine.model.ProductAdmin;
import vn.edu.ptit.znine.service.AccountService;
import vn.edu.ptit.znine.service.CategoryService;
import vn.edu.ptit.znine.service.CookieService;
import vn.edu.ptit.znine.service.ProductService;
import vn.edu.ptit.znine.service.SessionService;

@Controller
public class AdminController {
	@Autowired
	AccountService accService;
	@Autowired
	ProductService proService;
	@Autowired
	CategoryService cateService;
	@Autowired
	SessionService sesion;
	@Autowired
	CookieService cookie;
	@GetMapping({"/", "/admin"})
	public String getAllProduct(Model model){
		 AdminDao dao = new AdminDao();
	     List<ProductAdmin> listP = dao.getAllProductCate();
	     model.addAttribute("listP", listP);	     
		 return "manager_product";
	} //index
	 
	
	@PostMapping("/admin")
	public String login(HttpServletRequest request, HttpServletResponse response,@RequestParam("name") String name, @RequestParam("password") String password, Model model) {
	    Account a = accService.findById(name, password);
	    if (a != null) {
	        if(a.getIsAdmin() !=1) {
	            model.addAttribute("messlogin", "Đây không phải tài khoản admin!");
	            AdminController a1 = new AdminController();
	            return a1.getAllProduct(model);
	        }
	        else {
	        	model.addAttribute("messlogin", "");
				sesion.set("accountAdmin", a);
//	        	Lưu cookies
	        	cookie.addCookie("nameCAdmin", name, 60);
	        	cookie.addCookie("passCAdmin", password, 60);
	        	
	            return "redirect:/admin"; // chuyển hướng đến trang quản trị
	        }
	    } 
	    else {
	        model.addAttribute("messlogin", "Tên đăng nhập hoặc mật khẩu không đúng!");
	        AdminController a1 = new AdminController();
            return a1.getAllProduct(model);
        } 
	    
	}
//	Get 1 book// xử lý chuyển trang 
	@GetMapping("/admin/{id}")
	public String getBook(Model model, @PathVariable String id){
		model.addAttribute("idBook", id);// xét xem dùng POST hay PUT truyen lên menthod
		Product pBook = proService.getProductById(id);
		int cateId;
		if(pBook==null) {
			cateId=-1;
		}
		else {
			cateId =pBook.getCateId();
		}
		List<Category> listC = cateService.getAllCategoryExecpt(cateId+"");
		model.addAttribute("book", pBook);// có dữ liệu hay không// truyền trong object để lấy dữ liệu cho form
		model.addAttribute("listCate",listC);
		return "book_details";// bấm list book view hay add phải là get 
	}
	//form
	@PostMapping("/admin/save/{id}")
	public String postBook(Model model, @Validated Product book, Errors error, @RequestParam("cateId") int cateId, @RequestParam("date") Date d, @RequestParam("image") String image) throws ParseException, ParseException { 
		if(error.hasErrors()) {
			return "book_details";
		}
		if(proService.checkProductName(book.getNameB()) != null) {
			model.addAttribute("messError","Tên sách đã tồn tại!");
		}
		else {
			try {
				book.setCateId(cateId);
				book.setReleaseDate(d);
				book.setImageB(image);
				proService.addProduct(book);
				return "redirect:/admin";
			} catch (Exception e) {
			}
		}
//		return "book_details";
		return "redirect:/admin/-1";
	}
//	sửa book
	@PutMapping("/admin/save/{idB}")
	public String putBook(Model model, Product book, @RequestParam("cateId") int cateId, @RequestParam("date") Date d, @RequestParam("image") String image) throws ParseException {
		//validate 
		//UPdate
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		java.util.Date utilDate = sdf.parse(d);
//		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
//		if(proService.checkProductName(book.getNameB()) != null)
		book.setCateId(cateId);
		book.setReleaseDate(d);
		book.setImageB(image);
		try {
			proService.updateProduct(book);
			return "redirect:/admin";
		} catch (Exception e) {
			
		}
//		return "book_details";
		int id= book.getIdB();
		return "redirect:/admin/" +id;
		
	}
//	xóa book
	@DeleteMapping("/admin/delete")
	public String fromDeleteBook(@RequestParam("bookId") String id) {
		proService.deleteProduct(id);
		return "redirect:/admin";
	}
	public static void main(String[] args) {
		ProductDao dao = new ProductDao();
		Product pBook = dao.getProductById("-1");
		System.out.println(pBook);
		
	}
}
