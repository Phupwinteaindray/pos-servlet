<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/home" var="home"></c:url>
<c:url value="/products" var="products"></c:url>
<c:url value="/products/edit" var="editproducts"></c:url>
<c:url value="/history" var="history"></c:url>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<div class="container">
		<a class="navbar-brand" href="${home}"><i class="fa fa-home "></i>Shopping POS</a>
		<ul class="navbar-nav mr-auto">
			<li class="nav-item"><a href="${products }" class="nav-link ${menu eq 'products' ? 'active' : '' }"><i class="fa fa-gift"></i>Product</a>
			</li>
			<li class="nav-item"><a href="${editproducts }" class="nav-link ${menu eq 'products-edit' ? 'active' : '' }"><i class="fa fa-plus"></i>Add Product</a>
			</li>
			<li class="nav-item"><a href="${history }" class="nav-link ${menu eq 'history' ? 'active' : '' }"><i class="fa fa-calendar"></i>Sale History</a>
			</li>
		</ul>
	</div>
</nav>
