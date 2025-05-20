package controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Product;
import model.ProductDao;

@WebServlet("/searchProduct")
public class searchProduct extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
			//商品リスト宣言する
			List<Product> products = new ArrayList<>();//商品名、単価をProduct型のproductsに格納
			
			//フォームタグの入力バーの情報をゲットする
			String sePro = request.getParameter("sePro");
			
			if(sePro == null) {
				ProductDao dao = new ProductDao();
				products = dao.findAll();
				
				request.setAttribute("products", products);
			}else {
				ProductDao dao_search = new ProductDao();
				products =  dao_search.findPart(sePro);
				request.setAttribute("products",products);
				
			}
			
			
			//JSPにフォーワーダーする

			request.getRequestDispatcher("WEB-INF/searchProduct.jsp").forward(request, response);
			
		}
}
