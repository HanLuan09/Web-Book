package vn.edu.ptit.znine.controller.user;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletResponse;
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
	public String getOrderBook(Model model, HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
		Account account = sesion.get("account");
		if(account == null) {
			return "login_register";
		}
		int idA = account.getIdA();
		try {
			List<UserProduct> listUserProducts = orderService.getOrderBook(idA+"");
			Collections.sort(listUserProducts);
			Map<Integer, List<UserProduct>> map = new LinkedHashMap<>();
			
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
	    	model.addAttribute("account", account);
	    	model.addAttribute("checkB", listUserProducts.size());
	    	model.addAttribute("checkDisplay", 1);
			model.addAttribute("listUserProducts", map);
			model.addAttribute("listUserProduct", listUserProducts);
			return "user_product";
		} catch (Exception e) {
			return "error";
		}
		
	}
// Trang chi tiết sản phẩm đã mua
	@GetMapping("/order-detail")
	public String getOrderBookDetail(Model model, @RequestParam("idb") String idB, @RequestParam("ido") String idO, HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
		try {
			
			Account account = sesion.get("account");
			if(account == null) {
				return "login_register";
			}
			UserProductDetail userProductDetail = ratingService.getRatingByIdOanhIdB(idO, idB);
			RatingProduct ratingProduct = ratingService.getRatingOneProductBook(account.getIdA()+"", idB, idO);
			int timeR = ratingService.timeRating(account.getIdA(), Integer.parseInt(idO), Integer.parseInt(idB));
			
			model.addAttribute("checkR", ratingProduct.getIdO()!=0 ? 1 : 0);
			model.addAttribute("timeR", timeR);
			model.addAttribute("userPDetail", userProductDetail);
			model.addAttribute("rProduct", ratingProduct);
			return "user_product_details";
		} catch (Exception e) {
			return "error";
		}
	}
	@GetMapping("/remove-order")
	public String removeOrderBook(Model model, @RequestParam("idb") String idB, @RequestParam("ido") String idO) {
		try {			
			Account account = sesion.get("account");
			if(account == null) {
				return "login_register";
			}
			int ido = Integer.parseInt(idO);
			int idb = Integer.parseInt(idB);
			int result = orderService.removeOrderBook(ido, idb);
			if(result == 0) return "error";
			return "redirect:/order";
		} catch (Exception e) {
			return "error";
		}
	}
	@PostMapping("/order/save/{rating}")
	public String postOrderBookDetail(RedirectAttributes redirectAttributes, RatingProduct ratingProduct, @RequestParam("idb") String idB, @RequestParam("ido") String idO) {
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
				int result = ratingService.addRatingProduct(ratingProduct);
				if(result == 0) return "error";
				redirectAttributes.addFlashAttribute("success", "Đánh giá thành công! Cảm ơn bạn đã đánh giá sản phẩm.");
				return "redirect:/order-detail?idb="+idB+"&ido="+idO;
			}
			
		} catch (Exception e) {
			return "error";
		}
	}
	@PutMapping("/order/save/{rating}")
	public String putOrderBookDetail(RedirectAttributes redirectAttributes, RatingProduct ratingProduct, @RequestParam("idb") String idB, @RequestParam("ido") String idO) {
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
			
			int result = ratingService.saveRatingProduct(ratingProduct);
			if(result == 0) return "error";
		} catch (Exception e) {
			return "error";
		}
		redirectAttributes.addFlashAttribute("success", "Đánh giá thành công! Cảm ơn bạn đã đánh giá sản phẩm.");
		return "redirect:/order-detail?idb="+idB+"&ido="+idO;
	}
	
	@PostMapping("/save-imageA")
	public String saveImageAccount(@RequestParam("imageAccount") MultipartFile file, RedirectAttributes redirectAttributes) {
		Account account = sesion.get("account");
		if(account == null) {
			return "login_register";
		}
		
		try {
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			if(fileName == null || fileName.equals("")) {
			}
			else {
				account.setImageA(fileName);
				String upLoadDir = "uploads/";
				FileUploadUtil.saveFile(upLoadDir, fileName, file);
			}
			int idA = account.getIdA();		
			sesion.set("account", account);
			int result = accountService.saveImageAccount(account.getImageA(), idA);
			if(result == 0) return "error";
		} catch (Exception e) {
			return "error";
		}
		redirectAttributes.addFlashAttribute("check", 2);
		return "redirect:/order";
	}
	@PostMapping("/save-pass")
	public String savePassAccount(RedirectAttributes redirectAttributes, @RequestParam("pass_ord") String passOld, 
			@RequestParam("pass_new") String passNew, @RequestParam("comfim_pass") String comfimPass) {
		Account account = sesion.get("account");
		if(account == null) {
			return "login_register";
		}
		
		try {
			if(!passNew.equals(comfimPass)) {
				redirectAttributes.addFlashAttribute("success", "Mật khẩu nhập lại không chính xác");
				redirectAttributes.addFlashAttribute("check", 2);
				return "redirect:/order";
			}
			if(accountService.checkPassAccount(passOld, account.getIdA()) == null) {
				redirectAttributes.addFlashAttribute("success", "Mật khẩu không chính xác");
				redirectAttributes.addFlashAttribute("check", 2);
				return "redirect:/order";
			}		
			int result = accountService.savePassAccount(passNew, account.getIdA());
			if(result == 0) return "error";
		} catch (Exception e) {
			return "error";
		}
		redirectAttributes.addFlashAttribute("success", "Thay đổi mật khẩu thành công!");
		redirectAttributes.addFlashAttribute("check", 2);
		return "redirect:/order";
	}
	@PostMapping("buy-book")
	public String buyBook(Model model ,@RequestParam("idb") String idB, @RequestParam("quantity") String quantity) {
		Product p = productService.getProductById(idB);
		model.addAttribute("product", p);
		model.addAttribute("quantity", quantity);
		return "orderbook";
	}
	@PostMapping("order-pay")
	public String orderPayBook(RedirectAttributes redirectAttributes ,@RequestParam("idb") String idB, @RequestParam("quantity") String quantity) {
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
			    int result = orderService.addOrderDetailsBook(orderDetails);
			    if(result == 0) return "error";
			}
			redirectAttributes.addFlashAttribute("success", "Đặt hàng thành công");
			return "redirect:/order";
			
		} catch (Exception e) {
			return "error";
		}
	}
}
