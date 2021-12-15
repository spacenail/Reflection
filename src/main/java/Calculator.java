public class Calculator {
    public int sum(int a, int b){
        return a+b;
    }

    public int div(int a, int b){
        if(b == 0){
            throw new RuntimeException("Деление на ноль!");
        }
        return a/b;
    }
}
