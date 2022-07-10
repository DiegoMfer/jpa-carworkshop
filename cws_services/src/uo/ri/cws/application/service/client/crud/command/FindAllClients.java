package uo.ri.cws.application.service.client.crud.command;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindAllClients implements Command<List<ClientDto>>{

	private ClientRepository cRepo = Factory.repository.forClient();
	
	@Override
	public List<ClientDto> execute() throws BusinessException {
		return DtoAssembler.toClientDtoList(cRepo.findAll());
	}

}
