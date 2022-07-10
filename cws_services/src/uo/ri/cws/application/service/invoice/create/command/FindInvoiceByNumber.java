package uo.ri.cws.application.service.invoice.create.command;

import java.util.Optional;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.util.command.Command;

public class FindInvoiceByNumber implements Command<Optional<InvoiceDto>> {

	public FindInvoiceByNumber(Long number) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Optional<InvoiceDto> execute() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
