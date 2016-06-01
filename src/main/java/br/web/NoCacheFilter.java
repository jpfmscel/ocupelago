package br.web;

import java.io.IOException;

import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(servletNames = { "Faces Servlet" })
public class NoCacheFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpRes = (HttpServletResponse) response;

		if (!httpReq.getRequestURI().startsWith(httpReq.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)) { // Skip
																													// JSF
																													// resources
																													// (CSS/JS/Images/etc)
			httpRes.setHeader("Cache-Control", "no-cache, must-revalidate"); // HTTP
																				// 1.1.
			httpRes.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			httpRes.setDateHeader("Expires", 0); // Proxies.
		}
		chain.doFilter(request, response);
	}

	// ...

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}