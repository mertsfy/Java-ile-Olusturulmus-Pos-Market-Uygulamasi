package igu.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import igu.database.DbConnection;
import igu.models.Product;

public class ProductService {

	public Product GetProductByName(String name, int productId) throws SQLException {
		Product product = null;
		DbConnection db = new DbConnection();
		
		try {
			db.Open();

			String sql = "SELECT * FROM products WHERE name='" + name + "' AND id!=" + productId;
			Statement stmt = db.connect.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				product = new Product(rs.getInt("id"), rs.getString("name"), rs.getInt("quantity"), rs.getFloat("price"), rs.getInt("category_id"));
			} 
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}

		db.Close();
		return product;
	}
	
	public boolean AddProduct(Product product) throws SQLException {
		boolean status = false;
		DbConnection db = new DbConnection();
		
		try {
			db.Open();

			String sql = "INSERT INTO products(name, quantity, price, category_id) VALUES('" + product.getName() + "'," + product.getQuantity() + "," + product.getPrice() + "," + product.getCategoryId() + ")";
			Statement stmt = db.connect.createStatement();
			stmt.executeUpdate(sql);
			
			JOptionPane.showMessageDialog(null, "Ürün eklendi");
			
			status = true;
			stmt.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}

		db.Close();
		return status;
	}	

	public boolean UpdateProduct(Product product) throws SQLException {
		boolean status = false;
		DbConnection db = new DbConnection();
		
		try {
			db.Open();

			String sql = "UPDATE products SET name='" + product.getName() + "', price="+ product.getPrice() +", category_id=" + product.getCategoryId() + " WHERE id=" + product.getId();
			Statement stmt = db.connect.createStatement();
			stmt.executeUpdate(sql);
			
			JOptionPane.showMessageDialog(null, "Ürün güncellendi");
			
			status = true;
			stmt.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}

		db.Close();
		return status;
	}	

	public boolean DeleteProduct(int productId) throws SQLException {
		boolean status = false;
		DbConnection db = new DbConnection();
		
		try {
			db.Open();

			String sql = "DELETE FROM products WHERE id=" + productId;
			Statement stmt = db.connect.createStatement();
			stmt.executeUpdate(sql);
			
			JOptionPane.showMessageDialog(null, "Ürün silindi");
			
			status = true;
			stmt.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}

		db.Close();
		return status;
	}	

	public ArrayList<Product> GetProducts() throws SQLException {
		ArrayList<Product> products = new ArrayList<Product>();
		DbConnection db = new DbConnection();
		
		try {
			db.Open();

			String sql = "SELECT * FROM products";
			Statement stmt = db.connect.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
            	products.add(new Product(rs.getInt("id"), rs.getString("name"), rs.getInt("quantity"), rs.getFloat("price"), rs.getInt("category_id")));
            }

            rs.close();
			stmt.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}

		db.Close();
		return products;
	}	

	public boolean AddStockProduct(int productId, int quantity) throws SQLException {
		boolean status = false;
		DbConnection db = new DbConnection();
		
		try {
			db.Open();

			String sql = "UPDATE products SET quantity = (quantity + " + quantity + ") WHERE id = " + productId;
			Statement stmt = db.connect.createStatement();
			stmt.executeUpdate(sql);
			
			JOptionPane.showMessageDialog(null, "Stok güncellendi");
			
			status = true;
			stmt.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}

		db.Close();
		return status;
	}	

}
