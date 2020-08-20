package com.jdc.shopping.controller;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jdc.shopping.entity.Product;
import com.jdc.shopping.entity.Sale;
import com.jdc.shopping.entity.SaleDetail;
import com.jdc.shopping.service.CategoryService;
import com.jdc.shopping.service.ProductService;
import com.jdc.shopping.service.SaleService;

@WebServlet(loadOnStartup = 1, urlPatterns = { "/home","/home-search","/add-to-cart","/cart-action" })
public class HomeController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	static EntityManagerFactory emf;
	private CategoryService catService;
	private ProductService pService;
	private SaleService sService;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String path=req.getServletPath();
		if("/home-search".equals(path)) {
			searchProducts(req);
		}
		if("/add-to-cart".equals(path)) {
			addToCart(req);
		}
		searchProducts(req);
		req.setAttribute("category", catService.getAll());
		getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
	}

	private void addToCart(HttpServletRequest req) {
		// TODO Auto-generated method stub
//		Find sale object from session
		HttpSession session=req.getSession(true);
		Sale sale=(Sale) session.getAttribute("cart");
		if(null==sale) {
			sale=new Sale();
			session.setAttribute("cart", sale);
		}
		String strId=req.getParameter("id");
		int id = Integer.parseInt(strId);
		for(SaleDetail d:sale.getSaleDetail()) {
			if(d.getProduct().getId()==id) {
				d.setQuantity(d.getQuantity()+1);
				return;
			}
		}
		
		Product p=pService.findById(id);
		SaleDetail detail=new SaleDetail();
		detail.setProduct(p);
		detail.setQuantity(1);
		sale.addDetail(detail);
		
	}

	private void searchProducts(HttpServletRequest req) {
		// TODO Auto-generated method stub
		String category=req.getParameter("category");
		String name=req.getParameter("name");
		req.setAttribute("products", pService.search(category, name));
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action=req.getParameter("action");
		System.out.println(action);
		HttpSession session=req.getSession(true);
		if("Clear".equals(action)) {
			session.removeAttribute("cart");
		}else if("Paid".equals(action)) {
			Sale sale=(Sale) session.getAttribute("cart");
			if(null!=sale && sale.getTotal()!=0) {
				sService.save(sale);
			}
			session.removeAttribute("cart");
			
		}
		resp.sendRedirect(req.getContextPath().concat("/home"));
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		emf=(EntityManagerFactory) getServletContext().getAttribute("emf");
		if(null == emf) {
			emf=Persistence.createEntityManagerFactory("jsp-3");
			getServletContext().setAttribute("emf", emf);
		}
		catService=new CategoryService(emf.createEntityManager());
		pService=new ProductService(emf.createEntityManager());
		sService=new SaleService(emf.createEntityManager());
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		emf=(EntityManagerFactory) getServletContext().getAttribute("emf");
		if(emf.isOpen()) {
			emf.close();
		}
	}
}
