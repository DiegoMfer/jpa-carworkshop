package uo.ri.cws.domain;

import alb.util.assertion.ArgumentChecks;

public class Cash extends PaymentMean {

	Cash() {
	}

	public Cash(Client client) {

		ArgumentChecks.isNotNull(client);
		Associations.Pay.link(client, this);
	}

	@Override
	public String toString() {
		return "Cash [getAccumulated()=" + getAccumulated() + ", getClient()="
				+ getClient() + "]";
	}

	@Override
	public boolean canPay(double amount) {
		return true;
	}

}
