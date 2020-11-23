package uo.ri.cws.domain;

import alb.util.assertion.ArgumentChecks;
import alb.util.assertion.StateChecks;
import uo.ri.cws.domain.Invoice.InvoiceStatus;
import uo.ri.cws.domain.base.BaseEntity;

public class Charge extends BaseEntity {
	private Invoice invoice;
	private PaymentMean paymentMean;
	private double amount;

	Charge() {
	}

	public Charge(Invoice invoice, PaymentMean paymentMean, double amount) {
		ArgumentChecks.isNotNull(invoice);
		ArgumentChecks.isNotNull(paymentMean);
		ArgumentChecks.isTrue(amount >= 0);
		this.amount = amount;
		paymentMean.pay(amount);
		Associations.Charges.link(invoice, this, paymentMean);
	}

	/**
	 * Unlinks this charge and restores the value to the payment mean
	 * 
	 * @throws IllegalStateException if the invoice is already settled
	 */
	public void rewind() {
		StateChecks.isFalse(invoice.getStatus().equals(InvoiceStatus.PAID), "La factura ya esta pagada");
		paymentMean.pay(-amount);
		Associations.Charges.unlink(this);
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public PaymentMean getPaymentMean() {
		return paymentMean;
	}

	public double getAmount() {
		return amount;
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	void _setPaymentMean(PaymentMean paymentMean) {
		this.paymentMean = paymentMean;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((invoice == null) ? 0 : invoice.hashCode());
		result = prime * result + ((paymentMean == null) ? 0 : paymentMean.hashCode());
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
		Charge other = (Charge) obj;
		if (invoice == null) {
			if (other.invoice != null)
				return false;
		} else if (!invoice.equals(other.invoice))
			return false;
		if (paymentMean == null) {
			if (other.paymentMean != null)
				return false;
		} else if (!paymentMean.equals(other.paymentMean))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Charge [invoice=" + invoice + ", paymentMean=" + paymentMean + ", amount=" + amount + "]";
	}
	
	

}
