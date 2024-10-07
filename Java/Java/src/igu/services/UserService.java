package igu.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import igu.database.DbConnection;
import igu.models.User;

public class UserService {

	public User Login(String email, String password) throws SQLException {
		User user = null;
		DbConnection db = new DbConnection();
		
		try {
			db.Open();

			String sql = "SELECT id, name, email, password, role FROM users WHERE email='" + email + "'";
			Statement stmt = db.connect.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				if (rs.getString("password").equals(password)) {
					user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("role"));
				} else {
					JOptionPane.showMessageDialog(null, "Kullanıcı bilgisi hatalı");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Kullanıcı bilgisi hatalı");
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}

		db.Close();
		return user;
	}
}
