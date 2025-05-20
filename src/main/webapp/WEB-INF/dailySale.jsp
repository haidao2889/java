<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" session="true" %>

<%@ page import="model.Product, model.Sale, java.util.List" %>

<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    List<Sale> sales = (List<Sale>) session.getAttribute("sales");
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>売上登録</title>
</head>
<body>
  
  <h1>売上登録</h1>
  
  <form method="post" action="/javaB/dailySale">
  
  <%--  dailySale.javaの"sales_date"をこのjspのString型 sales_date変数に代入する--%>
  <% String sales_date = (String)request.getAttribute("sales_date"); %>
  売上日 <%= sales_date %>
  
  </br>
  
  
  	
    <label for="productCode"></label>
    商品名　<select name="productCode" id="productCode">
			<% for(Product p: products) { %>
				
				<option value=<%= p.getProductCode() %>> <%= p.getProductName()  %></option>
				<% } %>
				</select>
    <label for="qty">数量</label>
    <input type="text" name="qty" id="qty"/>
    <input type="submit" name = "add" value = "追加">
    
    
  </form>
  
  <hr style="width:50%;text-align:left;margin-left:0">

  
  <% if (sales != null && !sales.isEmpty()) { %>
        <table border="1">
            <tr><th>商品名</th><th>数量</th></tr>
            <% for (Sale s : sales) { %>
                <tr>
                    <td><%= s.getProductName() %></td>
                    <td><%= s.getQuantity() %></td>
                </tr>
            <% } %>
        </table>
    <% } else { %>
        <p>まだ売上データがありません。</p>
    <% } %>
    
   
   <!-- 売上登録 -->
   <form action = "/javaB/registerSales" method = "post">
    <input type = "submit" name = "register" value = "登録">
   </form>
</body>
</html>
