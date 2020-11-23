package uo.ri.cws.ext.domain.order;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.HashSet;

import org.junit.Test;

import uo.ri.cws.domain.Order;
import uo.ri.cws.domain.Provider;

public class ConstructorTest {
	LocalDate now = LocalDate.now();

	/**
	 * Cant create an order with null code
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCantCreateWithNullCode() {
		new Order(null);
	}

	/**
	 * Cant create and order with empty code
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCantCreateWithEmptyCode() {
		new Order("");
	}

	/**
	 * Cant create an order with negative amount
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCantCreateWithNegativeAmount() {
		new Order("11111", -1, now, LocalDate.now());
	}

	/**
	 * Cant create an order with null provider
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCantCreateWithNullProvider() {
		new Order("11111", -1, now, now, null);
	}

	@Test
	public void testAllRight() {
		Provider p = new Provider("1234567", "Prueba", "987654321", "buen@email");
		Order o = new Order("11111", 1, now, now, p);

		assertEquals("11111", o.getCode());
		assertEquals(1, o.getAmount(),0.1);
		assertEquals(now, o.getOrderedDate());
		assertEquals(now, o.getReceptionDate());
		assertEquals(p, o.getProvider());

		assertEquals(new HashSet<>(), o.getOrderLines());
	}
}
