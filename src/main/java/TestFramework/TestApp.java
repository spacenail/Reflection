package TestFramework;

/*
1. Создать класс, который может выполнять «тесты», в качестве тестов выступают
классы с наборами методов с аннотациями @Test. Для этого у него должен быть статический
метод start(), которому в качестве параметра передается или объект типа Class, или имя класса.
Из «класса-теста» вначале должен быть запущен метод с аннотацией @BeforeSuite, если такой имеется,
далее запущены методы с аннотациями @Test, а по завершению всех тестов – метод с аннотацией @AfterSuite.
К каждому тесту необходимо также добавить приоритеты (int числа от 1 до 10), в соответствии с которыми
будет выбираться порядок их выполнения, если приоритет одинаковый, то порядок не имеет значения. Методы
с аннотациями @BeforeSuite и @AfterSuite должны присутствовать в единственном экземпляре, иначе
необходимо бросить RuntimeException при запуске «тестирования».
 */

import java.lang.reflect.Method;

public class TestApp {
private static Class clazz;

    private TestApp() {
    }

    public static void start(Class clazz){
        TestApp.clazz = clazz;
        checkAnnotation();
        beforeSuite();
        test();
        afterSuite();
    }

    public static void start(String className){
        try {
            start(Class.forName(className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private static void beforeSuite(){

    }

    private static void afterSuite(){

    }

    private static void test(){

    }

    private static void checkAnnotation() {
        Method[] methods = TestApp.clazz.getMethods();
        int containBefore = 0;
        int containAfter = 0;
        for (Method method : methods) {
            if (method.getAnnotation(BeforeSuite.class) != null) {
                containBefore++;
            }
            if (method.getAnnotation(AfterSuite.class) != null) {
                containAfter++;
            }
        }
        if (containAfter > 1 || containBefore > 1) {
            throw new RuntimeException("Annotation After/Before must be only one!");
        }
    }

}
