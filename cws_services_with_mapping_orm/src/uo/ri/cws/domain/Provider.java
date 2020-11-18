package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class Provider extends BaseEntity {

	private String nif;
	private String name;
	private String email;
	private String phone;

	private Set<Supply> supplies = new HashSet<>();

	private Set<Order> orders = new HashSet<>();

	Provider() {
	}

	public Provider(String nif) {
		ArgumentChecks.isNotNull(nif);
		ArgumentChecks.isNotEmpty(nif);
		this.nif = nif;
	}

	public Provider(String nif, String name) {
		this(nif);
		ArgumentChecks.isNotNull(name);
		ArgumentChecks.isNotEmpty(name);
		this.name = name;
	}

	public Provider(String nif, String name, String phone, String email) {
		this(nif);
		ArgumentChecks.isNotNull(phone);
		ArgumentChecks.isNotEmpty(phone);
		ArgumentChecks.isNotNull(email);
		ArgumentChecks.isNotEmpty(email);
		this.phone = phone;
		this.email = email;
	}

	public String getNif() {
		return nif;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	Set<Supply> _getSupplies() {
		return supplies;
	}

	public Set<Supply> getSupplies() {
		return new HashSet<>(supplies);
	}

	Set<Order> _getOrders() {
		return orders;
	}

	public Set<Order> getOrders() {
		return new HashSet<>(orders);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((nif == null) ? 0 : nif.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Provider other = (Provider) obj;
		if (nif == null) {
			if (other.nif != null)
				return false;
		} else if (!nif.equals(other.nif))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Provider [nif=" + nif + ", name=" + name + ", email=" + email + ", phone=" + phone + "]";
	}

}
