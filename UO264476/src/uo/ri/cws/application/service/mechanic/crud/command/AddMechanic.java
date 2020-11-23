package uo.ri.cws.application.service.mechanic.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class AddMechanic implements Command<MechanicDto>{

	private MechanicDto dto;
	private MechanicRepository repo = Factory.repository.forMechanic();
	public AddMechanic(MechanicDto dto) {
		this.dto = dto;
	}

	public MechanicDto execute() throws BusinessException {
		
		BusinessChecks.isNotNull(dto.dni, "The dni cant be null");
		BusinessChecks.isNotEmpty(dto.dni, "The dni cant be empty");
		
		BusinessChecks.isTrue(repo.findByDni(dto.dni).isEmpty(), "Mechanic already exists");
		Mechanic m = new Mechanic(dto.dni, dto.name, dto.surname);
		dto.id = m.getId();
		repo.add(m);
		
		return dto;
	}

}
