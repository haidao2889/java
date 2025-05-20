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


@WebServlet("/editProduct")

public class editProduct extends HttpServlet {
	//商品リスト宣言しておく
	List<Product> products = new ArrayList<>();//商品名だけコード、登録日、更新日などもあるのでProduct型
	
	//変数_productCodeを宣言。この変数はdoGet,doPost両方使いたいから　ここで宣言
	 String _productCode;
	 String _codeVersion;
	 
	//<a>タグから呼ばれてdoGet()メソッドが起動する
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
			 //変数_productCodenに<a>タグのproductCodeを取得
			 _productCode = request.getParameter("productCode");
			 _codeVersion = request.getParameter("codeVersion");
			 
			 //取得した_productCodeをrequestのproductCodeに設定
			 request.setAttribute("productCode",_productCode);
			 request.setAttribute("codeVersion",_codeVersion);
			 
			//このdoGetはeditProduct.jspのJSPにフォーワーダーする
			request.getRequestDispatcher("WEB-INF/editProduct.jsp").forward(request, response);
			}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
			
			String name = request.getParameter("name");
			String price = request.getParameter("price");
			String delete = request.getParameter("delete");
			String edit = request.getParameter("edit");
				
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
		    
		    //_codeVersionをInt型に変更
		    int _codeVersionInt;
		    if(_codeVersion == null || _codeVersion.length() == 0) {
		    	_codeVersionInt = -1;
		    }else {
		    	try {
		    		_codeVersionInt = Integer.parseInt(_codeVersion); 
		    	}catch (NumberFormatException e) {
		    		_codeVersionInt = -1;
		    	}
		    }
			
		    
			ProductDao dao = new ProductDao();
			
			//編集の場合
			if(edit != null) {
				products = dao.editOne(_productCode, name, priceInt, _codeVersionInt);
			    products = dao.findAll();
				//boolean _OK = dao.updateProduct(_productCode, name, priceInt, _codeVersionInt);
				//System.out.println(_OK);
			} else if(delete != null) {
			//削除な場合
			products = dao.deleteOne(_productCode);
			products = dao.findAll();
			
			}
			request.setAttribute("products",products);
			
			//ここも注目：全部実行したら、遷移するところは下記のsearchProduct.jsp
			// つまり検索画面に遷移される

			request.getRequestDispatcher("WEB-INF/searchProduct.jsp").forward(request, response);
			
			}
}