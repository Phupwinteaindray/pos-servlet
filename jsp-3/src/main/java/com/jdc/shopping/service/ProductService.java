package com.jdc.shopping.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.jdc.shopping.entity.Category;
import com.jdc.shopping.entity.Product;

public class ProductService {

	private EntityManager em;

	public ProductService(EntityManager em) {
		this.em = em;
	}

	public void save(Product p) {
		em.getTransaction().begin();
		if (p.getId() == 0) {
			em.persist(p);
		} else {
			em.merge(p);
		}

		em.getTransaction().commit();
	}



	public List<Product> search(String categoryId, String name) {

		StringBuffer sb = new StringBuffer("select p from Product p where 1 = 1");
		Map<String, Object> params = new HashMap<>();
		
		if(null != categoryId && !categoryId.isEmpty() && !"0".equals(categoryId)) {
			sb.append(" and p.category.id = :categorId");
			params.put("categorId", Integer.parseInt(categoryId));
		}
		
		if(null != name && !name.isEmpty()) {
			sb.append(" and lower(p.name) like lower(:name)");
			params.put("name", name.concat("%"));
		}
		
		TypedQuery<Product> query = em.createQuery(sb.toString(), Product.class);
		
		for(String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}
		
		return query.getResultList();
	}

	public List<Product> getAll() {
		return em.createNamedQuery("Product.getAll", Product.class).getResultList();
	}

	public Product findById(int pId) {
		// TODO Auto-generated method stub
		return em.find(Product.class, pId);
	}

	public void saveAll(List<String> str) {
		// TODO Auto-generated method stub
		em.getTransaction().begin();
		for (String st : str) {
			Product p = getProductfFromLine(st);
			em.persist(p);
		}
		em.getTransaction().commit();
	}

	private Product getProductfFromLine(String st) {
		// TODO Auto-generated method stub
		Product p = new Product();
		String[] array = st.split("\t");
		for (String s : array) {
			System.out.println(s);
		}

		Category c = findCategoryByName(array[0]);
		p.setCategory(c);
		p.setName(array[1]);
		p.setPrice(Integer.parseInt(array[2]));

		return p;
	}

	private Category findCategoryByName(String name) {
		// TODO Auto-generated method stub
		// StringBuffer buffer=new StringBuffer("SELECT c FROM Category c WHERE c.name =
		// ?1");

		TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c WHERE c.name = '" + name + "'",
				Category.class);
//		TypedQuery<Category> query = em.createNamedQuery("Category.findByName", Category.class);
		// query.setParameter(1, name);

		return query.getSingleResult();
	}
}
