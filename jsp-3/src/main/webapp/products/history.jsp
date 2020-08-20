<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sale History</title>
<jsp:include page="/common/resources.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/common/menu.jsp">
		<jsp:param value="Shopping POS" name="title" />
</jsp:include>
<!--Search -->
<div class="container-fluid">
<div class="card">
<div class="card-body">
<h4 class="card-title">Sale History </h4>
<c:url value="/history" var="his"></c:url>
<form action="${his }" class="form form-inline">
<div class="form-group">
<label for="from" class="mr-2">DatevFrom</label>
<input type="date" name="from" id="from" class="form-control" value="${param.from }" />
</div>
<div class="form-group">
<label for="to" class="mr-2">Date To</label>
<input type="date" name="to" id="to" class="form-control" value="${param.to }" />
</div>
<div class="form-group">
<button class="btn btn-primary"><i class="fa fa-search"></i>Search</button>
</div>
</form>
</div>
</div>
</div>

<!-- Result Table -->
<table class="table mt-4">
<thead>
	<tr>
		<th>Id</th>
		<th>Sale Date</th>
		<th class="text-right">Sub Total</th>
		<th class="text-right">Tax</th>
		<th class="text-right">Total</th>
		<th></th>
	</tr>
</thead>
<tbody>
<c:forEach items="${list }" var="li">
<tr>
	<td>${li.id }</td>
	<td>${li.date }</td>
	<td class="text-right">${li.subTotal }</td>
	<td class="text-right">${li.tax }</td>
	<td class="text-right">${li.total }</td>
	<td>
	<c:url value="/sale/edit" var="sal">
	<c:param name="id" value="${li.id }"></c:param>
	</c:url>
	<a href="${sal }"><i class="fa fa-pencil"></i>Edit</a>
	
	</td>
</tr>
</c:forEach>
</tbody>
</table>
</body>
</html>