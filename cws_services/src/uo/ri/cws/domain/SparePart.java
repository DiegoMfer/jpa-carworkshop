package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class SparePart extends BaseEntity {
	// natural attributes

	private String code;
	private String description;
	private double price;

	// accidental attributes

	private Set<Substitution> substitutions = new HashSet<>();

	SparePart() {

	}

	public SparePart(String code, String description, double price) {
		ArgumentChecks.isNotEmpty(code);
		ArgumentChecks.isNotEmpty(description);
		ArgumentChecks.isTrue(price >= 0);
		this.description = description;
		this.price = price;
		this.code = code;
	}

	public SparePart(String code) {
		this(code, "No description", 1);
	}

	public Set<Substitution> getSustitutions() {
		return new HashSet<>(substitutions);
	}

	Set<Substitution> _getSubstitutions() {
		return substitutions;
	}

	public double getPrice() {
		return price;
	}

	public String getCode() {
		return this.code;
	}

	public String getDescription() {
		return this.description;
	}

}
