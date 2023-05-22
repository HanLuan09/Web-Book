package vn.edu.ptit.znine.controller.admin;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletResponse;
import vn.edu.ptit.znine.context.FileUploadUtil;
import vn.edu.ptit.znine.model.Account;
import vn.edu.ptit.znine.model.Category;
import vn.edu.ptit.znine.model.Product;
import vn.edu.ptit.znine.model.ProductAdmin;
import vn.edu.ptit.znine.service.AccountService;
import vn.edu.ptit.znine.service.AdminService;
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
	AdminService adminService;
	@Autowired
	CategoryService cateService;
	@Autowired
	SessionService sesion;
	@Autowired
	CookieService cookie;
	
	@GetMapping("/admin")
	public String getAllProduct(Model model, HttpServletResponse response, @RequestParam(value = "search", required = false) String search){
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
		 
	     List<ProductAdmin> listP = new ArrayList<>();
	     if(search == null) listP = adminService.getAllProductCate();
	     else listP = adminService.getAllProductAdminSearch(search);
	     Collections.sort(listP);
	     String name = cookie.getValue("nameCAdmin","");
		 String pass = cookie.getValue("passCAdmin","");
		 
		 List<ProductAdmin> selectedProducts = new ArrayList<>();
		 int count = 0;
		 if(listP.size() > 0) {	 
			 selectedProducts = listP.subList(0, 12);
			 count = listP.size()/12;
			 if(listP.size()%12 != 0) count++;
		 }
		 model.addAttribute("nameCAdmin", name);
		 model.addAttribute("passCAdmin", pass);
		 model.addAttribute("count", count);
	     model.addAttribute("listP", selectedProducts);	     
		 return "manager_product";
	} //index
	@GetMapping("/admin-ajax")
	@ResponseBody
	public List<ProductAdmin> getAllProductAjax(HttpServletResponse response, @RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "spbatdau", required = false) String sp){
	     List<ProductAdmin> listP = new ArrayList<>();
	     if(search == null) listP = adminService.getAllProductCate();
	     else listP = adminService.getAllProductAdminSearch(search);
	     Collections.sort(listP);
	     
	     int spbatdau = 0;
	     if (sp != null) {
	         spbatdau = Integer.parseInt(sp);
	     }
	        
	     List<ProductAdmin> selectedProducts = new ArrayList<>();
	     if (spbatdau + 12 <= listP.size()) {
	         selectedProducts = listP.subList(spbatdau, spbatdau + 12);
	     } else {
	         selectedProducts = listP.subList(spbatdau, listP.size());
	     }
		 return selectedProducts;
	} //index
	
	@PostMapping("/admin")
	public String login(@RequestParam("name") String name, @RequestParam("password") String password, RedirectAttributes redirectAttributes) {
	    Account a = accService.findById(name, password);
	    if (a != null) {
	        if(a.getIsAdmin() !=1) {     	
	        	redirectAttributes.addFlashAttribute("messlogin", "Đây không phải tài khoản admin!");
	            return "redirect:/admin";
	        }
	        else {
	        	redirectAttributes.addFlashAttribute("messlogin", "");
				sesion.set("accountAdmin", a);
//	        	Lưu cookies
	        	cookie.addCookie("nameCAdmin", name, 60);
	        	cookie.addCookie("passCAdmin", password, 60);
	        	
	            return "redirect:/admin"; // chuyển hướng đến trang quản trị
	        }
	    } 
	    else {
	    	redirectAttributes.addFlashAttribute("messlogin", "Tên đăng nhập hoặc mật khẩu không đúng!");
	        AdminController a1 = new AdminController();
	        return "redirect:/admin";
            //return a1.getAllProduct(model);
        } 
	    
	}
	@GetMapping("/admin/logout")
	public String logout() {
		sesion.remove("accountAdmin");
		return "redirect:/admin"; 
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
		return "book_details";
	}
	//form
	@PostMapping("/admin/save/{id}")
	public String postBook(Model model, @Validated Product book, Errors error, @RequestParam("cateId") int cateId, @RequestParam("date") Date d,
			@PathVariable String id, @RequestParam("image") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException{ 
		if(error.hasErrors()) {
			return "book_details";
		}
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		String upLoadDir = "uploads/";
		FileUploadUtil.saveFile(upLoadDir, fileName, file);
		if(adminService.checkProductName(book.getNameB(), book.getAuthor(), id) != null) {
			redirectAttributes.addFlashAttribute("messError","Tên sách đã tồn tại!");
		}
		else {
			try {
				book.setCateId(cateId);
				book.setReleaseDate(d);
				book.setImageB(fileName);
				int result = adminService.addProduct(book);
				if(result ==0 ) return "error";
				redirectAttributes.addFlashAttribute("succesMess","Thêm sản phẩm thành công");
				return "redirect:/admin";
			} catch (Exception e) {
			}
		}
//		return "book_details";
		return "redirect:/admin/-1";
	}
//	sửa book
	@PutMapping("/admin/save/{idB}")
	public String putBook(Model model, @Validated Product book, Errors error, @RequestParam("cateId") int cateId, @RequestParam("date") Date d, 
			@PathVariable String idB, @RequestParam("image") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		int id= book.getIdB();
		Product p = proService.getProductById(id+"");
		if(fileName == null || fileName.equals("")) {
			book.setImageB(p.getImageB());
		}
		else {
			String upLoadDir = "uploads/";
			FileUploadUtil.saveFile(upLoadDir, fileName, file);
			book.setImageB(fileName);
		}
		book.setCateId(cateId);
		book.setReleaseDate(d);
		try {
			Product pCheck = adminService.checkProductName(book.getNameB(),book.getAuthor(), idB);
			if(pCheck != null){
				redirectAttributes.addFlashAttribute("messError","Tên sách đã tồn tại!");
			}
			else {
				int result = adminService.updateProduct(book);
				if(result == 0) return "error";
				redirectAttributes.addFlashAttribute("succesMess","Sửa sản phẩm thành công");
				return "redirect:/admin";
			}
		} catch (Exception e) {
			
		}
//		return "book_details";
		return "redirect:/admin/" +id;
	}
//	xóa book
	@DeleteMapping("/admin/delete")
	public String fromDeleteBook(@RequestParam("bookId") String id, RedirectAttributes redirectAttributes) {
		//adminService.deleteProduct(id);
		int result = adminService.removeProduct(id);
		if(result == 0) return "error";
		redirectAttributes.addFlashAttribute("succesMess","Xóa sản phẩm thành công");
		return "redirect:/admin";
	}
	
}
