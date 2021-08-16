package sample.functions.meta;

import sample.functions.Function;

public class Scale implements Function {
    private Function fun;
    private double kX;
    private  double kY;
    public Scale (Function f, double kX , double kY){
        fun = f;
        this.kX = kX;
        this.kY = kY;
    }
    public double getLeftDomainBorder()
    {
        if (kX > 0 ){
            return fun.getLeftDomainBorder()*kX;
        }
        else{
            return fun.getRightDomainBorder()*kX;
        }
    }

    public double getRightDomainBorder(){
        if (kX > 0){
            return fun.getRightDomainBorder()*kX;
        }
        else{
            return fun.getLeftDomainBorder()*kX;
        }
    }
    public double getFunctionValue(double x){
        return fun.getFunctionValue(x)*kY;
    }
}
