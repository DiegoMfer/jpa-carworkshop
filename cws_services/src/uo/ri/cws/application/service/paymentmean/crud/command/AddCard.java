package uo.ri.cws.application.service.paymentmean.crud.command;

import java.time.LocalDate;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.CardDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.CreditCard;

public class AddCard implements Command<CardDto> {

	private PaymentMeanRepository pmRepo = Factory.repository.forPaymentMean();
	private ClientRepository cRepo = Factory.repository.forClient();

	private CardDto card;

	public AddCard(CardDto card) {
		ArgumentChecks.isNotNull(card);
		ArgumentChecks.isNotEmpty(card.cardNumber);
		ArgumentChecks.isNotNull(card.cardExpiration);
		ArgumentChecks.isNotEmpty(card.cardType);
		ArgumentChecks.isNotEmpty(card.clientId);
		this.card = card;
	}

	@Override
	public CardDto execute() throws BusinessException {

		var oClient = cRepo.findById(card.clientId);

		BusinessChecks.exists(oClient, "The client does not exist");
		checkIsNotExpired(card.cardExpiration);
		checkIsNotRepeated(card.cardNumber);
		var client = oClient.get();

		CreditCard creditCard = new CreditCard(card.cardNumber, card.cardType,
				card.cardExpiration, client);

		pmRepo.add(creditCard);

		return card;
	}

	private void checkIsNotExpired(LocalDate cardExpiration)
			throws BusinessException {
		BusinessChecks.isFalse(cardExpiration.isBefore(LocalDate.now()));
	}

	private void checkIsNotRepeated(String cardNumber)
			throws BusinessException {
		var oc = pmRepo.findCreditCardByNumber(cardNumber);
		BusinessChecks.isTrue(oc.isEmpty(), "Credit card id is repeated");
	}

}
