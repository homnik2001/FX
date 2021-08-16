package sample.functions.basic;

import sample.functions.Function;

public class TrigonometricFunction implements Function {
    private final double pi = Math.PI;
    public double getLeftDomainBorder(){
        return Double.NaN;
    }

    public double getRightDomainBorder(){
        return Double.NaN;
    }
    public double getFunctionValue(double x){
        return Math.sin(x);
    }
}
