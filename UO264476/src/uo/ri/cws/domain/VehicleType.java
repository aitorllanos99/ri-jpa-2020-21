package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;


public class VehicleType extends BaseEntity{
	// natural attributes
	
	private String name;
	private double pricePerHour;

	// accidental attributes
	private Set<Vehicle> vehicles = new HashSet<>();

	VehicleType() {
	}

	public VehicleType(String name) {
		ArgumentChecks.isNotEmpty(name);
		this.name = name;
	}

	public VehicleType(String name, double pricePerHour) {
		this(name);
		ArgumentChecks.isTrue(pricePerHour >= 0);
		this.pricePerHour = pricePerHour;
	}

	public Set<Vehicle> getVehicles() {
		return new HashSet<>(vehicles);
	}

	Set<Vehicle> _getVehicles() {
		return vehicles;
	}

	public String getName() {
		return name;
	}

	public double getPricePerHour() {
		return pricePerHour;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		VehicleType other = (VehicleType) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VehicleType [name=" + name + ", pricePerHour=" + pricePerHour + "]";
	}

}
