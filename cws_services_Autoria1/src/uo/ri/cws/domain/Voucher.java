package uo.ri.cws.domain;

import alb.util.assertion.ArgumentChecks;
import alb.util.assertion.StateChecks;

public class Voucher extends PaymentMean {
	private String code;
	private double available;
	private String description;

	Voucher() {
	}

	public Voucher(String code) {
		ArgumentChecks.isNotEmpty(code);
		this.code = code;
	}

	public Voucher(String code, double available) {
		this(code);
		ArgumentChecks.isTrue(available >= 0);
		this.available = available;
	}

	public Voucher(String code, String description , double available) {
		this(code, available);
		ArgumentChecks.isNotEmpty(description);
		this.description = description;
	}

	/**
	 * Augments the accumulated and decrements the available
	 * 
	 * @throws IllegalStateException if not enough available to pay
	 */
	@Override
	public void pay(double amount) {
		StateChecks.isFalse(getAvailable() < amount, "No hay suficiente dinero para realizar el pago.");
		setAccumulated(getAccumulated() + amount);
		available -= amount;
	}

	public String getCode() {
		return code;
	}

	public double getAvailable() {
		return available;
	}

	public String getDescription() {
		return description;
	}

	public void setDescripcion(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		Voucher other = (Voucher) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Voucher [code=" + code + ", available=" + available + ", description=" + description + "]";
	}
}
