package vn.edu.ptit.znine.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.websocket.Session;
import vn.edu.ptit.znine.dao.*;
import vn.edu.ptit.znine.model.*;
import vn.edu.ptit.znine.service.CategoryService;
import vn.edu.ptit.znine.service.ProductService;
import vn.edu.ptit.znine.service.SessionService;

import org.springframework.ui.Model;

@Controller
public class HomeController {
	 @Autowired
	 SessionService sesion;
	 @Autowired
	 ProductService proService;
	 @Autowired
	 CategoryService cateService;
	 
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
			 List<Product> listP = proService.getAllProduct();
			 List<Product> listTopP = proService.getTop10Product();
			 List<Category> listC = cateService.getAllCategory();
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
	     Product product = proService.getProductById(idB);
	     int cateId = product.getCateId();
	     List<Product> listP = proService.getAllProductByCateId(cateId+"");
	     model.addAttribute("listProduct", listP);
	     model.addAttribute("book", product);
		 return "product_details"; 
	 }// chi tiết sản phẩm
	 
	 @GetMapping("/books/{cateId}")
	 public String getAllProductByCateId(Model model, @PathVariable String cateId){
	     List<Category> listC = cateService.getAllCategory();
	     List<Product> listP = proService.getAllProductByCateId(cateId);
	     model.addAttribute("listProduct", listP);
	     model.addAttribute("listCategory", listC);
	     model.addAttribute("active", cateId);
		 return "category_product";
	 }//tìm sản phẩm 
	 @GetMapping("/books/search")
	 public String getAllProductSearch(Model model, @RequestParam("search") String search){
	     List<Category> listC = cateService.getAllCategory();
	     List<Product> listP = proService.getAllProductSearch(search);
	     model.addAttribute("listProduct", listP);
	     model.addAttribute("listCategory", listC);
		 return "category_product";
	 }//tìm sản phẩm 
	 @GetMapping("/books/sort-selling")
	 public String getAllProductSelling(Model model){
	     List<Category> listC = cateService.getAllCategory();
	     List<Product> listP = proService.getAllProductSelling();
	     model.addAttribute("listProduct", listP);
	     model.addAttribute("listCategory", listC);
		 return "category_product";
	 }//tìm sản phẩm 
	 @GetMapping("/books/sort-new")
	 public String getAllProductNew(Model model){
	     List<Category> listC = cateService.getAllCategory();
	     List<Product> listP = proService.getAllProductNew();
	     model.addAttribute("listProduct", listP);
	     model.addAttribute("listCategory", listC);
		 return "category_product";
	 }//tìm sản phẩm 
	 @GetMapping("/books/sort-like")
	 public String getAllProductLike(Model model){
	     List<Category> listC = cateService.getAllCategory();
	     List<Product> listP = proService.getAllProductLike();
	     model.addAttribute("listProduct", listP);
	     model.addAttribute("listCategory", listC);
		 return "category_product";
	 }//tìm sản phẩm 
}
