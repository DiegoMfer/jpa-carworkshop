package uo.ri.cws.domain;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class Substitution extends BaseEntity {
	// natural attributes
	private int quantity;

	// accidental attributes
	private SparePart sparePart;

	private Intervention intervention;

	Substitution() {

	}

	public Substitution(SparePart sparePart, Intervention intervention,
			int quantity) {
		ArgumentChecks.isNotNull(sparePart);
		ArgumentChecks.isNotNull(intervention);
		ArgumentChecks.isTrue(quantity > 0);

		this.quantity = quantity;
		this.sparePart = sparePart;
		this.intervention = intervention;

		Associations.Sustitute.link(sparePart, this, intervention);
	}

	public int getQuantity() {
		return quantity;
	}

	public SparePart getSparePart() {
		return sparePart;
	}

	public Intervention getIntervention() {
		return intervention;
	}

	void _setSparePart(SparePart sparePart) {
		this.sparePart = sparePart;
	}

	void _setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

	public double getAmount() {
		return this.sparePart.getPrice() * quantity;
	}

}
