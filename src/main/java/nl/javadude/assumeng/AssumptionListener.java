package nl.javadude.assumeng;

import org.testng.IInvokedMethod;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.lang.String.format;

public class AssumptionListener extends BaseTestListener {
	@Override
	public void beforeInvocation(IInvokedMethod invokedMethod, ITestResult result) {
		ITestNGMethod testNgMethod = result.getMethod();
		Method method = testNgMethod.getConstructorOrMethod().getMethod();
		if (method == null || !method.isAnnotationPresent(Assumption.class)) {
			return;
		}

		if (!checkAssumptions(method, result)) {
			result.setStatus(ITestResult.SKIP);
		}
	}

	private boolean checkAssumptions(Method method, ITestResult result) {
		Assumption annotation = method.getAnnotation(Assumption.class);
		String[] assumptionMethods = annotation.methods();
		Class clazz = result.getMethod().getTestClass().getRealClass();
		boolean assumptionsHold = true;
		for (String assumptionMethod : assumptionMethods) {
			assumptionsHold &= checkAssumption(result, clazz, assumptionMethod);
		}
		return assumptionsHold;
	}

	private boolean checkAssumption(ITestResult result, Class clazz, String assumptionMethod) {
		try {
			Method assumption = clazz.getMethod(assumptionMethod);
			if (assumption.getReturnType() != boolean.class) {
				throw new RuntimeException(format("Assumption method [%s] should return a boolean", assumptionMethod));
			}
			return (Boolean) assumption.invoke(result.getInstance());
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(format("Could not find method [%s] to run assumption", assumptionMethod), e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(format("Could not invoke method [%s] to run assumption", assumptionMethod), e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(format("Could not access method [%s] to run assumption", assumptionMethod), e);
		}
	}

}
