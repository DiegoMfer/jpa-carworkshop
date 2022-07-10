package uo.ri.cws.application.service.client.crud.command;

import java.util.List;
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
import uo.ri.cws.domain.Client;

public class DeleteClient implements Command<ClientDto> {

	private ClientRepository cRepo = Factory.repository.forClient();
	private RecommendationRepository rRepo = Factory.repository
			.forRecomendacion();
	private PaymentMeanRepository pmRepo = Factory.repository.forPaymentMean();

	private String clientId;

	public DeleteClient(String id) {
		ArgumentChecks.isNotEmpty(id, "Client id can't be empty.");
		this.clientId = id;
	}

	@Override
	public ClientDto execute() throws BusinessException {

		Optional<Client> oc = cRepo.findById(clientId);
		BusinessChecks.exists(oc, "Client does not exist.");

		Client cl = oc.get();

		BusinessChecks.isTrue(cl.getVehicles().isEmpty(),
				"Client has vehicles assigned");

		cl.getPaymentMeans().forEach(pm -> pmRepo.remove(pm));

		List<Client> clients = cRepo.findAll();

		for (var client : clients) {
			for (var rc : client.getSponsored()) {
				if (rc.getRecommended().equals(cl)) {
					rRepo.remove(rc);
				}
			}
		}

		cl.getSponsored().forEach(rc -> rRepo.remove(rc));
		
		cRepo.remove(cl);
		
		return null;
	}

}
