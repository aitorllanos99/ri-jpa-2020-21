package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class DeleteMechanic implements Command<Void>{

	private String mechanicId;
	private MechanicRepository repo = Factory.repository.forMechanic();
	public DeleteMechanic(String mechanicId) {
		this.mechanicId = mechanicId;
	}

	public Void execute() throws BusinessException {

		BusinessChecks.isNotNull(mechanicId, "The id cant be null");
		BusinessChecks.isNotEmpty(mechanicId, "The id cant be empty");
		
		Optional<Mechanic> m = repo.findById(mechanicId);
		BusinessChecks.isTrue(m.isPresent(), "Mechanic doesnt exist");
		BusinessChecks.isTrue(m.get().getInterventions().isEmpty(), "Mechanic has interventions");
		
		repo.remove(m.get());
		return null;
	}

}
