package uo.ri.cws.application.service.invoice.create.commands;

import java.util.HashMap;
import java.util.Map;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;

public class SettleInvoice implements Command<Void> {
	private Map<String, Double> charges = new HashMap<String, Double>();
	private String invoiceId;

	public SettleInvoice(String invoiceId, Map<String, Double> charges) {
		this.charges= charges;
		this.invoiceId = invoiceId;
	}

	@Override
	public Void execute() throws BusinessException {

		return null;
	}

}
