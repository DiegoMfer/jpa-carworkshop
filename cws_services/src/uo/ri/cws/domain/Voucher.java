package uo.ri.cws.domain;

import alb.util.assertion.ArgumentChecks;

public class Voucher extends PaymentMean {
	private String code;
	private double available = 0.0;
	private String description;

	Voucher() {
	}

	public Voucher(String code, String description, double available) {
		this(code, description, available, new Client("testing-dni"));
	}

	public Voucher(String code, String description, double available,
			Client client) {
		ArgumentChecks.isNotEmpty(code);
		ArgumentChecks.isNotEmpty(description);
		ArgumentChecks.isTrue(available >= 0);
		ArgumentChecks.isNotNull(client);

		Associations.Pay.link(client, this);
		this.code = code;
		this.description = description;
		this.available = available;
	}

	public Voucher(String code, double d) {
		this(code, "no-description", d);
	}

	/**
	 * Augments the accumulated (super.pay(amount) ) and decrements the
	 * available
	 * 
	 * @throws IllegalStateException if not enough available to pay
	 */
	@Override
	public void pay(double amount) {

		if (available - amount < 0.0)
			throw new IllegalStateException(
					"There is not available enough to pay");

		super.pay(amount);
		available -= amount;
	}

	public double getAvailable() {
		return available;
	}

	public String getDescription() {
		return this.description;
	}

	public String getCode() {
		return this.code;
	}

	@Override
	public boolean canPay(double amount) {
		return available >= amount;
	}

}
