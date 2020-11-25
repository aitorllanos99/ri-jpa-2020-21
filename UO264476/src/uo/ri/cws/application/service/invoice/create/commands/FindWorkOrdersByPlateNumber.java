package uo.ri.cws.application.service.invoice.create.commands;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindWorkOrdersByPlateNumber implements Command<List<InvoicingWorkOrderDto>> {
	private String plate;
	WorkOrderRepository repo = Factory.repository.forWorkOrder();

	public FindWorkOrdersByPlateNumber(String plate) {
		this.plate = plate;
	}

	@Override
	public List<InvoicingWorkOrderDto> execute() throws BusinessException {
		return DtoAssembler.toInvoicingWorkOrderDtoList(repo.findByPlateNumber(plate));
	}

}
