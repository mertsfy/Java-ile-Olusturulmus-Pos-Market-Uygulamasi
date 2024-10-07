package igu.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import igu.database.DbConnection;
import igu.models.Category;

public class CategoryService {

	public Category GetCategoryByName(String name, int categoryId) throws SQLException {
		Category category = null;
		DbConnection db = new DbConnection();
		
		try {
			db.Open();

			String sql = "SELECT * FROM categories WHERE name='" + name + "' AND id!=" + categoryId;
			Statement stmt = db.connect.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				category = new Category(rs.getInt("id"), rs.getString("name"));
			} 
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}

		db.Close();
		return category;
	}
	
	public boolean AddCategory(String name) throws SQLException {
		boolean status = false;
		DbConnection db = new DbConnection();
		
		try {
			db.Open();

			String sql = "INSERT INTO categories(name) VALUES('" + name + "')";
			Statement stmt = db.connect.createStatement();
			stmt.executeUpdate(sql);
			
			JOptionPane.showMessageDialog(null, "Kategori eklendi");
			
			status = true;
			stmt.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}

		db.Close();
		return status;
	}	
	
	public boolean UpdateCategory(String name, int categoryId) throws SQLException {
		boolean status = false;
		DbConnection db = new DbConnection();
		
		try {
			db.Open();

			String sql = "UPDATE categories SET name='" + name + "' WHERE id=" + categoryId;
			Statement stmt = db.connect.createStatement();
			stmt.executeUpdate(sql);
			
			JOptionPane.showMessageDialog(null, "Kategori güncellendi");
			
			status = true;
			stmt.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}

		db.Close();
		return status;
	}	
	
	public boolean DeleteCategory(int categoryId) throws SQLException {
		boolean status = false;
		DbConnection db = new DbConnection();
		
		try {
			db.Open();

			String sql = "DELETE FROM categories WHERE id=" + categoryId;
			Statement stmt = db.connect.createStatement();
			stmt.executeUpdate(sql);
			
			JOptionPane.showMessageDialog(null, "Kategori silindi");
			
			status = true;
			stmt.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}

		db.Close();
		return status;
	}

	public ArrayList<Category> GetCategories() throws SQLException {
		ArrayList<Category> categories = new ArrayList<Category>();
		DbConnection db = new DbConnection();
		
		try {
			db.Open();

			String sql = "SELECT * FROM categories";
			Statement stmt = db.connect.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                categories.add(new Category(rs.getInt("id"), rs.getString("name")));
            }

            rs.close();
			stmt.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}

		db.Close();
		return categories;
	}
		
}
