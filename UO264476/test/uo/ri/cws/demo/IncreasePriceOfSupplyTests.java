package uo.ri.cws.demo;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.BusinessFactory;
import uo.ri.cws.application.service.spare.SuppliesCrudService;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.infrastructure.persistence.jpa.executor.JpaExecutorFactory;
import uo.ri.cws.infrastructure.persistence.jpa.repository.JpaRepositoryFactory;

public class IncreasePriceOfSupplyTests {

	private static final String SPARE_CODE = "1010";
	private static final double PERCENTAGE = 2.0;

	@Before
	public void setUp() throws Exception {
		Factory.service = new BusinessFactory();
		Factory.repository = new JpaRepositoryFactory();
		Factory.executor = new JpaExecutorFactory();
	}

	/**
	 * Increments the price for all supplies of a spare part
	 */
	@Test
	public void testBasic() throws BusinessException {
		SuppliesCrudService s = Factory.service.forSuppliesCrudService();
		List<SupplyDto> before = s.findBySparePartCode( SPARE_CODE );

		s.incrementPriceForSpareByPercent( SPARE_CODE, PERCENTAGE );

		List<SupplyDto> after = s.findBySparePartCode( SPARE_CODE );

		assertEquals( before.size(), after.size() );

		for(int i = 0; i < after.size(); i++) {
			SupplyDto  bfr = before.get( i );
			SupplyDto  aft = after.get( i );

			double expected = bfr.price + (bfr.price * PERCENTAGE / 100.0);

			assertEquals(expected, aft.price, 0.001);
		}

	}

	/**
	 * If the spare code does not exist it throws a BusinessException
	 */
	@Test(expected = BusinessException.class)
	public void testDoesNotExist() throws BusinessException {
		SuppliesCrudService s = Factory.service.forSuppliesCrudService();

		s.incrementPriceForSpareByPercent( "does-not-exist", PERCENTAGE );
	}

}
