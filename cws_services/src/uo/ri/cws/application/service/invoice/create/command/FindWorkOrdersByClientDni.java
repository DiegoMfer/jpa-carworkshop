package uo.ri.cws.application.service.invoice.create.command;

import java.util.ArrayList;
import java.util.List;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindWorkOrdersByClientDni
		implements Command<List<InvoicingWorkOrderDto>> {

	private ClientRepository cRepo = Factory.repository.forClient();

	private String dni;

	public FindWorkOrdersByClientDni(String dni) {
		ArgumentChecks.isNotEmpty(dni, "Dni field empty");
		this.dni = dni;
	}

	@Override
	public List<InvoicingWorkOrderDto> execute() throws BusinessException {
		
		List<InvoicingWorkOrderDto> result = new ArrayList<>();
		
		var client = cRepo.findByDni(dni).get();

		for (var vehicle : client.getVehicles()) {
			for (var workOrder : vehicle.getWorkOrders()) {
				result.add(DtoAssembler.toInvoicingWorkOrderDto(workOrder));
			}
		}
		
		return result;
	}

}
