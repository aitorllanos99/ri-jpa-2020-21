package uo.ri.cws.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.BusinessFactory;
import uo.ri.cws.application.service.spare.OrdersService;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.infrastructure.persistence.jpa.executor.JpaExecutorFactory;
import uo.ri.cws.infrastructure.persistence.jpa.repository.JpaRepositoryFactory;

public class CancelOldPendingOrdersTests {

	private ChronoLocalDate oneYearAgo = LocalDate.now().minusYears( 1 );

	@Before
	public void setUp() throws Exception {
		Factory.service = new BusinessFactory();
		Factory.repository = new JpaRepositoryFactory();
		Factory.executor = new JpaExecutorFactory();
	}

	@Test
	public void test() throws BusinessException {
		OrdersService s = Factory.service.forOrdersService();
		List<OrderDto> res = s.cancelOldPendingOrders();
		
		assertEquals( 8, res.size() );
		
		for(OrderDto order: res) {
			assertEquals("CANCELLED", order.status);
			assertTrue( order.orderedDate.isBefore( oneYearAgo ) );
		}
		
	}

}
