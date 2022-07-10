package uo.ri.cws.domain;

import java.time.LocalDate;

import alb.util.assertion.ArgumentChecks;

public class CreditCard extends PaymentMean {

	private String number;
	private String type;
	private LocalDate validThru;

	CreditCard() {
	}

	public CreditCard(String number, String type, LocalDate validThru,
			Client client) {
		ArgumentChecks.isNotEmpty(type);
		ArgumentChecks.isNotNull(validThru);
		ArgumentChecks.isNotEmpty(number);
		ArgumentChecks.isNotNull(client);

		this.number = number;
		this.type = type;
		this.validThru = validThru;

		Associations.Pay.link(client, this);
	}

	public CreditCard(String number, String type, LocalDate validThru) {
		this(number, type, validThru, new Client("Test_client_dni"));
	}

	public CreditCard(String number) {
		this(number, "UNKNOWN", LocalDate.now().plusDays(1));
	}

	@Override
	public void pay(double amount) {
		if (!isValidNow())
			throw new IllegalStateException("Card is expired");

		super.pay(amount);
	}

	public String getNumber() {
		return number;
	}

	public String getType() {
		return type;
	}

	public LocalDate getValidThru() {
		return validThru;
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
		CreditCard other = (CreditCard) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

	public boolean isValidNow() {
		return validThru.compareTo(LocalDate.now()) > 0;
	}

	public void setValidThru(LocalDate validThru) {
		ArgumentChecks.isNotNull(validThru);
		this.validThru = validThru;

	}

	@Override
	public boolean canPay(double amount) {

		return isValidNow();
	}

}
