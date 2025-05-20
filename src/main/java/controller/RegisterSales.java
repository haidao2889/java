package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.Sale;
import model.ProductDao;

@WebServlet("/registerSales")
public class RegisterSales extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDao dao;

    @Override
    public void init() throws ServletException {
        super.init();
        dao = new ProductDao();  //  khởi kết nối như trước
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            @SuppressWarnings("unchecked")
            List<Sale> sales = (List<Sale>) session.getAttribute("sales");
            if (sales != null && !sales.isEmpty()) {
                try {
                    // Lưu từng bản ghi sale vào DB
                    for (Sale s : sales) {
                        dao.insertSale(s);  // cần viết method này ở ProductDao
                    }
                    // Xóa sales khỏi session sau khi lưu
                    session.removeAttribute("sales");
                } catch (SQLException e) {
                    throw new ServletException("Lưu sales thất bại", e);
                }
            }
        }
        // Redirect về lại trang nhập sales
        resp.sendRedirect(req.getContextPath() + "/dailySale");
    }
}
