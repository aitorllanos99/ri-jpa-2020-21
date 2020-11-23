package uo.ri.cws.domain;

import alb.util.assertion.ArgumentChecks;

public class OrderLine {
	private double price;
	private int quantity;

	private SparePart sparepart;

	OrderLine() {
	}

	public OrderLine(SparePart sparePart, double price) {
		ArgumentChecks.isNotNull(sparePart);
		ArgumentChecks.isTrue(sparePart.getStock() <= sparePart.getMinStock());
		ArgumentChecks.isTrue(price >= 0);
		this.sparepart = sparePart;
		this.price = price;
		this.quantity = sparePart.getMaxStock() - sparePart.getStock();
	}

	public OrderLine(SparePart sparePart, double price, int cantidad) {
		this(sparePart, price);
		ArgumentChecks.isTrue(cantidad >= 0);
		this.quantity = cantidad;
	}

	public double getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public SparePart getSparePart() {
		return sparepart;
	}

	void _setSparepart(SparePart sparepart) {
		this.sparepart = sparepart;
	}

	public void receive() {
		sparepart.updatePriceAndStock(this.price, sparepart.getQuantityToOrder());

	}

	public double getAmount() {
		return price * quantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + quantity;
		result = prime * result + ((sparepart == null) ? 0 : sparepart.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderLine other = (OrderLine) obj;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (quantity != other.quantity)
			return false;
		if (sparepart == null) {
			if (other.sparepart != null)
				return false;
		} else if (!sparepart.equals(other.sparepart))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderLine [price=" + price + ", quantity=" + quantity + ", sparepart=" + sparepart + "]";
	}

}
