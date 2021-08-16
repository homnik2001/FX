package sample.functions.meta;

import sample.functions.Function;

public class Shift implements Function {
    private double mX;
    private double mY;
    private Function fun;

    public Shift (Function fun, double mX, double mY){
        this.mX = mX;
        this.mY = mY;
        this.fun =fun;
    }
    public double getLeftDomainBorder(){
        return fun.getLeftDomainBorder()+mX;
    }
    public double getRightDomainBorder(){
        return fun.getRightDomainBorder()+mY;
    }
    public double getFunctionValue(double x){
        return  fun.getFunctionValue(x) + mY;
    }
}
