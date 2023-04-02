package vn.edu.ptit.znine.controller.admin;

import java.io.IOException;
import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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

@Controller
public class AdminController {
	@GetMapping({"/", "/admin"})
	public String getAllProduct(Model model){
		 AdminDao dao = new AdminDao();
	     List<ProductAdmin> listP = dao.getAllProductCate();
	     model.addAttribute("listP", listP);	     
		 return "manager_product";
	} //index
	 
	
	@PostMapping("/admin")
	public String login(HttpServletRequest request, HttpServletResponse response,@RequestParam("name") String name, @RequestParam("password") String password, Model model) {
	    AccountDao dao = new AccountDao();
	    Account a = dao.getAccount(name, password);
	    if (a != null) {
	        if(a.getIsAdmin() !=1) {
	            model.addAttribute("messlogin", "Đây không phải tài khoản admin!");
	            AdminController a1 = new AdminController();
	            return a1.getAllProduct(model);
	        }
	        else {
	        	model.addAttribute("messlogin", "");
	        	HttpSession session = request.getSession();
	        	session.setAttribute("accountAdmin", a);
//	        	Lưu cookies
	        	Cookie cookieName = new Cookie("nameCAdmin", name);
	        	Cookie cookiePass = new Cookie("passCAdmin", password);
//	        	 xét thời gian tồn tại
	        	cookieName.setMaxAge(60*60*24*90);
	        	cookiePass.setMaxAge(60*60*24*90);
//	        	lưu vào trình duyệt
	        	response.addCookie(cookieName);
	        	response.addCookie(cookiePass);
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
		ProductDao dao = new ProductDao();
		CategoryDao daoC = new CategoryDao();
		Product pBook = dao.getProductById(id);
		int cateId;
		if(pBook==null) {
			cateId=-1;
		}
		else {
			cateId =pBook.getCateId();
		}
		List<Category> listC = daoC.getAllCategoryExecpt(cateId+"");
		model.addAttribute("book", pBook);// có dữ liệu hay không// truyền trong object để lấy dữ liệu cho form
		model.addAttribute("listCate",listC);
		return "book_details";// bấm list book view hay add phải là get 
	}
	//form
	@PostMapping("/admin/save/{id}")
	public String postBook(Model model, @Validated Product book, Errors error, @RequestParam("cateId") int cateId, @RequestParam("date") Date d, @RequestParam("image") String image) throws ParseException, ParseException { 
//		ProductDao dao = new ProductDao();
//		book.setCateId(cateId);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		java.util.Date utilDate = sdf.parse(d);
//		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
//		book.setReleaseDate(utilDate);
//		book.setImageB(image);
//		return book;
//		dao.addProduct(book);
		
		if(error.hasErrors()) {
			return "book_details";
		}
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = 0;
		
		String query = "INSERT INTO DBO.Book([NameB], [ImageB], [title], [author], [releaseDate], [pages], [CateID]) \r\n"
  				+ "VALUES(?, ?, ?, ?, ?, ?, ?)";
	      try {
	          conn = new DbContext().getConnection();//mo ket noi voi sql
	          ps = conn.prepareStatement(query);
	          ps.setString(1, book.getNameB());
	          ps.setString(2, image);
	          ps.setString(3, book.getTitle());
	          ps.setString(4, book.getAuthor());
	          ps.setDate(5, d);
	          ps.setInt(6, book.getPages());
	          ps.setInt(7, cateId);
	          result = ps.executeUpdate();
	          conn.close();
	          ps.close();
	          return "redirect:/admin";
	      } catch (Exception e) {
	      }
//		return "book_details";
		return "redirect:/admin/-1";
	}
//	sửa book
	@PutMapping("/admin/save/{idB}")
	public String putBook(Model model, Product book, @RequestParam("cateId") int cateId, @RequestParam("date") String d, @RequestParam("image") String image) throws ParseException {
		//validate 
		//UPdate
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date utilDate = sdf.parse(d);
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		book.setCateId(cateId);
		book.setReleaseDate(sqlDate);
		book.setImageB(image);
		ProductDao dao = new ProductDao();
		if(dao.updateProduct(book) !=0) {
			return "redirect:/admin";
		}
//		dao.updateProduct(book);
		return "redirect:/admin/{id}";
		
	}
//	xóa book
	@DeleteMapping("/admin/delete")
	public String fromDeleteBook(@RequestParam("bookId") String id) {
		ProductDao dao = new ProductDao();
		dao.deleteProduct(id);
		return "redirect:/admin";
	}
	public static void main(String[] args) {
		ProductDao dao = new ProductDao();
		Product pBook = dao.getProductById("-1");
		System.out.println(pBook);
		
	}
}
