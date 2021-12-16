import TestFramework.AfterSuite;
import TestFramework.BeforeSuite;
import TestFramework.Test;

public class TestCalculator {
    private static Calculator calculator;

    @Test()
    public static void testSum(){
        if(calculator.sum(1,1)==2){
            System.out.println("test ok");
        }else {
            System.out.println("test fail");
        }
    }

    @Test(priority = 1)
    public static void testDiv(){
        if(calculator.div(10,2)==4){
            System.out.println("test ok");
        }else {
            System.out.println("test fail");
        }
    }

    @BeforeSuite
    public static void preConfig(){
    calculator = new Calculator();
        System.out.println("beforeSuite is done");
    }

    @AfterSuite
    public static void end(){
        System.out.println("здесь могло бы быть закрытие ресурсов!");
    }
    /*
    @AfterSuite
    public static void wrongMethod(){
        System.out.println("ERROR!");
    }
     */
}
