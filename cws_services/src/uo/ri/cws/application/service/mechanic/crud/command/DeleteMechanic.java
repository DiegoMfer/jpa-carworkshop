package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class DeleteMechanic implements Command<Void> {

	private MechanicRepository repo = Factory.repository.forMechanic();
	private String mechanicId;

	public DeleteMechanic(String mechanicId) {
		ArgumentChecks.isNotEmpty(mechanicId);
		this.mechanicId = mechanicId;
	}

	@Override
	public Void execute() throws BusinessException {

		Optional<Mechanic> om = repo.findById(mechanicId);
		BusinessChecks.exists(om, "The mechanic does not exist");
		Mechanic m = om.get();

		checkCanBeDeleted(m);

		repo.remove(m);

		return null;
	}

	private void checkCanBeDeleted(Mechanic m) throws BusinessException {
		BusinessChecks.isNotNull(m, "The mechanic does not exist");
		BusinessChecks.isTrue(m.getInterventions().isEmpty(),
				"The mechanic has interventions");
		BusinessChecks.isTrue(m.getAssigned().isEmpty(),
				"The mechanic has work orders");

	}

}
