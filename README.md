Assume NG -- Assumptions for TestNG
===================================

Using Assume NG it becomes possible to add [JUnit](http://www.junit.org)-like [assumptions](http://kentbeck.github.com/junit/javadoc/latest/org/junit/Assume.html) to [TestNG](http://www.testng.org).

Note
----
Please ensure that Assume NG is on the classpath *before* TestNG. This is because we need to classpath override the TestNG <code>org.testng.internal.Invoker</code> class to hook in our behavior.

Usage
-----
Assume NG works using the @Assumption annotation and the AssumptionListener.

The <code>@Assumption</code> annotation is defined on a <code>@Test</code> method, and calls out to an assumption method which decides whether or not the <code>@Test</code> annotated method needs to run in the current context.

The <code>AssumptionListener</code> scans for <code>@Assumption</code> annotations and handles the actual invocation of the assumption method.

The assumption method should have the following signature

    public boolean checkWhetherXHolds() { ... }

A full example could be:

	@Listeners(AssumptionListener.class)
    public class WeatherTest {
        @Test
        @Assumption(method = "assumeWeatherIsNice")
        public void shouldNotRunWhenItsRaining() {
            // Do some stuff, like going to the beach...
        }

        public boolean assumeWeatherIsNice() {
        	// check the weather bulletin...
        }
    }
