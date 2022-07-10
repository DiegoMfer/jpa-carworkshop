package uo.ri.cws.application.service.paymentmean.voucher.crud.command;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.paymentmean.VoucherService.VoucherSummaryDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class GetVoucherSummary implements Command<List<VoucherSummaryDto>> {

	PaymentMeanRepository pmRepo = Factory.repository.forPaymentMean();
	ClientRepository cRepo = Factory.repository.forClient();

	@Override
	public List<VoucherSummaryDto> execute() throws BusinessException {

		List<VoucherSummaryDto> result = new ArrayList<>();

		for (var client : cRepo.findAll()) {

			var voucherList = pmRepo.findVouchersByClientId(client.getId());

			if (!voucherList.isEmpty()) {
				result.add(
						DtoAssembler.toVoucherSummaryDto(voucherList, client));
			}
		}

		return result;
	}

}
