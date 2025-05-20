<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Export Sales CSV</title>
</head>
<body>
  <h1>Export Sales CSV</h1>
  <!-- Truy cập trang này: http://<host>:<port>/<context>/exportSales.jsp -->
  <form action="<%= request.getContextPath() %>/exportSalesAction" method="get">
    <!-- Nút 1: Xuất toàn bộ doanh số -->
    <button type="submit" name="type" value="all">Tải CSV - Tất cả sản phẩm</button>
    <br><br>
    <!-- Nút 2: Xuất doanh số theo tháng -->
    <label for="month">Chọn tháng:</label>
    <select id="month" name="month">
      <option value="">--Chọn--</option>
      <% for (int m = 1; m <= 12; m++) { %>
        <option value="<%= m %>"><%= m %></option>
      <% } %>
    </select>
    <button type="submit" name="type" value="monthly">Tải CSV - Theo tháng</button>
  </form>
</body>
</html>