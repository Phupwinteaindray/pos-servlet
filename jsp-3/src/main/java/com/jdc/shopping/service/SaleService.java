package com.jdc.shopping.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.jdc.shopping.entity.Sale;

public class SaleService {

	private static DateTimeFormatter DF=DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private EntityManager em;
	public SaleService(EntityManager em) {
		this.em=em;
	}
	public void save(Sale sale) {
		// TODO Auto-generated method stub
		em.getTransaction().begin();
		sale.setSaleDate(LocalDateTime.now());
		if(sale.getId()!=0) {
			em.merge(sale);
		}else {
			em.persist(sale);
		}
	
		em.getTransaction().commit();
	}
	public List<Sale> search(String from, String to) {
		// TODO Auto-generated method stub
		StringBuffer st=new StringBuffer("select s from Sale s where 1=1");
		Map<String, Object> param=new HashMap<String, Object>();
		if(null!=from && !from.equals(null) && !from.isEmpty()) {
			st.append(" and s.saleDate>=:from");
			LocalDate dateFrom=LocalDate.parse(from,DF);
			param.put("from", dateFrom.atStartOfDay());			//Transfering from LocalDate to LocalDateTime
		}if(null!=to && !to.equals(null) && !to.isEmpty()) {
			st.append(" and s.saleDate<:to");
			LocalDate dateTo=LocalDate.parse(to,DF).plusDays(1);
			param.put("to", dateTo.atStartOfDay());
		}
		TypedQuery<Sale> query=em.createQuery(st.toString(),Sale.class);
		for(String s:param.keySet()) {
			query.setParameter(s, param.get(s));
		}
		return query.getResultList();
	}
	public Sale findById(String id) {
		// TODO Auto-generated method stub
		
		return em.find(Sale.class, Integer.parseInt(id));
	}
	
}
