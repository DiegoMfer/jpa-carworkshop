package uo.ri.cws.domain;

public class Associations {

	public static class Recommend {
		public static void link(Recommendation r, Client sponsor, Client rec) {

			r._setRecommended(rec);
			r._setSponsor(sponsor);
			rec._setRecommended(r);
			sponsor._getSponsored().add(r);

		}

		public static void unlink(Recommendation r) {

			r.getSponsor()._getSponsored().remove(r);
			r.getRecommended()._setRecommended(null);
			r._setSponsor(null);
			r._setRecommended(null);
		}
	}

	public static class Own {

		public static void link(Client client, Vehicle vehicle) {
			vehicle._setClient(client);
			client._getVehicles().add(vehicle);
		}

		public static void unlink(Client client, Vehicle vehicle) {
			client._getVehicles().remove(vehicle);
			vehicle._setClient(null);
		}

	}

	public static class Classify {

		public static void link(VehicleType vehicleType, Vehicle vehicle) {
			vehicle._setVehicleType(vehicleType);
			vehicleType._getVehicles().add(vehicle);
		}

		public static void unlink(VehicleType vehicleType, Vehicle vehicle) {
			vehicleType._getVehicles().remove(vehicle);
			vehicle._setVehicleType(null);
		}

	}

	public static class Pay {

		public static void link(PaymentMean pm, Client client) {
			link(client, pm);
		}

		public static void link(Client client, PaymentMean pm) {
			client._getPaymentMeans().add(pm);
			pm._setClient(client);
		}

		public static void unlink(Client client, PaymentMean pm) {
			pm._setClient(null);
			client._getPaymentMeans().remove(pm);

		}

	}

	public static class Fix {

		public static void link(Vehicle vehicle, WorkOrder workOrder) {
			workOrder._setVehicle(vehicle);
			vehicle._getWorkOrders().add(workOrder);
		}

		public static void unlink(Vehicle vehicle, WorkOrder workOrder) {
			vehicle._getWorkOrders().remove(workOrder);
			workOrder._setVehicle(null);
		}

	}

	public static class ToInvoice {

		public static void link(Invoice invoice, WorkOrder workOrder) {
			workOrder._setInvoice(invoice);
			invoice._getWorkOrders().add(workOrder);
		}

		public static void unlink(Invoice invoice, WorkOrder workOrder) {
			invoice._getWorkOrders().remove(workOrder);
			workOrder._setInvoice(null);
		}
	}

	public static class Charges {

		public static void link(PaymentMean pm, Charge charge,
				Invoice invoice) {

			charge._setInvoice(invoice);
			charge._setPaymentMean(pm);
			pm._getCharges().add(charge);
			invoice._getCharges().add(charge);
		}

		public static void unlink(Charge charge) {
			charge.getInvoice()._getCharges().remove(charge);
			charge.getPaymentMean()._getCharges().remove(charge);
			charge._setInvoice(null);
			charge._setPaymentMean(null);
		}

	}

	public static class Assign {

		public static void link(Mechanic mechanic, WorkOrder workOrder) {
			workOrder._setMechanic(mechanic);
			mechanic._getAssigned().add(workOrder);
		}

		public static void unlink(Mechanic mechanic, WorkOrder workOrder) {
			mechanic._getAssigned().remove(workOrder);
			workOrder._setMechanic(null);
		}

	}

	public static class Intervene {

		public static void link(WorkOrder workOrder, Intervention intervention,
				Mechanic mechanic) {

			intervention._setMechanic(mechanic);
			intervention._setWorkOrder(workOrder);

			workOrder._getInterventions().add(intervention);
			mechanic._getInterventions().add(intervention);
		}

		public static void unlink(Intervention intervention) {

			intervention.getWorkOrder()._getInterventions()
					.remove(intervention);
			intervention.getMechanic()._getInterventions().remove(intervention);

			intervention._setMechanic(null);
			intervention._setWorkOrder(null);

		}

	}

	public static class Sustitute {

		public static void link(SparePart spare, Substitution sustitution,
				Intervention intervention) {
			sustitution._setIntervention(intervention);
			sustitution._setSparePart(spare);

			spare._getSubstitutions().add(sustitution);
			intervention._getSubstitutions().add(sustitution);
		}

		public static void unlink(Substitution sustitution) {

			sustitution.getSparePart()._getSubstitutions().remove(sustitution);
			sustitution.getIntervention()._getSubstitutions()
					.remove(sustitution);

			sustitution._setIntervention(null);
			sustitution._setSparePart(null);

		}

	}

}
