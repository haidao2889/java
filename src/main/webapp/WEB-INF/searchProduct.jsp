<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import = "java.util.List" %>
<%@ page import = "model.Product" %>
<%--<% List<String> products = (List<String>)request.getAttribute("products"); --%>
<% List<Product> products = (List<Product>)request.getAttribute("products"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品検索</title>
</head>

<body>
	<%-- ﾀｲﾄﾙ --%>
	<h1>商品検索</h1>
	
	<%-- 商品名表示JSPのみ使用 
	<table>
		<tr>
			<th>商品名</th>
		</tr>
		
		<tr>
			<td>デスクトップPC</td>
		</tr>
		
		<tr>
			<td>ノートPC</td>
		</tr>
		
		<tr>
			<td>モニター</td>
		</tr>
		
		<tr>
			<td>マウス</td>
		</tr>
		
		<tr>
			<td>キーボード</td>
		</tr>
		
		<tr>
			<td>USB</td>
		</tr>
		
		<tr>
			<td>カメラ</td>
		</tr>
	</table>
	--%>
	
	<%-- 商品名表示サーブレット＆JSP使用 
	<table>
		<tr>
			<th>商品名</th>
		</tr>
		
		<tr>
			<td><%= products.get(0) %></td>
		</tr>
		
		<tr>
			<td><%= products.get(1) %></td>
		</tr>
		
		<tr>
			<td><%= products.get(2) %></td>
		</tr>
		
		<tr>
			<td><%= products.get(3) %></td>
		</tr>
		
		<tr>
			<td><%= products.get(4) %></td>
		</tr>
		
		<tr>
			<td><%= products.get(5) %></td>
		</tr>
		
		<tr>
			<td><%= products.get(6) %></td>
		</tr>
	</table> --%>
	
	<%-- model Product 使用 --%>
	
	<%-- 検索入力部分 --%>
	<div>
		<form action = "/javaB/searchProduct" method = "get">
			商品名
			<input type = "text" name = "sePro">
			<input type = "submit" value = "検索">
		</form>
	</div>
	
	<table border = "1">
		<tr>
			<th>商品コード</th>
			<th>商品名</th>
			<th>単価</th>
			<th>操作</th>
		</tr>
		<% for(Product p: products) { %>
		<tr>
			<%-- <td><%= p.getProductCode() %>></td>--%>
			<td><% out.print(String.format("%03d", new Integer(p.getProductCode()))); %></td>
			<td><%= p.getProductName()  %></td>
			<%-- <td><%= p.getPrice()  %></td>--%>
			<td><% out.print(String.format("%,d", new Integer(p.getPrice()))); %></td>
			
			
		    <%-- hrefのところはクリックした後に遷移されるリンク（この場合はeditProduct.javaに遷移）--%>
			<td><a href ="editProduct?productCode=<% out.print(String.format("%03d", new Integer(p.getProductCode())));%>
				&codeVersion=<%= p.getCodeVersion() %>" >編集</a></td>
		</tr>
		<% } %>
	</table>
	

</body>
</html>