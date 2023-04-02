package vn.edu.ptit.znine.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.websocket.Session;
import vn.edu.ptit.znine.dao.*;
import vn.edu.ptit.znine.model.*;
import vn.edu.ptit.znine.service.SessionService;

import org.springframework.ui.Model;

@Controller
public class HomeController {
	 @Autowired
	 SessionService sesion;
	 @GetMapping({"/", "/webbook"})
	 public String webBook() {
		 return "index";
	 }
	 public boolean checkSecurity() {
		 String name = sesion.get("usernameS");
		 if(name!= null) {
			 return true;
		 }
		 return false;
	 }
	 @GetMapping({"/", "/home"})
	 public String getAllProduct(Model model){
		 if(checkSecurity()) { 
			 ProductDao daoProd = new ProductDao();
			 CategoryDao daoCate = new CategoryDao();
			 List<Product> listP = daoProd.getAllProduct();
			 List<Product> listTopP = daoProd.getTop10Product();
			 List<Category> listC = daoCate.getAllCategory();
			 model.addAttribute("listProduct", listP);
			 model.addAttribute("listTopProduct", listTopP);
			 model.addAttribute("listCategory", listC);
			 return "home";
		 }
		 return "redirect:/login";
	 } //index
	 //lấy 1 book
	 @GetMapping({"/", "/book/{idB}"})
	 public String getBook(Model model, @PathVariable String idB) {
		 ProductDao daoProd = new ProductDao();
	     Product product = daoProd.getProductById(idB);
	     int cateId = product.getCateId();
	     List<Product> listP = daoProd.getAllProductByCateId(cateId+"");
	     model.addAttribute("listProduct", listP);
	     model.addAttribute("book", product);
		 return "product_details"; 
	 }// chi tiết sản phẩm
	 @GetMapping("/books/{cateId}")
	 public String getAllProductByCateId(Model model, @PathVariable String cateId){
		 ProductDao daoProd = new ProductDao();
		 CategoryDao daoCate = new CategoryDao();
	     List<Category> listC = daoCate.getAllCategory();
	     List<Product> listP = daoProd.getAllProductByCateId(cateId);
	     model.addAttribute("listProduct", listP);
	     model.addAttribute("listCategory", listC);
	     model.addAttribute("active", cateId);
		 return "category_product";
	 }//tìm sản phẩm 
}
