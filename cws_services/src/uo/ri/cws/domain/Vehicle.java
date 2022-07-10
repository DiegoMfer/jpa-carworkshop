package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class Vehicle extends BaseEntity {

	private String plateNumber;
	private String make;
	private String model;

	private Client client;

	private VehicleType vehicleType;

	private Set<WorkOrder> workOrders = new HashSet<>();

	Vehicle() {

	}

	public Vehicle(String plate, String make, String model) {
		ArgumentChecks.isNotEmpty(plate);
		ArgumentChecks.isNotEmpty(make);
		ArgumentChecks.isNotEmpty(model);
		this.plateNumber = plate;
		this.make = make;
		this.model = model;
	}

	public Vehicle(String plate) {
		this(plate, "no-maker", "no-model");
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	public Client getClient() {
		return client;
	}

	void _setClient(Client client) {
		this.client = client;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((plateNumber == null) ? 0 : plateNumber.hashCode());
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
		Vehicle other = (Vehicle) obj;
		if (plateNumber == null) {
			if (other.plateNumber != null)
				return false;
		} else if (!plateNumber.equals(other.plateNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vehicle [plateNumber=" + plateNumber + ", make=" + make
				+ ", model=" + model + "]";
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	void _setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>(this.workOrders);
	}

	Set<WorkOrder> _getWorkOrders() {
		return this.workOrders;
	}

}
