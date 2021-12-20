package TestFramework;

import java.lang.reflect.Method;
import java.util.Comparator;

public class TestComparator implements Comparator<Method> {
    @Override
    public int compare(Method o1, Method o2) {
        int a = o1.getAnnotation(Test.class).priority();
        int b = o2.getAnnotation(Test.class).priority();
        if (a == b) {
            return 0;
        } else if (a > b) {
            return 1;
        } else return -1;
    }
}