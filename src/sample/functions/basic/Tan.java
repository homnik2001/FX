package sample.functions.basic;

public class Tan extends TrigonometricFunction{
    // Непонятно, что делать с выколотами из области определения точками в тангенсе
    public double getFunctionValue(double x){
        return Math.tan(x);
    }
}
