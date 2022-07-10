package uo.ri.cws.domain;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class Charge extends BaseEntity {
	// natural attributes
	private double amount = 0.0;

	// accidental attributes

	private Invoice invoice;

	private PaymentMean paymentMean;

	Charge() {
	}

	public Charge(Invoice invoice, PaymentMean paymentMean, double amount) {
		ArgumentChecks.isNotNull(paymentMean);
		ArgumentChecks.isNotNull(invoice);
		ArgumentChecks.isTrue(amount >= 0);

		this.amount = amount;

		paymentMean.pay(amount);
		Associations.Charges.link(paymentMean, this, invoice);

		// store the amount
		// increment the paymentMean accumulated -> paymentMean.pay( amount )
		// link invoice, this and paymentMean
	}

	/**
	 * Unlinks this charge and restores the accumulated to the payment mean
	 * 
	 * @throws IllegalStateException if the invoice is already settled
	 */
	public void rewind() {
		// asserts the invoice is not in PAID status
		// decrements the payment mean accumulated ( paymentMean.pay( -amount) )
		// unlinks invoice, this and paymentMean
	}

	public Invoice getInvoice() {
		return this.invoice;
	}

	public PaymentMean getPaymentMean() {
		return this.paymentMean;
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	void _setPaymentMean(PaymentMean pm) {
		this.paymentMean = pm;
	}

	public double getAmount() {
		return this.amount;
	}
}
