package uo.ri.cws.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class Client extends BaseEntity {

	private String dni;
	private String name;
	private String surname;
	private String email;
	private String phone;
	private Address address;

	private Set<Vehicle> vehicles = new HashSet<>();

	private Set<PaymentMean> paymentMeans = new HashSet<>();

	private Set<Recommendation> sponsored = new HashSet<>();

	private Recommendation recommended = null;

	Client() {

	}

	public Client(String dni, String name, String surname) {

		this(dni,name,surname, "No email", "No phone", "no street", "no city", "no zipCode");

	}

	public Client(String dni, String name, String surname, String email,
			String phone, String addressStreet, String addressCity, String addressZipcode) {
		ArgumentChecks.isNotEmpty(dni);
		ArgumentChecks.isNotEmpty(name);
		ArgumentChecks.isNotEmpty(surname);
		ArgumentChecks.isNotEmpty(email);
		ArgumentChecks.isNotEmpty(phone);
		ArgumentChecks.isNotEmpty(addressStreet);
		ArgumentChecks.isNotEmpty(addressCity);
		ArgumentChecks.isNotEmpty(addressZipcode);

		this.dni = dni;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.phone = phone;
		this.address = new Address(addressStreet, addressCity, addressZipcode);

	}

	public Client(String dni) {
		this(dni, "No name", "No surname");
	}

	public String getDni() {
		return dni;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public Address getAddress() {
		return address;
	}

	public Set<Vehicle> getVehicles() {
		return new HashSet<>(vehicles);
	}

	Set<Vehicle> _getVehicles() {
		return vehicles;
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
		Client other = (Client) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Client [dni=" + dni + ", name=" + name + ", surname=" + surname
				+ ", email=" + email + ", phone=" + phone + ", address="
				+ address + "]";
	}

	Set<PaymentMean> _getPaymentMeans() {
		return paymentMeans;

	}

	public Set<PaymentMean> getPaymentMeans() {
		return new HashSet<>(paymentMeans);

	}

	Set<Recommendation> _getSponsored() {
		return this.sponsored;
	}

	public Set<Recommendation> getSponsored() {
		return new HashSet<>(this.sponsored);
	}

	public Recommendation getRecommended() {
		return recommended;
	}

	void _setRecommended(Recommendation recommended) {
		this.recommended = recommended;
	}

	public boolean eligibleForRecommendationVoucher() {
		if (sponsored.size() < 3) {
			return false;
		}

		// Client has vehicle with paid workOrders
		if (!hasAllWorkOrdersPaid(this)) {
			return false;
		}

		int counter = 0;

		// Client has 3 recommendations with paid workOrders and those
		// recommendations are not used
		counter = getRecommendationsNotUsed().size();

		if (counter < 3) {
			return false;
		}

		return true;
	}

	private boolean hasAllWorkOrdersPaid(Client client) {

		for (var vehicle : client.getVehicles()) {
			for (var workOrder : vehicle.getWorkOrders()) {
				if (!workOrder.isPaid()) {
					return false;
				}
			}
		}
		return true;
	}

	public List<WorkOrder> getWorkOrdersAvailableForVoucher() {
		List<WorkOrder> result = new ArrayList<>();

		for (var vehicle : vehicles) {
			for (var workOrder : vehicle.getWorkOrders()) {
				if (workOrder.isInvoiced() && workOrder.getInvoice().isSettled()
						&& workOrder.canBeUsedForVoucher()) {
					result.add(workOrder);
				}
			}
		}

		return result;
	}

	public void setAddress(Address address) {
		ArgumentChecks.isNotNull(address);
		this.address = address;
	}

	private List<Recommendation> getRecommendationsNotUsed() {

		List<Recommendation> result = new ArrayList<>();

		for (var recommendation : sponsored) {
			if (hasAllWorkOrdersPaid(recommendation.getRecommended())
					&& !recommendation.isUsed()) {
				result.add(recommendation);
			}
		}

		return result;
	}

	public void markThreeRecomendationsAsUsed() {
		var list = getRecommendationsNotUsed();

		if (list.size() < 3) {
			return;
		}

		for (int i = 0; i < 3; i++) {
			list.get(i).markAsUsed();
		}

	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	
}
