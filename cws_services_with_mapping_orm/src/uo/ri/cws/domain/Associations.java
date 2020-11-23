package uo.ri.cws.domain;

public class Associations {

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

		public static void link(PaymentMean paymentMean, Client client) {
			paymentMean._setClient(client);
			client._getPaymentMeans().add(paymentMean);
		}

		public static void unlink(Client client, PaymentMean paymentMean) {
			client._getPaymentMeans().remove(paymentMean);
			paymentMean._setClient(null);
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

		public static void link(WorkOrder workOrder, Invoice invoice) {
			workOrder._setInvoice(invoice);
			invoice._getWorkOrders().add(workOrder);
		}

		public static void unlink(WorkOrder workOrder, Invoice invoice) {
			invoice._getWorkOrders().remove(workOrder);
			workOrder._setInvoice(null);
		}
	}

	public static class Charges {

		public static void link(Invoice invoice, Charge charge, PaymentMean paymentMean) {
			charge._setInvoice(invoice);
			charge._setPaymentMean(paymentMean);

			invoice._getCharges().add(charge);
			paymentMean._getCharges().add(charge);
		}

		public static void unlink(Charge charge) {
			Invoice invoice = charge.getInvoice();
			PaymentMean paymentMean = charge.getPaymentMean();

			invoice._getCharges().remove(charge);
			paymentMean._getCharges().remove(charge);

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

		public static void link(Mechanic mechanic, Intervention intervention, WorkOrder workOrder) {
			intervention._setMechanic(mechanic);
			intervention._setWorkOrder(workOrder);

			workOrder._getInterventions().add(intervention);
			mechanic._getInterventions().add(intervention);
		}

		public static void unlink(Intervention intervention) {
			Mechanic mechanic = intervention.getMechanic();
			WorkOrder workOrder = intervention.getWorkOrder();

			workOrder._getInterventions().remove(intervention);
			mechanic._getInterventions().remove(intervention);

			intervention._setMechanic(null);
			intervention._setWorkOrder(null);
		}
	}

	public static class Sustitute {

		public static void link(SparePart sparePart, Substitution substitution, Intervention intervention) {
			substitution._setSparePart(sparePart);
			substitution._setIntervention(intervention);

			sparePart._getSubstitutions().add(substitution);
			intervention._getSubstitutions().add(substitution);
		}

		public static void unlink(Substitution substitution) {
			SparePart sparePart = substitution.getSparePart();
			Intervention intervention = substitution.getIntervention();

			sparePart._getSubstitutions().remove(substitution);
			intervention._getSubstitutions().remove(substitution);

			substitution._setSparePart(null);
			substitution._setIntervention(null);
		}
	}

	public static class Supplies {
		public static void link(SparePart sparePart, Supply supply, Provider provider) {
			supply._setSparePart(sparePart);
			supply._setProvider(provider);

			sparePart._getSupplies().add(supply);
			provider._getSupplies().add(supply);
		}

		public static void unlink(Supply supply) {
			SparePart sparePart = supply.getSparePart();
			Provider provider = supply.getProvider();

			sparePart._getSupplies().remove(supply);
			provider._getSupplies().remove(supply);

			supply._setSparePart(null);
			supply._setProvider(null);
		}
	}

	public static class Ordering {
		public static void link(SparePart sparePart, OrderLine orderline, Order order) {
			orderline._setSparepart(sparePart);
			order._getOrderLines().add(orderline);
		}

		public static void unlink(OrderLine orderline) {
			orderline._setSparepart(null);
			
		}
	}
	public static class Deliver {

		public static void link(Provider provider, Order order) {
			order.setProvider(provider);
			provider._getOrders().add(order);
		}

		public static void unlink(Provider provider, Order order) {
			provider._getOrders().remove(order);
			order.setProvider(null);
		}
	}
}
