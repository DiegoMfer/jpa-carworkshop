package uo.ri.cws.application.service.paymentmean.crud.command;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.PaymentMeanDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindById implements Command<Optional<PaymentMeanDto>> {

	PaymentMeanRepository pmRepo = Factory.repository.forPaymentMean();

	private String id;

	public FindById(String id) {
		ArgumentChecks.isNotEmpty(id);
		this.id = id;
	}

	@Override
	public Optional<PaymentMeanDto> execute() throws BusinessException {
		var opm = pmRepo.findById(id);

		if (opm.isEmpty()) {
			return Optional.empty();
		}

		var pm = opm.get();
		return Optional.ofNullable(DtoAssembler.toDto(pm));
	}

}
