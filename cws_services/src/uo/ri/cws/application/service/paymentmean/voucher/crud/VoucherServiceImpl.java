package uo.ri.cws.application.service.paymentmean.voucher.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.VoucherDto;
import uo.ri.cws.application.service.paymentmean.VoucherService;
import uo.ri.cws.application.service.paymentmean.voucher.crud.command.FindVouchersByClientId;
import uo.ri.cws.application.service.paymentmean.voucher.crud.command.GenerateVouchers;
import uo.ri.cws.application.service.paymentmean.voucher.crud.command.GetVoucherSummary;
import uo.ri.cws.application.util.command.CommandExecutor;

public class VoucherServiceImpl implements VoucherService {

	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public int generateVouchers() throws BusinessException {
		return executor.execute(new GenerateVouchers());
	}

	@Override
	public List<VoucherDto> findVouchersByClientId(String id)
			throws BusinessException {
		return executor.execute(new FindVouchersByClientId(id));
	}

	@Override
	public List<VoucherSummaryDto> getVoucherSummary()
			throws BusinessException {
		return executor.execute(new GetVoucherSummary());
	}

	@Override
	public Optional<VoucherDto> findVouchersById(String id)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
