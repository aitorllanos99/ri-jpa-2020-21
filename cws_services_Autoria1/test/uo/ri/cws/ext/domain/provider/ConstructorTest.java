package uo.ri.cws.ext.domain.provider;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.junit.Test;

import uo.ri.cws.domain.Provider;

public class ConstructorTest {
	

	/**
	 * Test that checks that the provider cant be created with a null nif
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testNifCantBeNull() {
		 new Provider(null);
	}
	
	
	/**
	 * Test that checks that the provider cant be created with an empty nif
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testNifCantBeEmpty() {
		 new Provider("");
	}
	
	/**
	 * Test that checks that the provider cant be created with a null name
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testNameCantBeNull() {
		new Provider("1232455757636" , null);
	}
	
	
	/**
	 * Test that checks that the provider cant be created with an empty name
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testNameCantBeEmpty() {
		 new Provider("1232455757636" , "");
	}
	
	/**
	 * Test that checks that the provider cant be created with a null phone
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testPhoneCantBeNull() {
		new Provider("1232455757636" , "Prueba", null, "Prueba@emails");
	}
	
	
	/**
	 * Test that checks that the provider cant be created with an empty phone
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testPhoneCantBeEmpty() {
		 new Provider("1232455757636" ,"Prueba", "", "Prueba@emails");
	}
	
	/**
	 * Test that checks that the provider cant be created with a null email
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testEmailCantBeNull() {
		new Provider("1232455757636" , "Prueba" , "111111", null);
	}
	
	
	/**
	 * Test that checks that the provider cant be created with an empty email
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testEmailCantBeEmpty() {
		 new Provider("1232455757636" , "Prueba" ,"11111", "");
	}
	
	/**
	 * Test that check that the provider is created well
	 */
	@Test
	public void testAllRight() {
		Provider p = new Provider("1234567", "Prueba" , "987654321", "buen@email");
		assertEquals("1234567" , p.getNif());
		assertEquals("Prueba" , p.getName());
		assertEquals("987654321", p.getPhone());
		assertEquals("buen@email", p.getEmail());
		
		//Estan vacios
		assertEquals(new HashSet<>(), p.getOrders());
		assertEquals(new HashSet<>(), p.getSupplies());
	}
}
