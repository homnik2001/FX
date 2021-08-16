package sample.functions.meta;

import sample.functions.Function;

public class Power implements Function {
    Function fun;
    double degree;
    public Power (Function f , double degree){
        fun = f;
        this.degree = degree;

    }
    public double getLeftDomainBorder(){
        return fun.getLeftDomainBorder();
    }
    public double getRightDomainBorder(){
        return fun.getRightDomainBorder();
    }
    public double getFunctionValue(double x){
        return Math.pow(fun.getFunctionValue(x), degree);
    }
}
