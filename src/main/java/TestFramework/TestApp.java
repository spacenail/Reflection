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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public class TestApp<T> {
    private static Method[] methods;
    private TestApp() {
    }

    public  static <T> void start(Class<T> clazz){
        T instance = null;
        try {
            instance = clazz.getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        TestApp.methods = clazz.getDeclaredMethods();

        checkAnnotation();
        beforeSuite(instance);
        test(instance);
        afterSuite(instance);
    }

    public static <T> void start(String className){
        try {
            start((Class<T>) Class.forName(className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private static <T> void beforeSuite(T instance){
        Arrays.stream(methods).
                filter(x -> x.getAnnotation(BeforeSuite.class) != null).
                    forEach(x -> {
                        try {
                            x.invoke(instance);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    });
    }

    private static <T> void afterSuite(T instance){
        Arrays.stream(methods).
                filter(x -> x.getAnnotation(AfterSuite.class) != null).
                    forEach(x -> {
                    try {
                        x.invoke(instance);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
    }

    private static <T> void test(T instance) {
        List<Method> list = Arrays.stream(methods).
                filter(x -> x.getAnnotation(Test.class) != null).
                sorted(new TestComparator()).
                collect(Collectors.toList());

        for (Method method : list) {
            int modifiers = method.getModifiers();
            if (Modifier.isPrivate(modifiers)) {
                method.setAccessible(true);
            }
            try {
                method.invoke(instance);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private static void checkAnnotation() {
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
