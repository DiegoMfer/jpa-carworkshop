package uo.ri.cws.domain;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CashTest.class, ChargeTests.class, CreditCardTest.class,
		ElegibleWorkOrderTest.class, InterventionTest.class, InvoiceTests.class,
		SustitutionTests.class, VoucherPer3RecommendationsTest.class,
		VoucherTest.class, WorkOrderTests.class })
public class AllDomainTests {

}
