package com.jdc.shopping.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.jdc.shopping.entity.Category;
import com.jdc.shopping.entity.Product;
import com.jdc.shopping.service.CategoryService;
import com.jdc.shopping.service.ProductService;

@WebServlet(urlPatterns = { "/products", "/products/edit", "/products/upload" })
@MultipartConfig
public class ProductController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private CategoryService categoryService;
	private ProductService pService;
	static EntityManagerFactory emf;

//	@Override
//	public void init(ServletConfig config) throws ServletException {
//		// TODO Auto-generated method stub
//		super.init(config);
//		emf=(EntityManagerFactory) getServletContext().getAttribute("emf");
//		categoryService=new CategoryService(emf.createEntityManager());
//		pService=new ProductService(emf.createEntityManager());
//	}
//	
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
		if (null == emf) {
			emf = Persistence.createEntityManagerFactory("jsp-3");
			getServletContext().setAttribute("emf", emf);
		}
		categoryService = new CategoryService(emf.createEntityManager());
		pService = new ProductService(emf.createEntityManager());
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String page = null;
		if (req.getServletPath().equals("/products")) {
			//
			String catId = req.getParameter("category");
			String pName = req.getParameter("name");
			// find product by Id
			
			List<Product> prodList = pService.search(catId, pName);
			
			// add Product to request scope
			req.setAttribute("list", prodList);
			page = "/products/list.jsp";
			req.setAttribute("menu", "products");
		} else {
			String pId = req.getParameter("id");
			if (null != pId) {
				// edit product
				// find product by Id
				Product prd = pService.findById(Integer.parseInt(pId));
				// add Product to request scope
				req.setAttribute("product", prd);
				req.setAttribute("title", "Edit Product");
			} else {
				// add new product
				req.setAttribute("title", "Add New Product");
			}
			page = "/products/edit.jsp";
			req.setAttribute("menu", "products-edit");
		}
		// search category and add to request code
		req.setAttribute("category", categoryService.getAll());
		getServletContext().getRequestDispatcher(page).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (req.getServletPath().equals("/products")) {

			// save product
			String id = req.getParameter("id");
			String categoryId = req.getParameter("category");
			String name = req.getParameter("name");
			String price = req.getParameter("price");
			Category category = categoryService.findById(Integer.parseInt(categoryId));
			Product product = (null == id || id.isEmpty()) ? new Product() : pService.findById(Integer.parseInt(id));
			product.setPrice(Integer.parseInt(price));
			product.setName(name);
			product.setCategory(category);
			pService.save(product);

		} else {
			// upload file
			Part part = req.getPart("file");
			try (BufferedReader buffer = new BufferedReader(new InputStreamReader(part.getInputStream()))) {
				String line = null;
				List<String> str = new ArrayList<String>();
				while (null != (line = buffer.readLine())) {
					str.add(line);
					System.out.println(line);
				}
				pService.saveAll(str);
			}
		}
		resp.sendRedirect(req.getContextPath().concat("/products"));
	}
}
