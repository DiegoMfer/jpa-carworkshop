package uo.ri.cws.associations;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AssignTests.class, ClassifyTests.class, FixTests.class,
		InterventionTests.class, InvoiceTests.class, OwnTests.class,
		PayTests.class, RecommendTest.class, SustituteTests.class })
public class AllAssociationsTests {

}
