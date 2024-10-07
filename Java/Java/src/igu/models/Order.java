package igu.models;

import java.util.Date;

public class Order {
    private int id;
    private Date createdAt;
    
    
    public Order() { }

    public Order(int id, Date createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
