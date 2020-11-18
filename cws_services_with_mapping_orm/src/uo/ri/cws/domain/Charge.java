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

}
