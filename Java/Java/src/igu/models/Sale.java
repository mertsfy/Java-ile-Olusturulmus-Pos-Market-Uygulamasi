package igu.models;

import java.util.Date;

public class Sale {
	
	private Date saleDate;
	private int count;
	private float amount;

    public Sale() { }

    public Sale(Date saleDate, int count, float amount) {
        this.saleDate = saleDate;
        this.count = count;
        this.amount = amount;
    }

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

}