package vn.edu.ptit.znine.controller.user;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vn.edu.ptit.znine.context.FileUploadUtil;
import vn.edu.ptit.znine.model.Account;
import vn.edu.ptit.znine.model.Order;
import vn.edu.ptit.znine.model.OrderDetails;
import vn.edu.ptit.znine.model.Product;
import vn.edu.ptit.znine.model.RatingProduct;
import vn.edu.ptit.znine.model.UserProduct;
import vn.edu.ptit.znine.model.UserProductDetail;
import vn.edu.ptit.znine.service.AccountService;
import vn.edu.ptit.znine.service.OrderService;
import vn.edu.ptit.znine.service.ProductService;
import vn.edu.ptit.znine.service.RatingService;
import vn.edu.ptit.znine.service.SessionService;

@Controller
public class OrderController {
	@Autowired
	SessionService sesion;
	@Autowired
	ProductService productService; 
	@Autowired
	OrderService orderService;
	@Autowired
	RatingService ratingService;
	@Autowired
	AccountService accountService;
	
	@GetMapping("/order")
	public String getOrderBook(Model model) {
		Account account = sesion.get("account");
		if(account == null) {
			return "login_register";
		}
		int idA = account.getIdA();
		try {
			List<UserProduct> listUserProducts = orderService.getOrderBook(idA+"");
			Map<Integer, List<UserProduct>> map = new HashMap<>();

	    	for (UserProduct o : listUserProducts) {
	    	    if (map.containsKey(o.getIdO())) {
	    	        // Nếu khóa đã tồn tại trong map, thêm đối tượng UserProduct vào danh sách tương ứng
	    	        map.get(o.getIdO()).add(o);
	    	    } else {
	    	        // Nếu khóa chưa tồn tại trong map, tạo danh sách mới và thêm đối tượng UserProduct vào danh sách đó
	    	        List<UserProduct> newList = new ArrayList<>();
	    	        newList.add(o);
	    	        map.put(o.getIdO(), newList);
	    	    }
	    	}
			model.addAttribute("listUserProducts", map);
			model.addAttribute("listUserProduct", listUserProducts);
			return "user_product";
		} catch (Exception e) {
			return "error";
		}
		
	}

	@GetMapping("/order-detail")
	public String getOrderBookDetail(Model model, @RequestParam("idb") String idB, @RequestParam("ido") String idO) {
		try {
			
			Account account = sesion.get("account");
			if(account == null) {
				return "login_register";
			}
			UserProductDetail userProductDetail = ratingService.getRatingByIdOanhIdB(idO, idB);
			RatingProduct ratingProduct = ratingService.getRatingOneProductBook(account.getIdA()+"", idB, idO);
			model.addAttribute("userPDetail", userProductDetail);
			model.addAttribute("rProduct", ratingProduct);
			return "user_product_details";
		} catch (Exception e) {
			return "error";
		}
	}
	@PostMapping("/order/save/{rating}")
	public String postOrderBookDetail(Model model, RatingProduct ratingProduct, @RequestParam("idb") String idB, @RequestParam("ido") String idO) {
		LocalDate localDate = LocalDate.now();
        Date date = java.sql.Date.valueOf(localDate);
		Account account = sesion.get("account");
		if(account == null) {
			return "login_register";
		}
		try {
			ratingProduct.setDateRating(date);
			ratingProduct.setIdA(account.getIdA());
			ratingProduct.setIdB(Integer.parseInt(idB));
			ratingProduct.setIdO(Integer.parseInt(idO));
			if(ratingProduct.getRating()<=0) {
				return "redirect:/order-detail?idb="+idB+"&ido="+idO;
			}
			else {
				ratingService.addRatingProduct(ratingProduct);
				return "redirect:/order-detail?idb="+idB+"&ido="+idO;
			}
			
		} catch (Exception e) {
			return "error";
		}
	}
	@PutMapping("/order/save/{rating}")
	public String putOrderBookDetail(Model model, RatingProduct ratingProduct, @RequestParam("idb") String idB, @RequestParam("ido") String idO) {
		LocalDate localDate = LocalDate.now();
        Date date = java.sql.Date.valueOf(localDate);
		Account account = sesion.get("account");
		if(account == null) {
			return "login_register";
		}
		try {
			ratingProduct.setDateRating(date);
			ratingProduct.setIdA(account.getIdA());
			ratingProduct.setIdB(Integer.parseInt(idB));
			ratingProduct.setIdO(Integer.parseInt(idO));
			
			ratingService.saveRatingProduct(ratingProduct);
		} catch (Exception e) {
			return "error";
		}
		return "redirect:/order-detail?idb="+idB+"&ido="+idO;
	}
	
	@PostMapping("/save-imageA")
	public String saveImageAccount(@RequestParam("imageAccount") MultipartFile file) {
		Account account = sesion.get("account");
		if(account == null) {
			return "login_register";
		}
		
		try {
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			if(fileName == null || fileName.equals("")) {
			}
			else {
				String upLoadDir = "uploads/";
				FileUploadUtil.saveFile(upLoadDir, fileName, file);
			}
			int idA = account.getIdA();
			account.setImageA(fileName);
			sesion.set("account", account);
			accountService.saveImageAccount(fileName, idA);
		} catch (Exception e) {
			return "error";
		}
		return "redirect:/order";
	}
	@PostMapping("buy-book")
	public String buyBook(Model model ,@RequestParam("idb") String idB, @RequestParam("quantity") String quantity) {
		Product p = productService.getProductById(idB);
		model.addAttribute("product", p);
		model.addAttribute("quantity", quantity);
		return "order";
	}
	@PostMapping("order-pay")
	public String orderPayBook(Model model ,@RequestParam("idb") String idB, @RequestParam("quantity") String quantity) {
		try {
			Account account = sesion.get("account");
		    if(account == null) {
		        return "login_register";
		    }
			synchronized (this) {
			    LocalDate localDate = LocalDate.now();
			    Date date = java.sql.Date.valueOf(localDate);
			    Order order = new Order(0, date, account.getIdA());
			    int idO = orderService.addOrderBook(order);
			    if(idO == 0) return "error";
			    OrderDetails orderDetails = new OrderDetails(idO, Integer.parseInt(idB), Integer.parseInt(quantity), 0);
			    orderService.addOrderDetailsBook(orderDetails);
			}
			return "redirect:/order";
			
		} catch (Exception e) {
			return "error";
		}
	}
}
