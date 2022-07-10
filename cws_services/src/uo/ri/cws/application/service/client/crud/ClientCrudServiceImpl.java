package uo.ri.cws.application.service.client.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService;
import uo.ri.cws.application.service.client.crud.command.AddClient;
import uo.ri.cws.application.service.client.crud.command.DeleteClient;
import uo.ri.cws.application.service.client.crud.command.FindAllClients;
import uo.ri.cws.application.service.client.crud.command.FindClientById;
import uo.ri.cws.application.service.client.crud.command.FindClientsRecommendedById;
import uo.ri.cws.application.service.client.crud.command.UpdateClient;
import uo.ri.cws.application.util.command.CommandExecutor;

public class ClientCrudServiceImpl implements ClientCrudService {

	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public List<ClientDto> findClientsRecommendedBy(String id)
			throws BusinessException {
		return executor.execute(new FindClientsRecommendedById(id));
	}

	@Override
	public ClientDto addClient(ClientDto client, String sponsorId)
			throws BusinessException {
		return executor.execute(new AddClient(client, sponsorId));
	}

	@Override
	public void deleteClient(String id) throws BusinessException {
		executor.execute(new DeleteClient(id));

	}

	@Override
	public Optional<ClientDto> findClientById(String id)
			throws BusinessException {
		return executor.execute(new FindClientById(id));
	}

	@Override
	public void updateClient(ClientDto updatedClient) throws BusinessException {
		executor.execute(new UpdateClient(updatedClient));

	}

	@Override
	public List<ClientDto> findAllClients() throws BusinessException {
		return executor.execute(new FindAllClients());
	}

}
