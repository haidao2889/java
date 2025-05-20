<%--makeProduct.javaから呼ばれるからこの内容が表示される --%>
<%--表示したFormの商品名と単価を入力すると今度はFormは注目！！
action属性は"/javaB/makeProduct"
methodがpostですので
makeProduct.javaのtoPost()メソッドが実行される --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import = "java.util.List" %>
<%@ page import = "model.Product" %>
<% List<Product> products = (List<Product>)request.getAttribute("products"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品登録</title>
</head>

<body>
	<%-- ﾀｲﾄﾙ --%>
	<h1>商品登録</h1>
	
	<form action = "/javaB/makeProduct" method = "post">
		商品名　<input type = "text" name = "name"><br>
		単価　<input type = "text" name = "price"><br>
		<input type = "submit" value = "登録">
	</form>
	

</body>
</html>