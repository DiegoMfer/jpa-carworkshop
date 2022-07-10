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

	public Intervention(Mechanic mechanic, WorkOrder workOrder, int minutes) {
		this(mechanic, workOrder, LocalDateTime.now(), minutes);
	}

	public Intervention(Mechanic mechanic, WorkOrder workOrder,
			LocalDateTime date, int minutes) {

		ArgumentChecks.isNotNull(workOrder);
		ArgumentChecks.isNotNull(mechanic);
		ArgumentChecks.isNotNull(date);
		ArgumentChecks.isTrue(minutes >= 0);
		this.minutes = minutes;
		this.date = date.truncatedTo(ChronoUnit.MILLIS);
		Associations.Intervene.link(workOrder, this, mechanic);

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
		return this.workOrder;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	public double getAmount() {
		double result = 0.0;

		for (Substitution s : substitutions) {
			result += s.getQuantity() * s.getSparePart().getPrice();
		}

		result += (this.minutes / 60.0) * this.workOrder.getVehicle()
				.getVehicleType().getPricePerHour();
		return result;
	}

	public void setMinutes(int i) {
		this.minutes = i;

	}

	public LocalDateTime getDate() {
		return this.date;
	}

	public int getMinutes() {
		return this.minutes;
	}

}
