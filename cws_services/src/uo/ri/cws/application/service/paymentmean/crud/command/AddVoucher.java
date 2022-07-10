package uo.ri.cws.application.service.paymentmean.crud.command;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.VoucherDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Voucher;

public class AddVoucher implements Command<VoucherDto> {

	private PaymentMeanRepository pmRepo = Factory.repository.forPaymentMean();
	private ClientRepository cRepo = Factory.repository.forClient();

	private VoucherDto voucher;

	public AddVoucher(VoucherDto voucher) {
		ArgumentChecks.isNotNull(voucher);
		ArgumentChecks.isNotEmpty(voucher.clientId);
		ArgumentChecks.isNotEmpty(voucher.code);
		ArgumentChecks.isNotEmpty(voucher.description);
		ArgumentChecks.isTrue(voucher.balance >= 0.0);

		this.voucher = voucher;
	}

	@Override
	public VoucherDto execute() throws BusinessException {

		var oClient = cRepo.findById(voucher.clientId);

		BusinessChecks.exists(oClient, "The client does not exist");
		checkIsNotRepeated(voucher.code);

		Voucher newVoucher = new Voucher(voucher.code, voucher.description,
				voucher.balance, oClient.get());

		voucher.id = newVoucher.getId();
		pmRepo.add(newVoucher);
		return voucher;
	}

	private void checkIsNotRepeated(String voucherCode)
			throws BusinessException {
		var oc = pmRepo.findVoucherByCode(voucherCode);
		BusinessChecks.isTrue(oc.isEmpty(), "Voucher id is repeated");
	}

}
