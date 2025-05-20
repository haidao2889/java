package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SaleDao{
	//DB接続情報
	final String JDBC_URL = "com.mysql.cj.jdbc.Driver";
	final String URL = "jdbc:mysql://localhost:3306/db";
	final String USER = "root";
	final String PASSWORD = "ttQD8785";
	private Connection conn;
	
	
	
	//初期化をコンストラクタでやる
	public SaleDao(){
		try {
			//DB接続
			Class.forName(JDBC_URL);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			
		}catch(Exception e) {
			try {
				e.printStackTrace();
				if(conn != null) {
					//DB切断 
					conn.close();}
				
			}catch (SQLException f) {
			}
		}
	}
	
	// 売上リストを受けてデータベースに登録j
	public int addSale(List<Sale> sales){
		
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		int result = 0;
		ResultSet rs = null;
		
		try {
			for(Sale sale : sales) {
				//既に売上ﾃﾞｰﾀ登録されているか判断
				String select_sql = "SELECT count(*) as count from t-sales" +
					"WHERE sales_date = '" + sale.getSalesDate() + "' and product_code = '" +
					sale.getProductCode() + "';";
				
				// 売上登録mySQL文
				String insert_sql = "INSERT INTO t_sales (sales_date, product_code, quantity)" +
					"VALUES(?, ?, ?);";
				
				// SQL文を載せる
				pstmt = conn.prepareStatement(select_sql);
				pstmt2 = conn.prepareStatement(insert_sql);
				int count = 0;
				
				// SQL文実行
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					count = rs.getInt("count");
				}
				
				//countが＜＝0場合、売上ﾃﾞｰﾀを登録する
				if(count < 1) {
					pstmt2.setString(1, sale.getSalesDate());
					pstmt2.setInt(2, sale.getProductCode());
					pstmt2.setInt(3, sale.getQuantity());
				}
				
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(pstmt != null && !pstmt.isClosed()) {pstmt.close();}
				if(pstmt2 != null && !pstmt2.isClosed()) {pstmt2.close();}
				if(rs != null) {rs.close();}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
