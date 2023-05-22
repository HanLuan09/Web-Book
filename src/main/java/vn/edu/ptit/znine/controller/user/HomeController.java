package vn.edu.ptit.znine.controller.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import vn.edu.ptit.znine.model.*;
import vn.edu.ptit.znine.service.CategoryService;
import vn.edu.ptit.znine.service.ProductService;
import vn.edu.ptit.znine.service.RatingService;
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
	 @Autowired
	 RatingService ratingService;
	 public boolean checkSecurity() {
		 String name = sesion.get("usernameS");
		 if(name!= null) {
			 return true;
		 }
		 return false;
	 }
	 @GetMapping({"/", "/webbook"})
	 public String webBook(Model model) {
		 List<ProductDetail> listP = proService.getAllProductDetail();
		 List<ProductDetail> randomProducts = new ArrayList<>(listP); 
        	// Sử dụng đối tượng Random để lấy ngẫu nhiên các phần tử trong danh sách
		 Collections.shuffle(randomProducts, new Random());
		 ProductDetail productDetail = randomProducts.get(0);
		 List<ProductDetail> randomSelection1 = randomProducts.subList(1, 3);
		 List<ProductDetail> randomSelection2 = randomProducts.subList(3, 5);
		 Account account = sesion.get("account");
		 model.addAttribute("account", account);
		 model.addAttribute("productDetail", productDetail);
		 model.addAttribute("randomSelection1", randomSelection1);
		 model.addAttribute("randomSelection2", randomSelection2);
		 return "index";
	 }
	 
	 @GetMapping({"/", "/home"})
	 public String getAllProduct(Model model){
		 try {
			 if(checkSecurity()) { 
				 List<ProductDetail> listP = proService.getAllProductDetail();
				 Collections.sort(listP);
				 List<Product> listTopP = new ArrayList<>(listP);
				 List<Category> listC = cateService.getAllCategory();
				 List<ProductDetail> randomProducts = new ArrayList<>(listP); 
		        	// Sử dụng đối tượng Random để lấy ngẫu nhiên các phần tử trong danh sách
				 Collections.shuffle(randomProducts, new Random());
		        	// Lấy 16 sản phẩm đầu tiên trong danh sách đã được xáo trộn
				 List<ProductDetail> randomSelection = randomProducts.subList(0, 16);
				 
				 model.addAttribute("randomSelection", randomSelection);
				 model.addAttribute("listProduct", listP);
				 model.addAttribute("listTopProduct", listTopP);
				 model.addAttribute("listCategory", listC);
				 return "home";
			 }
			 return "redirect:/login";
		} catch (Exception e) {
			return "error";
		}
	 } //index
	 @GetMapping("account")
	 public String getAccount(){
		 return "myusers";
	 } //index
	 
	 //lấy 1 book
	 @GetMapping({"/", "/book/{idB}"})
	 public String getBook(Model model, @PathVariable String idB) {
		 try {
			 ProductDetail product = proService.getProductDetailById(idB);
		     int cateId = product.getCateId();
		     RatingCount ratingCount = new RatingCount();
		     List<RatingAccount> listRatingAccounts = ratingService.getRatingAccountBook(idB);
		     List<RatingCount> listRatingCounts = ratingService.getRatingCountProduct(idB);
		     List<ProductDetail> listP = proService.getAllProductDetailByCateId(cateId+"");
		     Collections.sort(listP);
		     model.addAttribute("listProduct", listP);
		     model.addAttribute("book", product);
		     model.addAttribute("listRatingAccounts", listRatingAccounts);
		     model.addAttribute("listRatingCounts", listRatingCounts);
		     model.addAttribute("countRating", ratingCount.countRating(listRatingCounts));
			 return "product_details"; 
		} catch (Exception e) {
			return "error";
		}
	     
	 }
	 
	 @GetMapping("/books/{cateId}")
	 public String getAllProductByCateId(Model model, @PathVariable String cateId){
		 try {
			 List<Category> listC = cateService.getAllCategory();
			 
		     List<ProductDetail> listP = new ArrayList<>();
		     if(Integer.parseInt(cateId)>0) listP = proService.getAllProductDetailByCateId(cateId);
		     else listP = proService.getAllProductDetail();
		     Collections.sort(listP);
		     model.addAttribute("listProduct", listP);
		     model.addAttribute("listCategory", listC);
		     model.addAttribute("active", cateId);
			 return "category_product";
		} catch (Exception e) {
			return "error";
		}
	     
	 }//tìm sản phẩm 
	 @GetMapping("/books/search")
	 public String getAllProductSearch(Model model, @RequestParam("search") String search){
		 try {
			 List<Category> listC = cateService.getAllCategory();
			 List<ProductDetail> listP = proService.getAllProductSearch(search);
			 Collections.sort(listP);
			 model.addAttribute("valuesearch", search);
			 model.addAttribute("listProduct", listP);
			 model.addAttribute("listCategory", listC);
			 return "category_product";
			
		} catch (Exception e) {
			return "error";
		}
	 }//tìm sản phẩm 
	 @GetMapping("/sort-books/{id}")
	 public String getAllProductSelling(Model model, @PathVariable int id){
		 try {
			
			 List<Category> listC = cateService.getAllCategory();
			 List<ProductDetail> listP = proService.getAllProductDetail();
			 if(id == 1) {
				 Collections.sort(listP, new SortByPopularity());
			 }else if(id==2) {
				 Collections.sort(listP, new SortBySelling());
			 }else if(id==3) {
				 Collections.sort(listP, new SortByNew());
			 }
			 model.addAttribute("listProduct", listP);
			 model.addAttribute("listCategory", listC);
			 return "category_product";
		} catch (Exception e) {
			return "error";
		}
	 }
}
