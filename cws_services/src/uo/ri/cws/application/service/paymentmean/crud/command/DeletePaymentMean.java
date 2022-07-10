package uo.ri.cws.application.service.paymentmean.crud.command;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Cash;
import uo.ri.cws.domain.PaymentMean;

public class DeletePaymentMean implements Command<Void> {

	PaymentMeanRepository pmRepo = Factory.repository.forPaymentMean();

	private String id;

	public DeletePaymentMean(String id) {
		ArgumentChecks.isNotEmpty(id);
		this.id = id;
	}

	@Override
	public Void execute() throws BusinessException {

		var opm = pmRepo.findById(id);

		BusinessChecks.exists(opm, "The payment mean does not exist");

		var pm = opm.get();

		checkCanBeDeleted(pm);

		pmRepo.remove(pm);

		return null;
	}

	private void checkCanBeDeleted(PaymentMean paymentMean)
			throws BusinessException {
		BusinessChecks.isTrue(paymentMean.getCharges().isEmpty(),
				"The paymentmean has charges linked to it");
		BusinessChecks.isFalse(paymentMean instanceof Cash);
	}

}
