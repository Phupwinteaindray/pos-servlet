package com.jdc.shopping.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Sale implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String email;
	private String customer;
	private LocalDateTime saleDate;
	@OneToMany(mappedBy = "sale",cascade = {CascadeType.PERSIST,CascadeType.MERGE},orphanRemoval = true)
	private List<SaleDetail> saleDetail=new ArrayList<SaleDetail>();
	private static final DateTimeFormatter DF=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	public void addDetail(SaleDetail sd) {
		sd.setSale(this);
		saleDetail.add(sd);
	}
	
	public String getDate() {
		return null==saleDate ? "":saleDate.format(DF);
	}
	public int getSubTotal() {
		return saleDetail.stream().mapToInt(d -> d.getQuantity() * d.getProduct().getPrice()).sum();
	}
	
	public int getTax() {
		return getSubTotal()/100*5;
	}
	
	public int getTotal() {
		return getSubTotal() + getTax();
	}
}
