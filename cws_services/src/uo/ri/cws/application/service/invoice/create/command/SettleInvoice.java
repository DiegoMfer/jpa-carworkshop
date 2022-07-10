package uo.ri.cws.application.service.invoice.create.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ChargeRepository;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Charge;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.PaymentMean;

public class SettleInvoice implements Command<Void> {

	private InvoiceRepository iRepo = Factory.repository.forInvoice();
	private PaymentMeanRepository pmRepo = Factory.repository.forPaymentMean();
	private ChargeRepository cRepo = Factory.repository.forCharge();

	private String invoiceId;
	private Map<String, Double> charges;

	public SettleInvoice(String invoiceId, Map<String, Double> charges) {
		ArgumentChecks.isNotEmpty(invoiceId, "The id field is empty");
		this.invoiceId = invoiceId;
		this.charges = charges;
	}

	@Override
	public Void execute() throws BusinessException {

		var invoice = getInvoice();
		var paymentMeans = getPaymentMeans();

		paymentMeans.forEach(pm -> cRepo
				.add(new Charge(invoice, pm, charges.get(pm.getId()))));

		try {
			invoice.settle();
		} catch (IllegalStateException e) {
			throw new BusinessException(e.getMessage());
		}

		return null;

	}

	private List<PaymentMean> getPaymentMeans() throws BusinessException {

		List<PaymentMean> result = new ArrayList<>();

		for (var pmId : charges.keySet()) {
			Optional<PaymentMean> opm = pmRepo.findById(pmId);
			BusinessChecks.exists(opm, "PaymentMean does not exist");
			BusinessChecks.isTrue(opm.get().canPay(charges.get(pmId)),
					"PaymentMean insufficient");
			result.add(opm.get());
		}

		return result;
	}

	/**
	 * @return The invoice from the invoice repository
	 * @throws BusinessException if the invoice does not exist
	 */
	private Invoice getInvoice() throws BusinessException {
		Optional<Invoice> oi = iRepo.findById(invoiceId);
		BusinessChecks.exists(oi, "Invoice doesn't exist");
		return oi.get();
	}

}
