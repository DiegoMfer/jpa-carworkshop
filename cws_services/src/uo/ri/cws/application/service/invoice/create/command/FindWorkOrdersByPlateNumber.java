package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.util.command.Command;

public class FindWorkOrdersByPlateNumber
		implements Command<List<InvoicingWorkOrderDto>> {

	public FindWorkOrdersByPlateNumber(String plate) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<InvoicingWorkOrderDto> execute() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
