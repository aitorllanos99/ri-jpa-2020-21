package uo.ri.cws.application.service.invoice.create;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.create.commands.FindWorkOrdersByClientDni;
import uo.ri.cws.application.service.invoice.create.commands.FindWorkOrdersByPlateNumber;
import uo.ri.cws.application.service.invoice.create.commands.SettleInvoice;
import uo.ri.cws.application.util.command.CommandExecutor;

public class InvoicingServiceImpl implements InvoicingService {
	private CommandExecutor executor = Factory.executor.forExecutor();
	@Override
	public InvoiceDto createInvoiceFor(List<String> workOrderIds) throws BusinessException {
		return null;
	}

	@Override
	public List<InvoicingWorkOrderDto> findWorkOrdersByClientDni(String dni) throws BusinessException {
		return executor.execute(new FindWorkOrdersByClientDni(dni));
	}

	@Override
	public List<InvoicingWorkOrderDto> findWorkOrdersByPlateNumber(String plate) throws BusinessException {
		return executor.execute(new FindWorkOrdersByPlateNumber(plate));
	}

	@Override
	public Optional<InvoiceDto> findInvoiceByNumber(Long number) throws BusinessException {
		return null;
	}

	@Override
	public List<PaymentMeanDto> findPayMeansByClientDni(String dni) throws BusinessException {
		return null;
	}

	@Override
	public void settleInvoice(String invoiceId, Map<String, Double> charges) throws BusinessException {
		executor.execute(new SettleInvoice(invoiceId, charges));

	}

}
