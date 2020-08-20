<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title }</title>
<jsp:include page="/common/resources.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/common/menu.jsp">
		<jsp:param value="Shopping POS" name="title" />
	</jsp:include>
	<div class="container">
		<h2>${title }</h2>
		<div class="row">
			<div class="col-4">
				<c:url value="/products" var="prod"></c:url>
				<form action="${prod }" method="post" class="form-group">
				<input type="hidden" name="id" value="${product.id }" />
					<div class="form-group">
						<label>Category</label> <select name="category"
							class="form-control">
						
					<c:forEach items="${category }" var="cat">
					<option value="${cat.id }" ${cat.id eq product.category.id ? 'selected':'' }>${cat.name }</option>
					</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label>Product Name</label> <input type="text" name="name" id=""
							placeholder="Enter product name" class="form-control" value="${product.name }"
							required="required" />
					</div>
					<div class="form-group">
						<label>Price</label> <input type="number" name="price"
							class="form-control" id="" placeholder="Enter product price" value="${product.price }"
							required="required" />
					</div>
					<div class="form-group">
					<button class="btn btn-primary" type="submit">
							<i class="fa fa-save"></i>Save</button>
					</div>
				</form>
			</div>
		</div>

	</div>
</body>
</html>