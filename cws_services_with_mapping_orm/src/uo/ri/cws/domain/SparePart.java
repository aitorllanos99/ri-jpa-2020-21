package uo.ri.cws.domain;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;


public class SparePart extends BaseEntity {
	// natural attributes
	
	private String code;
	private String description;
	private double price;
	private int stock;
	private int maxStock;
	private int minStock;
	// accidental attributes

	private Set<Substitution> substitutions = new HashSet<>();

	
	private Set<Supply> supplies = new HashSet<>();

	
	private Set<OrderLine> orderlines = new HashSet<>();

	SparePart() {
	}

	public SparePart(String code) {
		ArgumentChecks.isNotEmpty(code);
		this.code = code;
	}

	public SparePart(String code, String description, double price) {
		this(code);
		ArgumentChecks.isNotEmpty(description);
		ArgumentChecks.isTrue(price >= 0);
		this.description = description;
		this.price = price;
	}

	public SparePart(String code, String description, double price, int stock, int minStock, int maxStock) {
		this(code, description, price);
		ArgumentChecks.isTrue(stock >= 0);
		ArgumentChecks.isTrue(maxStock >= 0);
		ArgumentChecks.isTrue(minStock >= 0);
		ArgumentChecks.isTrue(maxStock >= minStock);
		this.stock = stock;
		this.maxStock = maxStock;
		this.minStock = minStock;
	}

	Set<Substitution> _getSubstitutions() {
		return substitutions;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public Set<Substitution> getSubstitutions() {
		return new HashSet<>(substitutions);
	}

	public Set<Substitution> getSustitutions() {
		return new HashSet<>(substitutions);
	}

	Set<Supply> _getSupplies() {
		return supplies;
	}

	public Set<Supply> getSupplies() {
		return new HashSet<>(supplies);
	}

	Set<OrderLine> _getOrderLines() {
		return orderlines;
	}

	public Set<OrderLine> getOrderLines() {
		return new HashSet<>(orderlines);
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public void setMaxStock(int maxStock) {
		this.maxStock = maxStock;
	}

	public void setMinStock(int minStock) {
		this.minStock = minStock;
	}
	

	public int getStock() {
		return stock;
	}

	public int getMaxStock() {
		return maxStock;
	}

	public int getMinStock() {
		return minStock;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		SparePart other = (SparePart) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SparePart [code=" + code + ", description=" + description + ", price=" + price + "]";
	}

	public int getQuantityToOrder() {
		if(stock > maxStock || stock == minStock)
			return 0;
		return maxStock - stock;
	}

	public int getTotalUnitsSold() {
		return substitutions.stream().mapToInt(c -> c.getQuantity()).sum();
	}

	public void updatePriceAndStock(double purchasePrice, int newQuantity) {
		ArgumentChecks.isTrue(newQuantity > 0);
		ArgumentChecks.isTrue(purchasePrice >= 0);
		price = (stock * price + newQuantity * purchasePrice * 1.2) / (stock + newQuantity);
		stock += newQuantity;
		
	}

	public Optional<Supply> getBestSupply() {
		double lowerPrice = supplies.stream().min(Comparator.comparingDouble(dto -> dto.getPrice())).get().getPrice();
		List<Supply> s = supplies.stream().filter(d -> d.getPrice() == lowerPrice).collect(Collectors.toList());
		if(s.size() == 1)
			Optional.ofNullable(s.get(0));
		double lowerDeliveryTerm = s.stream().min(Comparator.comparingDouble(dto -> dto.getDeliveryTerm()))
				.get().getDeliveryTerm();
		
		
		// We get the ones with that delivery term
		List<Supply> lowerSupplyDeliveryTerm = s.stream().filter(d -> d.getDeliveryTerm() == lowerDeliveryTerm)
				.collect(Collectors.toList());
		if (lowerSupplyDeliveryTerm.size() == 1)
			return Optional.ofNullable(lowerSupplyDeliveryTerm.get(0));
		
		//Comparation for nifs
		return lowerSupplyDeliveryTerm.stream().min(Comparator.comparing(dto -> dto.getProvider().getNif()));

	}

}
