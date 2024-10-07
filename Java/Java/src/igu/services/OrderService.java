package igu.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import igu.database.DbConnection;
import igu.models.OrderItem;
import igu.models.Product;
import igu.models.Sale;

public class OrderService {
	
	public float GetDailyTotalSales() throws SQLException {
		float totalAmount = 0;
		DbConnection db = new DbConnection();
		
		try {
			db.Open();

			String sql = "SELECT SUM(b.price* b.quantity) as total FROM `orders` a JOIN order_items b ON b.order_id = a.id WHERE DATE(created_at) = CURDATE()";
			Statement stmt = db.connect.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				totalAmount = rs.getFloat("total");
			} 
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}

		db.Close();
		return totalAmount;
	}	

	public float GetTotalSales() throws SQLException {
		float totalAmount = 0;
		DbConnection db = new DbConnection();
		
		try {
			db.Open();

			String sql = "SELECT SUM(b.price* b.quantity) as total FROM `orders` a JOIN order_items b ON b.order_id = a.id";
			Statement stmt = db.connect.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				totalAmount = rs.getFloat("total");
			} 
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}

		db.Close();
		return totalAmount;
	}	

	public ArrayList<Sale> GetSales() throws SQLException {
		ArrayList<Sale> sales = new ArrayList<Sale>();
		DbConnection db = new DbConnection();
		
		try {
			db.Open();

			String sql = "SELECT SUM(b.price * b.quantity) as total, DATE(created_at) as date, COUNT(DISTINCT a.id) as count FROM orders a JOIN order_items b ON b.order_id = a.id GROUP BY DATE(created_at) ORDER BY date";
			Statement stmt = db.connect.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
            	sales.add(new Sale(rs.getDate("date"), rs.getInt("count"), rs.getFloat("total")));
            }

            rs.close();
			stmt.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}

		db.Close();
		return sales;
	}

	public boolean AddOrder(ArrayList<OrderItem> items) throws SQLException {
		boolean status = false;
		DbConnection db = new DbConnection();
		
		try {
			db.Open();

			String sql = "INSERT INTO orders(created_at) VALUES(NOW())";
			Statement stmt = db.connect.createStatement();
			stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

			ResultSet rs = stmt.getGeneratedKeys();
		    rs.next();
		    int orderId = rs.getInt(1);

			for (OrderItem item : items) {
				sql = "INSERT INTO order_items(product_id, order_id, quantity, price) VALUES(" + item.getProductId() + "," + orderId + "," + item.getQuantity() + "," + item.getPrice() + ")";
				stmt.executeUpdate(sql);
				
				sql = "UPDATE products SET quantity = (quantity - " + item.getQuantity() + ") WHERE id = " + item.getProductId();
				stmt.executeUpdate(sql);				
			}
			
			status = true;

            rs.close();
			stmt.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}

		db.Close();
		return status;
	}
	
}
