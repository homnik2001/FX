package sample.functions.basic;

import sample.functions.Function;

public class Log implements Function {
    double Base;
    public Log (){
        Base = Math.E;
    }
    public double getLeftDomainBorder(){
        return 0;
    }

    public double getRightDomainBorder(){
        return  Double.NaN;
    }

    public double getFunctionValue(double x)
    {
        return Math.log(x)/Math.log(Base);
    }
}
