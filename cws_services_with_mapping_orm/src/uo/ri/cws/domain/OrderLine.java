package uo.ri.cws.domain;

import alb.util.assertion.ArgumentChecks;


public class OrderLine {
	private double price;
	private int quantity;
	 
	private SparePart sparepart;
	 
	private Order order;

	OrderLine() {
	}

	public OrderLine(SparePart sparePart, double price) {
		ArgumentChecks.isTrue(sparePart.getStock() <= sparePart.getMinStock());
		ArgumentChecks.isTrue(price >= 0);
		this.sparepart = sparePart;
		this.price = price;
		this.quantity = sparePart.getMaxStock() - sparePart.getStock();
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

	public Order getOrder() {
		return order;
	}

	void _setSparepart(SparePart sparepart) {
		this.sparepart = sparepart;
	}

	void _setOrder(Order order) {
		this.order = order;
	}

	public void receive() {
		sparepart.updatePriceAndStock(this.price, sparepart.getQuantityToOrder());

	}

	public double getAmount() {
		return price * quantity;
	}

}
