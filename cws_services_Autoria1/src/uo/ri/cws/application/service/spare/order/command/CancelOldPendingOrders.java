package uo.ri.cws.application.service.spare.order.command;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.OrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Order;
import uo.ri.cws.domain.Order.OrderStatus;

public class CancelOldPendingOrders implements Command<List<OrderDto>> {
	
	OrderRepository repo  = Factory.repository.forOrder();
	@Override
	public List<OrderDto> execute() throws BusinessException {
		LocalDate dateYearAgo = LocalDate.now().minusYears(1);
		List <Order> list = repo.findAll().stream().filter(o -> o.getStatus().equals(OrderStatus.PENDING) && o.getOrderedDate().isBefore(dateYearAgo)).collect(Collectors.toList());
		list.forEach(Order::cancel);
		return DtoAssembler.toOrdersDtoList(list);
	}

}
