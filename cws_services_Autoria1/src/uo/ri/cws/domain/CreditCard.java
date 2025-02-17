package uo.ri.cws.domain;

import java.time.LocalDate;

import alb.util.assertion.ArgumentChecks;
import alb.util.assertion.StateChecks;

public class CreditCard extends PaymentMean {
	private String number;
	private String type;
	private LocalDate validThru;

	CreditCard() {
	}

	public CreditCard(String number) {
		ArgumentChecks.isNotEmpty(number);
		this.number = number;
	}

	public CreditCard(String number, String type, LocalDate validThru) {
		this(number);
		ArgumentChecks.isNotEmpty(type);
		ArgumentChecks.isNotNull(validThru);
		StateChecks.isFalse(validThru.isBefore(LocalDate.now()), "La tarjeta no es valida");
		this.type = type;
		this.validThru = validThru;
	}

	public String getNumber() {
		return number;
	}

	public String getType() {
		return type;
	}

	public LocalDate getValidThru() {
		return validThru;
	}
	
	

	public void setNumber(String number) {
		this.number = number;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValidThru(LocalDate validThru) {
		this.validThru = validThru;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
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
		CreditCard other = (CreditCard) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

}
