package uo.ri.cws.domain;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;


public class Supply extends BaseEntity {
	private int deliveryTerm;
	private double price;
	
	private Provider provider;
	
	private SparePart sparePart;

	
	Supply() {}

	public Supply(Provider p1, SparePart sp1, double price, int deliveryTerm) {
		ArgumentChecks.isNotNull(p1);
		ArgumentChecks.isNotNull(sp1);
		ArgumentChecks.isTrue(deliveryTerm > 0);
		ArgumentChecks.isTrue(price >= 0);
		Associations.Supplies.link(sp1,this,p1);
		this.price = price;
		this.deliveryTerm = deliveryTerm;
	}

	public int getDeliveryTerm() {
		return deliveryTerm;
	}

	public double getPrice() {
		return price;
	}

	public Provider getProvider() {
		return provider;
	}

	public SparePart getSparePart() {
		return sparePart;
	}

	void _setSparePart(SparePart sp) {
		this.sparePart = sp;
	}

	void _setProvider(Provider p) {
		this.provider = p;
	}

	public void setDeliveryTerm(int deliveryTerm) {
		this.deliveryTerm = deliveryTerm;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((provider == null) ? 0 : provider.hashCode());
		result = prime * result + ((sparePart == null) ? 0 : sparePart.hashCode());
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
		Supply other = (Supply) obj;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider))
			return false;
		if (sparePart == null) {
			if (other.sparePart != null)
				return false;
		} else if (!sparePart.equals(other.sparePart))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Supply [deliveryTerm=" + deliveryTerm + ", price=" + price + ", provider=" + provider + ", sparePart="
				+ sparePart + "]";
	}
	
	
	
}
