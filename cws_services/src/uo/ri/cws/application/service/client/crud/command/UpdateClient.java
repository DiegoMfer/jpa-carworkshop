package uo.ri.cws.application.service.client.crud.command;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Address;
import uo.ri.cws.domain.Client;

public class UpdateClient implements Command<ClientDto> {

	private ClientRepository cRepo = Factory.repository.forClient();

	private ClientDto clientDto;

	public UpdateClient(ClientDto updatedClient) {
		ArgumentChecks.isNotNull(updatedClient);
		ArgumentChecks.isNotEmpty(updatedClient.id);
		ArgumentChecks.isNotEmpty(updatedClient.dni);
		this.clientDto = updatedClient;
	}

	@Override
	public ClientDto execute() throws BusinessException {

		Optional<Client> oc = cRepo.findById(clientDto.id);
		BusinessChecks.exists(oc, "Client doesn't exist");

		Client cl = oc.get();

		cl.setName(clientDto.name);
		cl.setSurname(clientDto.surname);
		cl.setEmail(clientDto.email);
		cl.setPhone(clientDto.phone);
		cl.setAddress(new Address(clientDto.addressStreet,
				clientDto.addressCity, clientDto.addressZipcode));

		return null;
	}

}
