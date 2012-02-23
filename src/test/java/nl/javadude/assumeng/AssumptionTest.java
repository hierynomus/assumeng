package nl.javadude.assumeng;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.fail;

@Listeners(value = {AssumptionListener.class, AssumptionChecker.class})
public class AssumptionTest {

	@AfterClass
	public void assumptionsShouldBeCorrect() {
		assertThat(AssumptionChecker.getSkippedTests(), equalTo(2));
		assertThat(AssumptionChecker.getFailedTests(), equalTo(0));
		assertThat(AssumptionChecker.getSuccessTests(), equalTo(1));
	}

	@Test
	@Assumption(methods = "alwaysFalse")
	public void shouldSkip() {
		fail("Should not run");
	}

	@Test
	@Assumption(methods = "alwaysTrue")
	public void shouldSucceed() {
		assertThat(alwaysFalse(), equalTo(false));
	}

	@Test
	@Assumption(methods = {"alwaysTrue", "alwaysFalse"})
	public void shouldAlsoSkip() {

	}

	public boolean alwaysFalse() {
		return false;
	}

	public boolean alwaysTrue() {
		return true;
	}
}
