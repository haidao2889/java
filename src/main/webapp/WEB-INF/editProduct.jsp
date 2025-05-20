

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%@ page import = "java.util.List" %>
<%@ page import = "model.Product" %>
<% List<Product> products = (List<Product>)request.getAttribute("products"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品変更・削除</title>
</head>

<body>
	<%-- ﾀｲﾄﾙ --%>
	<h1>商品変更・削除</h1>
	<form action = "/javaB/editProduct" method = "post">
	
		<%-- editProduct.javaにproductCodeが設定された(request.setAttribute("productCode",_productCode);)
		ので今回はgetAttributeで使う--%>
		商品ｺｰﾄﾞ　<td><%= request.getAttribute("productCode") %></td></br>
		
		
		商品名　<input type = "text" name = "name"><br>
		単価　<input type = "text" name = "price"><br>
		<input type = "submit" name = "delete" value = "削除">
		<input type = "submit" name = "edit" value = "変更">
	</form>
	

</body>
</html>