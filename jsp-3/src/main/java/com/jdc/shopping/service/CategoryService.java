package com.jdc.shopping.service;

import java.util.List;

import javax.persistence.EntityManager;

import com.jdc.shopping.entity.Category;

public class CategoryService {

	private EntityManager em;
	public CategoryService(EntityManager em) {
		this.em=em;
	}
	public List<Category> getAll(){
		return em.createNamedQuery("Category.getAll",Category.class).getResultList();
	}
	public Category findById(int parseInt) {
		// TODO Auto-generated method stub
		return em.find(Category.class, parseInt);
	}
}
