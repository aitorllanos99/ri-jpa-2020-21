package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import alb.util.assertion.StateChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class Order extends BaseEntity {
	public enum OrderStatus {
		PENDING, RECEIVED, CANCELLED
	}

	private String code;
	private double amount;
	private LocalDate orderedDate;

	private LocalDate receptionDate;

	private OrderStatus status;

	private Set<OrderLine> orderlines = new HashSet<>();

	private Provider provider;

	Order() {
	}

	public Order(String code) {
		ArgumentChecks.isNotNull(code);
		ArgumentChecks.isNotEmpty(code);
		this.code = code;
		this.status = OrderStatus.PENDING;
	}

	public Order(String code, double amount, LocalDate orderedDate, LocalDate recievedDate) {
		this(code);
		ArgumentChecks.isNotNull(orderedDate);
		ArgumentChecks.isNotNull(recievedDate);
		ArgumentChecks.isTrue(amount >= 0);
		this.amount = amount;
		this.orderedDate = orderedDate;
		this.receptionDate = recievedDate;
	}

	public Order(String code, double amount, LocalDate orderedDate, LocalDate recievedDate, Provider provider) {
		this(code, amount, orderedDate, recievedDate);
		ArgumentChecks.isNotNull(provider);
		this.provider = provider;
	}

	public String getCode() {
		return code;
	}

	public double getAmount() {
		return amount;
	}

	public LocalDate getOrderedDate() {
		return orderedDate;
	}

	public LocalDate getReceptionDate() {
		return receptionDate;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public Set<OrderLine> getOrderLines() {
		return new HashSet<>(orderlines);
	}

	Set<OrderLine> _getOrderLines() {
		return orderlines;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setOrderedDate(LocalDate orderedDate) {
		this.orderedDate = orderedDate;
	}

	public void setRecievedDate(LocalDate recievedDate) {
		this.receptionDate = recievedDate;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public Provider getProvider() {
		return provider;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [code=" + code + ", amount=" + amount + ", orderedDate=" + orderedDate + ", recievedDate="
				+ receptionDate + ", status=" + status + "]";
	}

	public boolean isPending() {
		return status.equals(OrderStatus.PENDING);
	}

	public boolean isReceived() {
		return status.equals(OrderStatus.RECEIVED);
	}
	
	public void cancel() {
		this.status = OrderStatus.CANCELLED;
	}

	public void addSparePartFromSupply(Supply supply) {
		ArgumentChecks.isTrue(supply != null, "The supply cant be null");
		for (OrderLine ol : orderlines)
			StateChecks.isFalse(ol.getSparePart().getId().contentEquals(supply.getSparePart().getId()) && isPending(),
					"The spare is in order with different provider");

		boolean continuar = true;
		// Add for a spare with stock equals to min stock
		if (supply.getSparePart().getStock() == supply.getSparePart().getMinStock())
			continuar = false;

		if (continuar) {
			int cantidad = supply.getSparePart().getQuantityToOrder();
			OrderLine orderLine = new OrderLine(supply.getSparePart(), supply.getPrice(), cantidad);
			orderlines.add(orderLine);
			this.amount += orderLine.getAmount();
		}

		this.orderedDate = LocalDate.now();

	}

	public void receive() {
		StateChecks.isFalse(isReceived(), "Already received");

		this.status = OrderStatus.RECEIVED;
		this.receptionDate = LocalDate.now();
		for (OrderLine ol : orderlines) {
			ol.receive();
		}
	}

	public void removeSparePart(SparePart sparePart) {
		ArgumentChecks.isTrue(sparePart != null, "The sparepart cant be null");

		for (OrderLine ol : orderlines) {
			if (ol.getSparePart().equals(sparePart))
				orderlines.remove(ol);
		}
	}

}
