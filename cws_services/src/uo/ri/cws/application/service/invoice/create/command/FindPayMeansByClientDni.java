package uo.ri.cws.application.service.invoice.create.command;

import java.util.ArrayList;
import java.util.List;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingPaymentMeanDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindPayMeansByClientDni
		implements Command<List<InvoicingPaymentMeanDto>> {

	private ClientRepository cRepo = Factory.repository.forClient();
	private PaymentMeanRepository pmRepo = Factory.repository.forPaymentMean();

	private String dni;

	public FindPayMeansByClientDni(String dni) {
		ArgumentChecks.isNotEmpty(dni, "Dni field empty");
		this.dni = dni;
	}

	@Override
	public List<InvoicingPaymentMeanDto> execute() throws BusinessException {
		List<InvoicingPaymentMeanDto> result = new ArrayList<>();

		var oc = cRepo.findByDni(dni);

		if (oc.isPresent()) {
			result = DtoAssembler.toInvoicingPaymentMeanDtoList(
					pmRepo.findByClientId(oc.get().getId()));
		}

		return result;
	}

}
