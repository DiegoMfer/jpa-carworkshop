package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import uo.ri.cws.domain.base.BaseEntity;

public abstract class PaymentMean extends BaseEntity {

	// natural attributes
	private double accumulated = 0.0;

	// accidental attributes

	private Client client;

	private Set<Charge> charges = new HashSet<>();

	public double getAccumulated() {
		return accumulated;
	}

	public Client getClient() {
		return client;
	}

	public void pay(double importe) {
		this.accumulated += importe;
	}

	void _setClient(Client client) {
		this.client = client;
	}

	public Set<Charge> getCharges() {
		return new HashSet<>(charges);
	}

	Set<Charge> _getCharges() {
		return charges;
	}

	@Override
	public String toString() {
		return "PaymentMean [client=" + client + "]";
	}

	public abstract boolean canPay(double amount);

}
