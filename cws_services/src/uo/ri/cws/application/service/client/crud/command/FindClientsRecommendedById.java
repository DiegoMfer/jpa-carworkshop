package uo.ri.cws.application.service.client.crud.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.Recommendation;

public class FindClientsRecommendedById implements Command<List<ClientDto>> {

	private ClientRepository cRepo = Factory.repository.forClient();

	private String sponsorID;

	public FindClientsRecommendedById(String id) {
		ArgumentChecks.isNotEmpty(id, "Sponsor id can't be empty");
		this.sponsorID = id;
	}

	@Override
	public List<ClientDto> execute() throws BusinessException {
		Optional<Client> oc = cRepo.findById(sponsorID);

		List<ClientDto> clients = new ArrayList<>();

		if (oc.isPresent()) {
			for (Recommendation rc : oc.get().getSponsored()) {
				clients.add(DtoAssembler.toDto(rc.getRecommended()));
			}
		}

		return clients;
	}

}
