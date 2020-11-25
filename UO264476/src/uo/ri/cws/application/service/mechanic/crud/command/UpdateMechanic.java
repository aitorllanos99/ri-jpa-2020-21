package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class UpdateMechanic implements Command<Void> {

	private MechanicDto dto;
	private MechanicRepository repo = Factory.repository.forMechanic();

	public UpdateMechanic(MechanicDto dto) {
		this.dto = dto;
	}

	public Void execute() throws BusinessException {

		BusinessChecks.isNotNull(dto.id, "The id cant be null");
		BusinessChecks.isNotEmpty(dto.id, "The id cant be empty");
		BusinessChecks.isNotNull(dto.dni, "The dni cant be null");
		BusinessChecks.isNotEmpty(dto.dni, "The dni cant be empty");
		BusinessChecks.isNotNull(dto.name, "The name cant be null");
		BusinessChecks.isNotEmpty(dto.name, "The name cant be empty");
		BusinessChecks.isNotNull(dto.surname, "The surname cant be null");
		BusinessChecks.isNotEmpty(dto.surname, "The surname cant be empty");

		Optional<Mechanic> m = repo.findById(dto.id);
		BusinessChecks.isTrue(m.isPresent(), "Mechanic doesnt exist");
		BusinessChecks.hasVersion(m.get(), dto.version); // Para lecturas sucias
		m.get().setName(dto.name);
		m.get().setSurname(dto.surname);

		return null;
	}

}
