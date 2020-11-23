package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import alb.util.assertion.StateChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class WorkOrder extends BaseEntity {
	public enum WorkOrderStatus {
		OPEN, ASSIGNED, FINISHED, INVOICED
	}

	// natural attributes
	private LocalDateTime date;
	private String description;
	private double amount = 0.0;

	private WorkOrderStatus status = WorkOrderStatus.OPEN;

	// accidental attributes
	private Vehicle vehicle;
	private Mechanic mechanic;
	private Invoice invoice;
	private Set<Intervention> interventions = new HashSet<>();

	WorkOrder() {
	}

	public WorkOrder(Vehicle vehicle) {
		ArgumentChecks.isNotNull(vehicle);
		this.date = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
		Associations.Fix.link(vehicle, this);
	}

	public WorkOrder(Vehicle vehicle, String description) {
		this(vehicle);
		ArgumentChecks.isNotEmpty(description);
		this.description = description;
	}

	public WorkOrder(Vehicle vehicle, LocalDateTime now, String description) {
		this(vehicle, description);
		this.date = now;
	}

	/**
	 * Changes it to INVOICED state given the right conditions This method is called
	 * from Invoice.addWorkOrder(...)
	 * 
	 * @see WorkOrderStatus diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not FINISHED, or - The
	 *                               work order is not linked with the invoice
	 */
	public void markAsInvoiced() {
		StateChecks.isTrue(status.equals(WorkOrderStatus.FINISHED), "Work order " + getId() + " is not finished");
		StateChecks.isTrue(invoice != null, "Work order " + getId() + " is not linked with a mechanic");
		status = WorkOrderStatus.INVOICED;
	}

	/**
	 * Changes it to FINISHED state given the right conditions and computes the
	 * amount
	 * 
	 * @see state diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in ASSIGNED state,
	 *                               or - The work order is not linked with a
	 *                               mechanic
	 */
	public void markAsFinished() {
		StateChecks.isTrue(status.equals(WorkOrderStatus.ASSIGNED), "Work order " + getId() + " is not assigned");
		StateChecks.isTrue(mechanic != null, "Work order " + getId() + " is not linked with a mechanic");
		computeAmount();
		Associations.Assign.unlink(mechanic, this);
		status = WorkOrderStatus.FINISHED;
	}

	/**
	 * Changes it back to FINISHED state given the right conditions This method is
	 * called from Invoice.removeWorkOrder(...)
	 * 
	 * @see WorkOrderStatus diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not INVOICED, or - The
	 *                               work order is still linked with the invoice
	 */
	public void markBackToFinished() {
		StateChecks.isTrue(status.equals(WorkOrderStatus.INVOICED), "Work order " + getId() + " is not invoiced");
		StateChecks.isTrue(invoice == null, "Work order " + getId() + " is still linked with the invoice");
		status = WorkOrderStatus.FINISHED;
	}

	/**
	 * Links (assigns) the work order to a mechanic and then changes its status to
	 * ASSIGNED
	 * 
	 * @see WorkOrderStatus diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in OPEN status, or -
	 *                               The work order is already linked with another
	 *                               mechanic
	 */
	public void assignTo(Mechanic mechanic) {
		StateChecks.isTrue(status.equals(WorkOrderStatus.OPEN), "Work order " + getId() + " is not open");
		Associations.Assign.link(mechanic, this);
		status = WorkOrderStatus.ASSIGNED;
	}

	/**
	 * Unlinks (deassigns) the work order and the mechanic and then changes its
	 * status back to OPEN
	 * 
	 * @see WorkOrderStatus diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in ASSIGNED status
	 */
	public void desassign() {
		StateChecks.isTrue(status.equals(WorkOrderStatus.ASSIGNED), "Work order " + getId() + " is not assigned");
		Associations.Assign.unlink(mechanic, this);
		status = WorkOrderStatus.OPEN;
	}

	/**
	 * In order to assign a work order to another mechanic is first have to be moved
	 * back to OPEN state and unlinked from the previous mechanic.
	 * 
	 * @see WorkOrderStatus diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in FINISHED status
	 */
	public void reopen() {
		StateChecks.isTrue(status.equals(WorkOrderStatus.FINISHED), "Work order " + getId() + " is not finished");
		status = WorkOrderStatus.OPEN;
	}

	private void computeAmount() {
		amount = 0.0;
		for (Intervention intervencion : interventions)
			amount += intervencion.getAmount();
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>(interventions);
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}

	void _setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public double getAmount() {
		computeAmount();
		return amount;
	}

	public WorkOrderStatus getStatus() {
		return status;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public boolean isInvoiced() {
		return status.equals(WorkOrderStatus.INVOICED);
	}

	public boolean isFinished() {
		return status.equals(WorkOrderStatus.FINISHED);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((vehicle == null) ? 0 : vehicle.hashCode());
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
		WorkOrder other = (WorkOrder) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (vehicle == null) {
			if (other.vehicle != null)
				return false;
		} else if (!vehicle.equals(other.vehicle))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WorkOrder [date=" + date + ", description=" + description + ", amount=" + amount + ", status=" + status
				+ ", vehicle=" + vehicle + ", mechanic=" + mechanic + ", invoice=" + invoice + ", interventions="
				+ interventions + "]";
	}

}
