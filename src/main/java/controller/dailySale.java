package controller;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Product;
import model.ProductDao;
import model.Sale;

@WebServlet("/dailySale")
public class dailySale extends HttpServlet {
	//商品リスト宣言しておく
	List<Product> products = new ArrayList<>();//商品名だけコード、登録日、更新日などもあるのでProduct型
	List<Sale> sales = new ArrayList<>();
	
	
    ProductDao dao = new ProductDao();   
    
    //javaB/makeProductのブラウザに入ったらこのdoGet()メソッドが起動する
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // ドロップダウンリストのﾃﾞｰﾀを取得
        List<Product> products = dao.findAll();
        req.setAttribute("products", products);
        
     // 商品コードと数量変数を生成
	   	LocalDateTime now = LocalDateTime.now();
	   	//Formatする
	   	String sales_date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	   	//変数sales_dateにこのサーブレットのsales_dateを設定してJSPファイルでｇetAttributeで日にちの値を取る
	   	req.setAttribute("sales_date", sales_date);
	   	System.out.println(sales_date);


        //dailySale.jspを表示する
        req.getRequestDispatcher("/WEB-INF/dailySale.jsp").forward(req, res);
        
    }
    
    //追加ボタンを押した後にこのdoPostメソッドがt実施される
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // 商品コードと数量変数を生成
	   	LocalDateTime now = LocalDateTime.now();
	   	//現在日付けと更新日を"yyyy-MM-dd HH:mm:ss"のようにFormatする
	   	String sales_date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	   	String update_date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	   	
	   	
        String code = req.getParameter("productCode");
        String qtyStr = req.getParameter("qty");

        int qty;
        try {
            qty = Integer.parseInt(qtyStr);
            if (qty <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            req.setAttribute("error", "数量は正の整数で入力してください");
            doGet(req, res);          // ページ表示
            return;
        }

        Product p = dao.findByCode(Integer.parseInt(code));
        
        
        if (p == null) {
            req.setAttribute("error", "商品コードが不正です");
            doGet(req, res);
            return;
        }

        // 売上を追加する
        //String productName,int productCode, int quantity, String salesDate, String updateDate
        Sale sale = new Sale( p.getProductName(), p.getProductCode(), qty, sales_date, update_date);
        
        // session設定
        HttpSession session = req.getSession();
        
        @SuppressWarnings("unchecked")
        List<Sale> sales = (List<Sale>) session.getAttribute("sales");
        
        if (sales == null) {
            sales = new ArrayList<>();
            session.setAttribute("sales", sales);
        } 
        
        int i = 0;
        // 同じ日に同じ商品の追加場合、数量と最新更新日時を上書きする
        for(Sale s : sales) {
        	if(String.valueOf(s.getProductCode()).equals(code)) {
        		int product_qty = s.getQuantity();
        		
        		s.setQuantity(product_qty + qty);
        		s.setUpdateDate(sales_date);
        		sales.set(i, s);
        		
        		break;
        	}else if(i == sales.size() - 1) {
            	sales.add(sale);
            	break;
            }
        	
        	i = i + 1;
        }
        
        // 売上ﾃﾞｰﾀをsalesに追加
        if(sales.size() < 1) {
        	sales.add(sale);
        }
       
         
        // dailySale.jspにフォーワーダー（画面はdaily.Sale.jspにある）
        res.sendRedirect(req.getContextPath() + "/dailySale");
   	}
	   
}


