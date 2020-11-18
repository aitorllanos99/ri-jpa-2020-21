package uo.ri.cws.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import alb.util.assertion.StateChecks;
import uo.ri.cws.domain.base.BaseEntity;


public class Order extends BaseEntity {
	public enum OrderStatus {
		PENDING, RECEIVED
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
	}
	
	public Order(String code, double amount,LocalDateTime orderedDate, LocalDateTime recievedDate) {
		ArgumentChecks.isNotNull(code);
		ArgumentChecks.isNotEmpty(code);
		this.code = code;
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

	public void receive() {
		StateChecks.isTrue(status.equals(OrderStatus.PENDING), "Order is recieved");
		this.status = OrderStatus.RECEIVED;
		this.receptionDate = LocalDate.now();
	}

	public void addSparePartFromSupply(Supply supply) {
		ArgumentChecks.isNotNull(supply);
		
	}
	 
	
	
}
