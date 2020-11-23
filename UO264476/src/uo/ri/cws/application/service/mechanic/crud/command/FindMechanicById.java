package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindMechanicById implements Command<Optional<MechanicDto>>{

	private String id;
	private MechanicRepository repo = Factory.repository.forMechanic();
	public FindMechanicById(String id) {
		this.id = id;
	}

	public Optional<MechanicDto> execute() throws BusinessException {
		return repo.findById(id).map(DtoAssembler::toDto);
	}

}
