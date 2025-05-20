package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductDao{
	//DB接続情報
	final String JDBC_URL = "com.mysql.cj.jdbc.Driver";
	final String URL = "jdbc:mysql://localhost:3306/db";
	final String USER = "thuydao";
	final String PASSWORD = "123456";
	private Connection conn;
	
	
	
	//初期化をコンストラクタでやる
	public ProductDao(){
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

	public List<Product> findAll(){
		List<Product> products = new ArrayList<>();
		
		try {
			
			//SQL実行
			String sql = "SELECT product_code, product_name, price, code_version FROM m_product WHERE is_deleted = FALSE";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Product p = new Product();
				p.setProductCode(rs.getInt("product_code")); //DBのproduct_code colum名を入れる
				p.setProductName(rs.getString("product_name"));
				p.setPrice(rs.getInt("price"));
				p.setCodeVersion(rs.getInt("code_version"));
				products.add(p);
			}
			
			//DB切断
			pstmt.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return products;
	}
	
	public List<Product> findPart(String sePro){
		List<Product> products = new ArrayList<>();
		try {
			
			//SQL実行
			String sql = "SELECT product_code, product_name, price, code_version FROM m_product "
					+ "WHERE is_deleted = FALSE AND product_name like '%" + sePro +"%'";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Product p = new Product();
				p.setProductCode(rs.getInt("product_code")); //DBのproduct_code colum名を入れる
				p.setProductName(rs.getString("product_name"));
				p.setPrice(rs.getInt("price"));
				p.setCodeVersion(rs.getInt("code_version"));
				products.add(p);
			}
			
			//DB切断
			pstmt.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return products;
	}
	
	public Product findByCode(int seCode){
		Product p = new Product();
		try {
			
			//SQL実行
			String sql = "SELECT product_code, product_name, price, code_version FROM m_product "
					+ "WHERE is_deleted = FALSE AND product_code = " + seCode ;
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			p.setProductCode(rs.getInt("product_code")); //DBのproduct_code colum名を入れる
			p.setProductName(rs.getString("product_name"));
			p.setPrice(rs.getInt("price"));
			p.setCodeVersion(rs.getInt("code_version"));
			
			
			//DB切断
			pstmt.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return p;
	}
	
	public List<Product> makeOne(String name, int priceInt) {
		List<Product> products = new ArrayList<>();
		
		try {
				
			//SQL実行
			String sql1 = "INSERT INTO m_product(product_name, price, register_datetime, update_datetime) "
					+ "VALUES('" + name +"'," + priceInt + ", now(), now())";
			PreparedStatement pstmt1 = conn.prepareStatement(sql1);
			
			pstmt1.executeUpdate();
			System.out.println(name);
			System.out.println(priceInt);
			
			//DB切断
			pstmt1.close();

			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return products;
	
	}
	
	public List<Product> deleteOne(String _productCode) {
		List<Product> products = new ArrayList<>();
		
		try {	
			//SQL実行
			//String sql1 = "DELETE FROM m_product WHERE product_code = '" + _productCode + "'; ";
			//上記は物理削除、下記は論理削除
			String sql1 = "UPDATE m_product SET is_deleted = TRUE " + 
					" WHERE product_code = '" + _productCode + "';";
			
			PreparedStatement pstmt1 = conn.prepareStatement(sql1);
			
			pstmt1.executeUpdate();
			
			//DB切断
			pstmt1.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return products;
	
	}
	
	
	/*public List<Product> editOne(String _productCode, String name, int priceInt, int _codeVersionInt) {
		List<Product> products = new ArrayList<>();
		
		try {
				
		//SQL実行(排他制御ない場合)
			//String sql1 = "UPDATE m_product SET product_name = '" + name + "',price = " + priceInt + 
			//		" WHERE product_code = '" + _productCode + "';";
			//pstmt1.executeUpdate();
			
			
			//DB切断
			//pstmt1.close();
		
			
		//排他制御実
			String sql = "SELECT product_code, product_name, price, code_version FROM m_product "
					+ "WHERE is_deleted = FALSE AND product_code = '" + _productCode +"';";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int n = rs.getInt("code_version");
			System.out.println(n);
			pstmt.close();
			
			conn.setAutoCommit(false);
			String sql1 = "SELECT product_code, product_name, price, code_version FROM m_product "
					+ "WHERE is_deleted = FALSE AND product_code = '" + _productCode +"' FOR UPDATE;";
			
			PreparedStatement pstmt1 = conn.prepareStatement(sql1);
			ResultSet rs1 = pstmt1.executeQuery();
			rs1.next();
			int m = rs1.getInt("code_version");
			pstmt1.close();
		
			if(n == m) {
				conn.commit();
				String sql2 =  
						"UPDATE m_product SET product_name = '" + name + "',price = " + priceInt + ",code_version = " +  _codeVersionInt+1 +
						" WHERE product_code = '" + _productCode + "';";
				PreparedStatement pstmt2 = conn.prepareStatement(sql2);
				
				pstmt2.executeUpdate();
				
				//DB切断
				pstmt1.close();
				
				
			}else {
				conn.rollback();
				conn.close();
				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return products;
	
	}*/

	
	//楽観的ロック
	public List<Product> editOne(String _productCode, String name, int priceInt, int _codeVersionInt)
	{
		List<Product> products = new ArrayList<>();
		
		String sql = "UPDATE m_product SET product_name = ?, price = ?, "
				+ "code_version =  code_version+1 WHERE product_code = ? AND code_version = ?;";
		try (PreparedStatement stmt = conn.prepareStatement(sql)){
		stmt.setString(1, name);
		stmt.setInt(2, priceInt);
		stmt.setString(3, _productCode);
		stmt.setInt(4, _codeVersionInt);
		stmt.executeUpdate();
		System.out.println(sql);
		return products;
		}catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
	
	// Method insertSale gốc đã khai báo register_datetime, update_datetime, sales_date, product_name
    public void insertSale(Sale s) throws SQLException {
        String sql = "INSERT INTO t_sales(product_code, quantity, register_datetime, update_datetime, sales_date, product_name) "
                   + "VALUES (?, ?, ?, ?, ?, ?) "
                   + "ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity), update_datetime = VALUES(update_datetime)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, s.getProductCode());
            stmt.setInt(2, s.getQuantity());
            stmt.setString(3, LocalDateTime.now().toString()); // register_datetime
            stmt.setString(4, s.getUpdateDate());           // update_datetime
            stmt.setString(5, s.getSalesDate());            // sales_date
            stmt.setString(6, s.getProductName());          // product_name
            stmt.executeUpdate();
        }
    }
	
	/**
     * Thống kê tổng doanh số tất cả sản phẩm
     */
    public List<SaleSummary> getTotalSales() {
        String sql = "SELECT product_code, product_name, SUM(quantity) AS total_qty " +
                     "FROM t_sales GROUP BY product_code, product_name";
        return querySales(sql, false, 0);
    }

    /**
     * Thống kê doanh số theo tháng (1-12). Nếu month==0, trả về danh sách rỗng.
     */
    public List<SaleSummary> getMonthlySales(int month) {
        String sql = "SELECT product_code, product_name, SUM(quantity) AS total_qty " +
                     "FROM t_sales WHERE MONTH(sales_date) = ? " +
                     "GROUP BY product_code, product_name";
        return querySales(sql, true, month);
    }

    private List<SaleSummary> querySales(String sql, boolean withParam, int month) {
        List<SaleSummary> list = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (withParam) stmt.setInt(1, month);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new SaleSummary(
                        rs.getInt("product_code"),
                        rs.getString("product_name"),
                        rs.getInt("total_qty")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

	
}
