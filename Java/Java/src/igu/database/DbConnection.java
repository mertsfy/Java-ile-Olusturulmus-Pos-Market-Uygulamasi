package igu.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	
	public Connection connect = null;

	String connectionUrl = "jdbc:mysql://localhost:3306/pos";

    public void Open()
    {
    	try 
    	{
    		connect = DriverManager.getConnection(connectionUrl, "igu", "igu"); 
    	} catch (SQLException e) {
    		System.out.print(e.getMessage());
    	}
    }
    
    public void Close() throws SQLException
    {
    	connect.close();
    }    

}
