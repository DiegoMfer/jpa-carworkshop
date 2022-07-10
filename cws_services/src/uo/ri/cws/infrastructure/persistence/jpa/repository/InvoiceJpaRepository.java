package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class InvoiceJpaRepository extends BaseJpaRepository<Invoice>
		implements InvoiceRepository {

	@Override
	public Optional<Invoice> findByNumber(Long numero) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getNextInvoiceNumber() {
		var result = Jpa.getManager()
				.createNamedQuery("Invoice.getNextInvoiceNumber", Long.class)
				.getSingleResult();

		if (result == null) {
			return 1L;
		}

		return result;
	}

	@Override
	public List<Invoice> findUnusedWithBono500() {
		return Jpa.getManager().createNamedQuery(
				"Invoice.findUnusedWithBono500", Invoice.class).getResultList();
	}

}
