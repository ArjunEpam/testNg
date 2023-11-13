package summ;

import com.epam.tat.module4.Calculator;
import org.testng.Assert;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class CalculatorTest implements IRetryAnalyzer {
    private Calculator calculator;
    private int retryCount = 0;
    private static final int MAX_RETRY_COUNT = 2;

    @BeforeClass
    public void setUp() {
        calculator = new Calculator();
    }

    @AfterClass
    public void tearDown() {
    }

    @Test(groups = "positive")
    public void testAddPositive() {
        Assert.assertEquals(calculator.sum(2, 3), 5);
    }

    @Test(groups = "positive")
    public void testSubtractPositive() {
        Assert.assertEquals(calculator.sub(5, 3), 2);
    }

    // Negative Tests
    @Test(groups = "negative")
    public void testAddNegative() {
        int sss = (int) calculator.sum(2,2);
        System.out.println(sss);
        Assert.assertNotEquals(calculator.sum(2, 3), 10);
    }

    @Test(groups = "negative")
    public void testSubtractNegative() {
        Assert.assertNotEquals(calculator.sub(5, 3), 10);
    }


    @Test(dataProvider = "testData", groups = "positive",retryAnalyzer = CalculatorTest.class)
    public void testAddParameterized(int a, int b, int expectedResult) {
        Assert.assertEquals(calculator.sum(a, b), expectedResult);
    }

    @DataProvider(name = "testData")
    public Object[][] testData() {
        return new Object[][]{
                {1, 2, 3},
                {0, 0, 0},
                {-1, 1, 0}
        };
    }

    @Test(expectedExceptions = ArithmeticException.class)
    public void testDivideByZero() {
        int result = 10 / 0;
    }

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY_COUNT) {
            retryCount++;
            return true;
        }
        return false;
    }
}
