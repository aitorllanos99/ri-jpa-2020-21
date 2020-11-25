package uo.ri.cws.application.service.spare.supply.crud;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.SupplyRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;

public class IncrementPriceForSpareByPercent implements Command<Void> {
	private String code;
	private double percentage;
	SupplyRepository repo = Factory.repository.forSupply();

	public IncrementPriceForSpareByPercent(String code, double percentage) {
		this.code = code;
		this.percentage = percentage;
	}

	@Override
	public Void execute() throws BusinessException {
		BusinessChecks.isTrue(!repo.findBySparePartCode(code).isEmpty() ,"The code doesnt belong to a supply");
		repo.findBySparePartCode(code).forEach(c -> c.increasePriceBy(percentage));
		return null;
	}

}
