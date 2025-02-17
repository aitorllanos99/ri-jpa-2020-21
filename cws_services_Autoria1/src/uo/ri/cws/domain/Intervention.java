package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class Intervention extends BaseEntity {
	// natural attributes
	private LocalDateTime date;
	private int minutes;

	// accidental attributes
	private WorkOrder workOrder;
	private Mechanic mechanic;
	private Set<Substitution> substitutions = new HashSet<>();

	Intervention() {
	}

	public Intervention(Mechanic mechanic, WorkOrder workOrder) {
		ArgumentChecks.isNotNull(mechanic);
		ArgumentChecks.isNotNull(workOrder);
		this.date = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
		Associations.Intervene.link(mechanic, this, workOrder);
	}

	public Intervention(Mechanic mechanic, WorkOrder workOrder, int minutes) {
		this(mechanic, workOrder);
		ArgumentChecks.isTrue(minutes >= 0);
		this.minutes = minutes;
	}

	public Intervention(Mechanic mechanic, WorkOrder workOrder, LocalDateTime date, int i) {
		this(mechanic, workOrder, i);
		this.date = date.truncatedTo(ChronoUnit.MILLIS);
	}

	void _setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public Set<Substitution> getSubstitutions() {
		return new HashSet<>(substitutions);
	}

	Set<Substitution> _getSubstitutions() {
		return substitutions;
	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	public double getAmount() {

		double importe = 0.0;

		for (Substitution sustitucion : substitutions)
			importe += sustitucion.getAmount();

		importe += workOrder.getVehicle().getVehicleType().getPricePerHour() * ((double) minutes) / 60.0;

		return importe;
	}

	public int getMinutes() {
		return minutes;
	}

	public LocalDateTime getDate() {
		return date;
	}

	
	
}
