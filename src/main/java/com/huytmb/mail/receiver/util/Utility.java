package com.huytmb.mail.receiver.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
@Component
public class Utility {
	public static String getSiteUrl(HttpServletRequest request){
		String siteUrl = request.getRequestURL().toString();
		return siteUrl.replace(request.getServletPath(), "");
	}

}
