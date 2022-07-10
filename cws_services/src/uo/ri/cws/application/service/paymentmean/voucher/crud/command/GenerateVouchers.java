package uo.ri.cws.application.service.paymentmean.voucher.crud.command;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.Voucher;
import uo.ri.cws.domain.WorkOrder;

public class GenerateVouchers implements Command<Integer> {

	private static final int VOUCHER20_WORKORDERS = 3;

	private PaymentMeanRepository pmRepo = Factory.repository.forPaymentMean();
	private ClientRepository cRepo = Factory.repository.forClient();
	private InvoiceRepository iRepo = Factory.repository.forInvoice();

	@Override
	public Integer execute() throws BusinessException {
		int result = 0;

		for (var client : cRepo.findAll()) {
			result += voucher25(client);
			result += voucher20(client);
			result += voucher30(client);

		}

		return result;
	}

	private int voucher25(Client client) {
		int result = 0;
		while (client.eligibleForRecommendationVoucher()) {
			Voucher voucher = new Voucher(UUID.randomUUID().toString(),
					"By recommendation", 25, client);
			client.markThreeRecomendationsAsUsed();
			pmRepo.add(voucher);
			result++;
		}

		return result;

	}

	private int voucher20(Client client) {

		List<WorkOrder> validWorkOrders = client
				.getWorkOrdersAvailableForVoucher();

		int result = validWorkOrders.size() / VOUCHER20_WORKORDERS;

		for (int i = 0; i < result * VOUCHER20_WORKORDERS; i++) {
			validWorkOrders.get(i).markAsUsedForVoucher();
		}

		for (int i = 0; i < result; i++) {
			pmRepo.add(new Voucher(UUID.randomUUID().toString(),
					"By three workorders", 20, client));
		}

		return result;
	}

	private int voucher30(Client client) {
		
		var invoices = iRepo.findUnusedWithBono500();

		Set<Invoice> clientInvoices = new HashSet<>();
		for (var i : invoices) {
			for (var j : i.getWorkOrders()) {
				if (j.getVehicle().getClient().equals(client)) {
					clientInvoices.add(i);
					break;
				}
			}
		}

		for (int i = 0; i < clientInvoices.size(); i++) {
			pmRepo.add(new Voucher(UUID.randomUUID().toString(),
					"By invoice over 500", 30, client));
		}

		clientInvoices.forEach(invoice -> invoice.markAsUsed());

		return clientInvoices.size();
	}

}
