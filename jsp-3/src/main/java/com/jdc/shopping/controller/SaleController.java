package com.jdc.shopping.controller;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jdc.shopping.entity.Sale;

import com.jdc.shopping.service.SaleService;
@WebServlet(urlPatterns = { "/history","/sale/edit"})
public class SaleController extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	private SaleService sService;
	static EntityManagerFactory emf;
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
		if (null == emf) {
			emf = Persistence.createEntityManagerFactory("jsp-3");
			getServletContext().setAttribute("emf", emf);
		}
		sService = new SaleService(emf.createEntityManager());
		
	}
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// TODO Auto-generated method stub
	String path=req.getServletPath();
	if(path.equals("/history")) {
		String from=req.getParameter("from");
		String to=req.getParameter("to");
		List<Sale> sale=sService.search(from,to);
		req.setAttribute("list", sale);
		req.setAttribute("menu", "history");
		getServletContext().getRequestDispatcher("/products/history.jsp").forward(req, resp);
	}else if(path.equals("/sale/edit")) {
		String id=req.getParameter("id");
		if(id!=null && !id.isEmpty()) {
			Sale sale=sService.findById(id);
			HttpSession session=req.getSession(true);
			session.setAttribute("cart", sale);
			
		}
	}
	resp.sendRedirect(req.getContextPath().concat("/home"));
	
	
}
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// TODO Auto-generated method stub
	
}
}
