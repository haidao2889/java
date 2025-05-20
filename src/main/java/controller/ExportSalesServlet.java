package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ProductDao;
import model.SaleSummary;

@WebServlet("/exportSalesAction")
public class ExportSalesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDao dao;

    @Override
    public void init() throws ServletException {
        super.init();
        dao = new ProductDao();  // sử dụng DAO có sẵn
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String type = req.getParameter("type");
        List<SaleSummary> list;
        String filename;

        if ("monthly".equals(type)) {
            int month = 0;
            try { month = Integer.parseInt(req.getParameter("month")); }
            catch (NumberFormatException e) { month = 0; }

            list = dao.getMonthlySales(month);
            filename = String.format("sales_%02d.csv", month);
        } else {
            list = dao.getTotalSales();
            filename = "sales_all.csv";
        // ensure this servlet only triggers on form submit from exportSales.jsp
        }

        resp.setContentType("text/csv; charset=UTF-8");
        resp.setHeader("Content-Disposition", "attachment; filename=" + filename);

        try (PrintWriter pw = resp.getWriter()) {
            pw.println("product_code,product_name,quantity");
            for (SaleSummary s : list) {
                pw.printf("%d,%s,%d%n",
                    s.getProductCode(),
                    s.getProductName(),
                    s.getTotalQuantity()
                );
            }
        }
    }
}