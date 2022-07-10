package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class Invoice extends BaseEntity {
	public enum InvoiceStatus {
		NOT_YET_PAID, PAID
	}

	// natural attributes

	private Long number;
	private double amount;
	private LocalDate date;

	private InvoiceStatus status = InvoiceStatus.NOT_YET_PAID;
	private double vat;

	// accidental attributes

	private Set<WorkOrder> workOrders = new HashSet<>();

	private Set<Charge> charges = new HashSet<>();
	private boolean usedForVoucher = false;

	Invoice() {

	}

	public Invoice(Long number) {
		this(number, LocalDate.now(), new ArrayList<>());
	}

	public Invoice(Long number, LocalDate date) {
		// call full constructor with sensible defaults
		this(number, date, new ArrayList<>());
	}

	public Invoice(Long number, List<WorkOrder> workOrders) {
		this(number, LocalDate.now(), workOrders);
	}

	// full constructor
	public Invoice(Long number, LocalDate date, List<WorkOrder> workOrders) {
		// check arguments (always), through IllegalArgumentException
		// store the number
		// store a copy of the date
		// add every work order calling addWorkOrder( w )

		ArgumentChecks.isNotNull(number);
		ArgumentChecks.isNotNull(date);
		ArgumentChecks.isNotNull(workOrders);

		this.number = number;
		this.date = date;

		for (WorkOrder i : workOrders) {
			addWorkOrder(i);
		}

	}

	/**
	 * Computes amount and vat (vat depends on the date)
	 */
	private void computeAmount() {

		if (date.compareTo(LocalDate.parse("2012-07-01")) < 0) {
			vat = 0.18;
		} else {
			vat = 0.21;
		}

		amount = 0;

		for (WorkOrder i : workOrders) {
			amount += i.getAmount();
		}

		amount = amount * (1 + vat);

		amount = (double) Math.round(amount * 100) / 100;
	}

	/**
	 * Adds (double links) the workOrder to the invoice and updates the amount
	 * and vat
	 * 
	 * @param workOrder
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void addWorkOrder(WorkOrder workOrder) {
		if (!status.equals(InvoiceStatus.NOT_YET_PAID)) {
			throw new IllegalStateException(
					"Error: not paid invoice with number: " + number);
		}

		Associations.ToInvoice.link(this, workOrder);
		workOrder.markAsInvoiced();
		computeAmount();
	}

	/**
	 * Removes a work order from the invoice and recomputes amount and vat
	 * 
	 * @param workOrder
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void removeWorkOrder(WorkOrder workOrder) {
		if (!status.equals(InvoiceStatus.NOT_YET_PAID)) {
			throw new IllegalStateException(
					"Error: not paid invoice with number: " + number);
		}
		Associations.ToInvoice.unlink(this, workOrder);
		workOrder.markBackToFinished();
		computeAmount();
	}

	/**
	 * Marks the invoice as PAID, but
	 * 
	 * @throws IllegalStateException if - Is already settled - Or the amounts
	 *                               paid with charges to payment means do not
	 *                               cover the total of the invoice
	 */
	public void settle() {
		if (status.equals(InvoiceStatus.PAID)) {
			throw new IllegalStateException(
					"Error: already paid invoice with number: " + number);
		}

		double paidAmmount = 0;

		for (Charge i : charges)
			paidAmmount += i.getAmount();

		if (amount - paidAmmount > 0.011 || amount - paidAmmount < -0.011)
			throw new IllegalStateException("The invoice is not totally paid");

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

	public double getAmount() {
		return amount;
	}

	public boolean isNotSettled() {
		return status.equals(InvoiceStatus.NOT_YET_PAID);
	}

	public boolean isSettled() {
		return status.equals(InvoiceStatus.PAID);
	}

	public boolean canGenerate500Voucher() {

		return !isUsedForVoucher() && isSettled() && getAmount() >= 500.0;

	}

	public void markAsUsed() {
		if (!canGenerate500Voucher()) {
			throw new IllegalStateException(
					"This invoice cannot be used to generate a voucher.");

		}

		if (!isUsedForVoucher()) {
			this.usedForVoucher = true;
		}
			
	}

	public boolean isUsedForVoucher() {
		return usedForVoucher;
	}

	public Long getNumber() {
		return this.number;
	}

	public LocalDate getDate() {
		return this.date;
	}

	public double getVat() {
		return this.vat;
	}

	public InvoiceStatus getStatus() {
		return this.status;
	}
}
