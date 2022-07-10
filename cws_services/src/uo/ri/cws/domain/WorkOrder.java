package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class WorkOrder extends BaseEntity {
	public enum WorkOrderStatus {
		OPEN, ASSIGNED, FINISHED, INVOICED
	}

	// natural attributes
	private LocalDateTime date;
	private String description;
	private double amount;
	private WorkOrderStatus status = WorkOrderStatus.OPEN;

	private boolean usedForVoucher;

	// accidental attributes
	private Vehicle vehicle;
	private Mechanic mechanic;
	private Invoice invoice;
	private Set<Intervention> interventions = new HashSet<>();

	WorkOrder() {

	}

	public WorkOrder(Vehicle vehicle, String description) {
		this(vehicle, LocalDateTime.now(), description);
	}

	public WorkOrder(Vehicle vehicle) {
		this(vehicle, LocalDateTime.now(), "No description");
	}

	public WorkOrder(Vehicle vehicle, LocalDateTime date, String description) {
		ArgumentChecks.isNotNull(vehicle);
		ArgumentChecks.isNotNull(date);
		ArgumentChecks.isNotEmpty(description);
		this.description = description;
		this.date = date.truncatedTo(ChronoUnit.MILLIS);
		Associations.Fix.link(vehicle, this);
	}

	private void computeAmount() {
		amount = 0.0;

		for (Intervention i : interventions) {
			amount += i.getAmount();
		}

	}

	/**
	 * Changes it to INVOICED state given the right conditions This method is
	 * called from Invoice.addWorkOrder(...)
	 * 
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not FINISHED, or -
	 *                               The work order is not linked with the
	 *                               invoice
	 */
	public void markAsInvoiced() {

		if (!isFinished())
			throw new IllegalStateException("The work order is finished");
		if (invoice == null)
			throw new IllegalStateException(
					"There is no invoice for this work order");
		this.status = WorkOrderStatus.INVOICED;
	}

	/**
	 * Changes it to FINISHED state given the right conditions and computes the
	 * amount
	 *
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in ASSIGNED
	 *                               state, or - The work order is not linked
	 *                               with a mechanic
	 */
	public void markAsFinished() {

		if (!status.equals(WorkOrderStatus.ASSIGNED)) {
			throw new IllegalStateException("The work order is not assigned");
		}

		if (mechanic == null) {
			throw new IllegalStateException("The work order has no mechanic");
		}

		this.status = WorkOrderStatus.FINISHED;
		computeAmount();
	}

	/**
	 * Changes it back to FINISHED state given the right conditions This method
	 * is called from Invoice.removeWorkOrder(...)
	 * 
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not INVOICED, or -
	 *                               The work order is still linked with the
	 *                               invoice
	 */
	public void markBackToFinished() {

		if (!isInvoiced()) {
			throw new IllegalStateException("The work order is not invoiced");
		}

		if (invoice != null) {
			throw new IllegalStateException(
					"The work order is linked with an invoice");
		}
		this.status = WorkOrderStatus.FINISHED;
		computeAmount();
	}

	/**
	 * Links (assigns) the work order to a mechanic and then changes its status
	 * to ASSIGNED
	 * 
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in OPEN status,
	 *                               or - The work order is already linked with
	 *                               another mechanic
	 */
	public void assignTo(Mechanic mechanic) {

		if (!isOpen())
			throw new IllegalStateException(
					"The work order is not in open status");
		if (this.mechanic != null)
			throw new IllegalStateException("The mechanic is already linked");

		Associations.Assign.link(mechanic, this);
		this.status = WorkOrderStatus.ASSIGNED;

	}

	/**
	 * Unlinks (deassigns) the work order and the mechanic and then changes its
	 * status back to OPEN
	 * 
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in ASSIGNED
	 *                               status
	 */
	public void desassign() {

		if (!status.equals(WorkOrderStatus.ASSIGNED)) {
			throw new IllegalStateException(
					"The work order is not in assigned status");
		}

		ArgumentChecks.isTrue(!isAssigned());

		Associations.Assign.unlink(mechanic, this);

		this.status = WorkOrderStatus.OPEN;
	}

	/**
	 * In order to assign a work order to another mechanic is first have to be
	 * moved back to OPEN state and unlinked from the previous mechanic.
	 * 
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in FINISHED
	 *                               status
	 */
	public void reopen() {

		ArgumentChecks.isTrue(isFinished());

		Associations.Assign.unlink(mechanic, this);

		this.status = WorkOrderStatus.OPEN;

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

	@Override
	public String toString() {
		return "WorkOrder [date=" + date + ", description=" + description
				+ ", amount=" + amount + ", status=" + status + ", vehicle="
				+ vehicle + "]";
	}

	public Vehicle getVehicle() {
		return this.vehicle;
	}

	public Invoice getInvoice() {
		return this.invoice;
	}

	public boolean isInvoiced() {
		return status.equals(WorkOrderStatus.INVOICED);
	}

	public boolean isFinished() {
		return status.equals(WorkOrderStatus.FINISHED);
	}

	public boolean isOpen() {
		return status.equals(WorkOrderStatus.OPEN);
	}

	public boolean isAssigned() {
		return status.equals(WorkOrderStatus.ASSIGNED);
	}

	public Mechanic getMechanic() {
		return this.mechanic;
	}

	public double getAmount() {
		return amount;
	}

	public boolean canBeUsedForVoucher() {
		if (usedForVoucher)
			return false;

		// An invoiced and paid workOrder can be used for a voucher
		if (this.invoice != null && this.invoice.isSettled())
			return true;

		return false;
	}

	public void markAsUsedForVoucher() {
		this.usedForVoucher = true;

	}

	public boolean isPaid() {
		if (this.invoice == null)
			return false;

		return this.invoice.isSettled();
	}

	public LocalDateTime getDate() {
		return this.date;
	}

	public String getDescription() {
		return this.description;
	}

	public WorkOrderStatus getStatus() {
		return this.status;
	}

}
