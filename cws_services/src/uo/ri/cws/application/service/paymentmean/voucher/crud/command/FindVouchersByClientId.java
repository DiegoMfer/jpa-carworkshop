package uo.ri.cws.application.service.paymentmean.voucher.crud.command;

import java.util.List;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.VoucherDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindVouchersByClientId implements Command<List<VoucherDto>> {

	PaymentMeanRepository pmRepo = Factory.repository.forPaymentMean();

	private String id;

	public FindVouchersByClientId(String id) {
		ArgumentChecks.isNotNull(id);
		this.id = id;
	}

	@Override
	public List<VoucherDto> execute() throws BusinessException {
		return DtoAssembler.toVoucherDtoList(pmRepo.findVouchersByClientId(id));
	}

}
