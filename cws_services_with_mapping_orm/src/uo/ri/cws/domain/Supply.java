package uo.ri.cws.domain;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;


public class Supply extends BaseEntity {
	private int deliveryTerm;
	private double price;
	
	private Provider provider;
	
	private SparePart sparePart;

	
	Supply() {}

	public Supply(Provider p1, SparePart sp1, double deliveryTerm, int price) {
		ArgumentChecks.isNotNull(p1);
		ArgumentChecks.isNotNull(sp1);
		ArgumentChecks.isTrue(deliveryTerm > 0);
		ArgumentChecks.isTrue(price >= 0);
		Associations.Supplies.link(sp1,this,p1);
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
	
}
