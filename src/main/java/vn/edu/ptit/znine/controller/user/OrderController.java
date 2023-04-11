package vn.edu.ptit.znine.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderController {
	@PostMapping("/books/order")
	public String addOrderBook() {
		return null;
	}
}
