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

@WebServlet("/makeProduct")
public class makeProduct extends HttpServlet {
	//商品リスト宣言しておく
	List<Product> products = new ArrayList<>();//商品名だけコード、登録日、更新日などもあるのでProduct型
	
	//javaB/makeProductのﾌﾞﾗｳﾝに入ったらこのdoGet()メソッドが起動する
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				request.setAttribute("products",products);
				
				//このdoGetはmakeProduct.jspのJSPにフォーワーダーする
				request.getRequestDispatcher("WEB-INF/makeProduct.jsp").forward(request, response);
				
			}
	//makeProduct.jspのFormでこのdoPostが呼ばれる
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		String name = request.getParameter("name");
		String price = request.getParameter("price");
			
		//priceはString型ですのでInt型に変換
	    int priceInt;
	    if(price == null || price.length() == 0) {
	    	priceInt = -1;
	    }else {
	    	try {
	    		priceInt = Integer.parseInt(price); 
	    	}catch (NumberFormatException e) {
	    		priceInt = -1;
	    	}
	    }
		
		ProductDao dao = new ProductDao();
		products = dao.makeOne(name, priceInt);
		products = dao.findAll();
		
		request.setAttribute("products",products);
		
		//ここも注目：全部実行したら、遷移するところは下記のsearchProduct.jsp
		//つまり検索画面に遷移される

		request.getRequestDispatcher("WEB-INF/searchProduct.jsp").forward(request, response);
		
		}
}
