package uo.ri.cws.application.service.invoice.create.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Client;

public class FindNotInvoicedWorkOrdersByClientDni
		implements Command<List<InvoicingWorkOrderDto>> {

	private ClientRepository cRepo = Factory.repository.forClient();
	private WorkOrderRepository wRepo = Factory.repository.forWorkOrder();

	private String dni;

	public FindNotInvoicedWorkOrdersByClientDni(String dni) {
		ArgumentChecks.isNotEmpty(dni, "Dni field empty.");
		this.dni = dni;
	}

	@Override
	public List<InvoicingWorkOrderDto> execute() throws BusinessException {
		checkClientExists();

		List<InvoicingWorkOrderDto> result = new ArrayList<>();
		result = DtoAssembler.toInvoicingWorkOrderDtoList(
				wRepo.findNotInvoicedWorkOrdersByClientDni(dni));
		return result;

	}

	private void checkClientExists() throws BusinessException {

		Optional<Client> oc = cRepo.findByDni(dni);
		BusinessChecks.exists(oc, "Client doesn't exist");
	}

}
