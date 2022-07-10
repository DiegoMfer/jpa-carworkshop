package uo.ri.cws.application.service.client;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.BusinessException;

/**
 * This service is intended to be used by the Cashier It follows the ISP
 * principle (@see SOLID principles from RC Martin)
 */
public interface ClientCrudService {

	// ...

	public static class ClientDto {

		public String id;
		public long version;

		public String dni;
		public String name;
		public String surname;
		public String addressStreet;
		public String addressCity;
		public String addressZipcode;
		public String phone;
		public String email;

	}

	public List<ClientDto> findClientsRecommendedBy(String id) throws BusinessException;

	public ClientDto addClient(ClientDto client, String recommendedId)
			throws BusinessException;

	public void deleteClient(String id) throws BusinessException;

	public Optional<ClientDto> findClientById(String id)
			throws BusinessException;

	public void updateClient(ClientDto updatedClient) throws BusinessException;

	public List<ClientDto> findAllClients() throws BusinessException;

}
