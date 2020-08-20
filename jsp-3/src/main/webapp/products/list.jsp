<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Products</title>
<jsp:include page="/common/resources.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/common/menu.jsp">
		<jsp:param value="Shopping POS" name="title" />
	</jsp:include>
	<div class="container">
		<form action="" class="form-inline mb-4">
			<div class="form-group mr-4">
				<label class="mr-2">Category</label> <select name="category" id=""
					class="form-control">
					<option value="0">All Category</option>
					<c:forEach items="${category }" var="cat">
					<option value="${cat.id }" ${param.category eq cat.id ? 'selected' : '' }>${cat.name }</option>
					</c:forEach>
					
				</select>
			</div>
			<div class="form-group mr-4">
				<label class="mr-2">Products</label> <input type="text" name="name"
					id="" class="form-control" value="${param.name }" placeholder="Search Product" />
			</div>
			<div class="form-group mr-4">
				<button type="submit" class="btn btn-primary mr-2"><i class="fa fa-search"></i>Search</button>
				<button id="uploadButton" type="button" class="btn btn-danger"><i class="fa fa-upload"></i>Upload</button>
			</div>
		</form>
		<c:choose>
			<c:when test="${empty list  }">
				<div class="alert alert-primary">
					<p>There is no data to display</p>
				</div>
			</c:when>
			<c:otherwise>
				<table class="table">
					<thead>
						<tr>
							<th>No</th>
							<th>Category</th>
							<th>Name</th>
							<th>Price</th>
							<th></th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list }" var="p">
							<tbody>
								<tr>
									<td>${p.id }</td>
									<td>${p.category.name }</td>
									<td>${p.name }</td>
									<td>${p.price }MMK</td>
									<td><c:url value= "/products/edit"  var="edit">
											<c:param name="id" value="${p.id}"></c:param>
											
										</c:url>
										<a href="${edit }">Edit</a>
										</td>
									<td></td>
								</tr>
							</tbody>
						</c:forEach>
					</tbody>
				</table>
			</c:otherwise>
		</c:choose>
	</div>
	<c:url value="/products/upload" var="upload"></c:url>
	<form id="uploadForm" action="${upload }" method="post" enctype="multipart/form-data" class="d-none" >
	<input  id="uploadFile" type="file" name="file"  />
	</form>
	
	<script>
	$(()=>{
		$('#uploadButton').click(()=>$('#uploadFile').click())
		$('#uploadFile').change(()=>$('#uploadForm').submit())
	})
	</script>
</body>
</html>