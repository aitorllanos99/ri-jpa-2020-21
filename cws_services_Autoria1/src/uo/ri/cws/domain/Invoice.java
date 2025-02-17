package uo.ri.cws.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import alb.util.assertion.StateChecks;
import alb.util.math.Round;
import uo.ri.cws.domain.base.BaseEntity;

public class Invoice extends BaseEntity {

	private static LocalDate VAT_THRESHOLD;

	static {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2012);
		c.set(Calendar.MONTH, 7);
		c.set(Calendar.DAY_OF_MONTH, 1);

		VAT_THRESHOLD = LocalDate.ofInstant(Instant.ofEpochMilli(c.getTime().getTime()), ZoneId.systemDefault());
	}

	public enum InvoiceStatus {
		NOT_YET_PAID, PAID
	}

	// natural attributes
	private Long number;
	private LocalDate date;
	private double amount;
	private double vat;

	private InvoiceStatus status = InvoiceStatus.NOT_YET_PAID;

	// accidental attributes
	private Set<WorkOrder> workOrders = new HashSet<>();
	private Set<Charge> charges = new HashSet<>();

	Invoice() {
	}

	public Invoice(Long number) {
		this(number, LocalDate.now(), List.of());
	}

	public Invoice(Long number, LocalDate date) {
		ArgumentChecks.isNotNull(date);
		ArgumentChecks.isNotNull(number);
		this.number = number;
		this.date = date;
	}

	public Invoice(Long number, List<WorkOrder> workOrders) {
		this(number, LocalDate.now());
		addWorkOrders(workOrders);
	}

	public Invoice(Long number, LocalDate date, List<WorkOrder> workOrders) {
		this(number, date);
		addWorkOrders(workOrders);
	}

	private void addWorkOrders(List<WorkOrder> workOrders) {
		ArgumentChecks.isNotNull(workOrders);
		for (WorkOrder w : workOrders) {
			this.addWorkOrder(w);
		}
	}

	/**
	 * Computed amount and vat (vat depends on the date)
	 */
	private void computeAmount() {
		amount = .0;
		vat = date.isBefore(VAT_THRESHOLD) ? 18 : 21;
		for (WorkOrder w : workOrders)
			amount += w.getAmount();

		amount = Round.twoCents(amount * (vat / 100.0 + 1));
	}

	/**
	 * Adds (double links) the workOrder to the invoice and updates the amount and
	 * vat
	 * 
	 * @param workOrder
	 * @see InvoiceStatus diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void addWorkOrder(WorkOrder workOrder) {
		StateChecks.isTrue(status.equals(InvoiceStatus.NOT_YET_PAID), "Invoice is paid");
		Associations.ToInvoice.link(workOrder, this);
		workOrder.markAsInvoiced();
		computeAmount();
	}

	/**
	 * Removes a work order from the invoice and recomputes amount and vat
	 * 
	 * @param workOrder
	 * @see InvoiceStatus diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void removeWorkOrder(WorkOrder workOrder) {
		StateChecks.isTrue(status.equals(InvoiceStatus.NOT_YET_PAID), "Invoice is paid");
		Associations.ToInvoice.unlink(workOrder, this);
		workOrder.markBackToFinished();
		computeAmount();
	}

	/**
	 * Marks the invoice as PAID, but
	 * 
	 * @throws IllegalStateException if - Is already settled - Or the amounts paid
	 *                               with charges to payment means do not cover the
	 *                               total of the invoice
	 */
	public void settle() {
		StateChecks.isFalse(status.equals(InvoiceStatus.PAID), "Invoice is already settled");
		double money = charges.stream().mapToDouble(c -> c.getAmount()).sum();
		StateChecks.isTrue(amount > money, "Payment Mean does not cover total amount");
		status = InvoiceStatus.PAID;
	}

	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>(workOrders);
	}

	Set<WorkOrder> _getWorkOrders() {
		return workOrders;
	}

	public Set<Charge> getCharges() {
		return new HashSet<>(charges);
	}

	Set<Charge> _getCharges() {
		return charges;
	}

	public static LocalDate getVAT_THRESHOLD() {
		return VAT_THRESHOLD;
	}

	public Long getNumber() {
		return number;
	}

	public LocalDate getDate() {
		return date;
	}

	public double getAmount() {
		return amount;
	}

	public double getVat() {
		return vat;
	}

	public InvoiceStatus getStatus() {
		return status;
	}

	public boolean isNotSettled() {
		return status.equals(InvoiceStatus.NOT_YET_PAID);
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
		Invoice other = (Invoice) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Invoice [number=" + number + ", date=" + date + ", amount=" + amount + ", vat=" + vat + ", status="
				+ status + "]";
	}
	
}
