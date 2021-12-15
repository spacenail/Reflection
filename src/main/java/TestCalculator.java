import TestFramework.AfterSuite;
import TestFramework.BeforeSuite;
import TestFramework.Test;

public class TestCalculator {
    private static Calculator calculator;

    @Test()
    public void testSum(){

    }

    @Test(priority = 1)
    public void testDiv(){

    }

    @BeforeSuite
    public static void preConfig(){
    calculator = new Calculator();
    }

    @AfterSuite
    public static void end(){
        System.out.println("здесь могло бы быть закрытие ресурсов!");
    }

    @AfterSuite
    public static void wrongMethod(){
        System.out.println("ERROR!");
    }
}
