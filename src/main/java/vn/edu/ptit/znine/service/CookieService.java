package vn.edu.ptit.znine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CookieService {
	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpServletResponse response;
//	đọc cookies từ request
	public Cookie getCookie(String name) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie o: cookies) {
				if(o.getName().equalsIgnoreCase(name)) {
					return o;
				}
			}
		}
		return null;
	}
	public String getValue(String name, String defaultValue) {
		Cookie cookie = getCookie(name);
		if(cookie != null) {
			return cookie.getValue();
		}
		return defaultValue;
	}
//	tạo cookie từ client
	public Cookie addCookie(String name, String value, int hours) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(hours*60*60);
		cookie.setPath("/");
		response.addCookie(cookie);
		return cookie;
	}
	public void removeCookie(String name) {
		addCookie(name, "", 0);
	}
}
