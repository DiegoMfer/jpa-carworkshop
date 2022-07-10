package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class Mechanic extends BaseEntity {
	// natural attributes

	private String dni;
	private String surname;
	private String name;

	// accidental attributes

	private Set<WorkOrder> assigned = new HashSet<>();

	private Set<Intervention> interventions = new HashSet<>();

	Mechanic() {

	}

	public Mechanic(String dni, String name, String surname) {
		ArgumentChecks.isNotEmpty(dni);
		ArgumentChecks.isNotEmpty(name);
		ArgumentChecks.isNotEmpty(surname);
		this.name = name;
		this.surname = surname;
		this.dni = dni;
	}

	public Mechanic(String dni) {
		this(dni, "no-name", "no-surname");
	}

	public String getDni() {
		return dni;
	}

	public String getSurname() {
		return surname;
	}

	public String getName() {
		return name;
	}

	public Set<WorkOrder> getAssigned() {
		return new HashSet<>(assigned);
	}

	Set<WorkOrder> _getAssigned() {
		return assigned;
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>(interventions);
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
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
		Mechanic other = (Mechanic) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Mechanic [dni=" + dni + ", surname=" + surname + ", name="
				+ name + "]";
	}

	public void setName(String name) {
		ArgumentChecks.isNotEmpty(name);
		this.name = name;

	}

	public void setSurname(String surname) {
		this.surname = surname;

	}

}
