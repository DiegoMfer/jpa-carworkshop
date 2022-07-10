package uo.ri.cws.application.service.client.crud.command;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.repository.RecommendationRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Cash;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.Recommendation;

public class AddClient implements Command<ClientDto> {

	private ClientRepository cRepo = Factory.repository.forClient();
	private RecommendationRepository rRepo = Factory.repository
			.forRecomendacion();
	private PaymentMeanRepository pmRepo = Factory.repository.forPaymentMean();

	private ClientDto client;
	private String sponsorId;

	public AddClient(ClientDto client, String sponsorId)
			throws BusinessException {
		ArgumentChecks.isNotNull(client, "Client can't  be null.");
		ArgumentChecks.isNotEmpty(client.dni, "Dni can't be empty");

		this.client = client;
		this.sponsorId = sponsorId;
	}

	@Override
	public ClientDto execute() throws BusinessException {
		// The dni is not in the database
		checkDniClient(client.dni);

		Client cl = new Client(client.dni, client.name, client.surname,
				client.email, client.phone, client.addressStreet,
				client.addressCity, client.addressZipcode);

		// We need the sponsor of the new client to link it with the
		// recommendation
		if (sponsorId != null) {
			var oc = cRepo.findById(sponsorId);
			BusinessChecks.exists(oc, "Sponsor does not exist");
			Client sponsor = oc.get();

			Recommendation rc = null;
			if (sponsor.eligibleForRecommendationVoucher()) {
				rc = new Recommendation(cl, oc.get());
				rRepo.add(rc);
			}
		}
		
		
		client.id = cl.getId();
		
		//We save the client and the paymentmean
		cRepo.add(cl);
		pmRepo.add(new Cash(cl));

		return client;
	}

	private void checkDniClient(String dni) throws BusinessException {

		Optional<Client> client = cRepo.findByDni(dni);
		BusinessChecks.isTrue(client.isEmpty(), "Client already registered");
	}

}
