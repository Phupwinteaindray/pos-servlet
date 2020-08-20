<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Shopping Cart</title>
<jsp:include page="/common/resources.jsp"></jsp:include>

</head>
<body>
	<jsp:include page="/common/menu.jsp">
		<jsp:param value="Shopping POS" name="title" />
	</jsp:include>
	<div class="container">
		<div class="row">
			<div class="col-8">
				<!-- Search Product -->
				<div class="fluid card mt-4">
					<div class="card-body">
						<h4 class="card-title">Search Product</h4>
						<c:url value="/home-search" var="search"></c:url>

						<form action="${search }" class="form form-inline">
							<div class="form-row">
								<div class="form-group mr-4">
									<label class="mr-2">Category</label> <select name="category"
										class="form-control">
										<option value="0">All Categories</option>
										<c:forEach items="${category }" var="c">
											<option value="${c.id }"
												${param.category eq c.id ? 'selected' : '' }>${c.name }</option>
										</c:forEach>
									</select>
								</div>

								<div class="form-group mr-4">
									<label class="mr-2">Name</label> <input type="text"
										placeholder="Search name" name="name" value="${param.name }"
										id="" class="form-control" />
								</div>
								<div class="form-group">
									<label for="">&nbsp;</label>
									<button class="btn btn-primary">
										<i class="fa fa-search"></i>Search
									</button>
								</div>
							</div>
						</form>
					</div>
				</div>
				<table class="table mt-4">
					<thead>
						<tr>
							<th>Id</th>
							<th>Category</th>
							<th>Product</th>
							<th class="text-right">Price</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${products }" var="p">
							<tr>
								<td>${p.id }</td>
								<td>${p.category.name }</td>
								<td>${p.name }</td>
								<td class="text-right">${p.price }</td>

								<td class="text-right"><c:url value="/add-to-cart" var="addTocart"> <c:param
										name="id" value="${p.id }"></c:param> </c:url><a href="${addTocart }"><i
										class="fa fa-cart-plus"></i></a></td>
															</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="col-4">
				<!-- Order List  -->
				<div class="fluid card mt-4">
					<div class="card-body">
						<h4 class="card-title">
							<i class="fa fa-shopping-cart"></i>Sale Detail <span
								class="float-right pr-2">${cart.total }</span>
						</h4>
						<table class="table">
							<thead>
								<tr>
									<th>Product</th>
									<th class="text-right">Price</th>
									<th class="text-right">Quantity</th>
									<th class="text-right">Total</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${cart.saleDetail}" var="car">
								<tr>
									<td>${car.product.name }</td>
									<td>${car.product.price }</td>
									<td class="text-right">${car.quantity }</td>
									<td>${car.product.price * car.quantity}</td>
								</tr>
								</c:forEach>
							
							
								<tr class="table-primary">
									<td colspan="3">SubTotal</td>
									<td class="text-right">${cart.subTotal }</td>
								</tr>
								<tr>
									<td colspan="3">Tax</td>
									<td class="text-right">${cart.tax }</td>
								</tr>
								<tr class="table-primary">
									<td colspan="3">Total</td>
									<td class="text-right">${cart.total }</td>
								</tr>
							</tbody>
						</table>
						<c:url value="/cart-action" var="cartAction"></c:url>
						<form action="${cartAction }" method="post">
						<div class="form-row">
							<div class="col">
							
								<input type="submit" name="action" value="Clear" class="btn btn-block btn-danger" />
								
							</div>
							<div class="col">
								<input type="submit" name="action" value="Paid" class="btn btn-block btn-danger" />
							</div>
						</div>
						</form>
					</div>

				</div>
			</div>
		</div>
	</div>
</body>
</html>