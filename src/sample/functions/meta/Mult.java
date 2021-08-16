package sample.functions.meta;

import sample.functions.Function;

public class Mult implements Function {
    private Function fun1;
    private Function fun2;
    private double lborder;
    private double rborder;


    public Mult (Function f1 , Function f2){
        fun1 = f1;
        fun2 = f2;
        double l1 = f1.getLeftDomainBorder();
        double r1 = f1.getRightDomainBorder();
        double l2 = f2.getLeftDomainBorder();
        double r2 = f2.getRightDomainBorder();
        if(l1 == Double.NaN){
            lborder = l2;
        }
        else if(l2 == Double.NaN){
            rborder = l1;
        }
        else {
            lborder = (l1 < l2) ? l2 : l1;
        }
        if(r1 == Double.NaN){
            lborder = r2;
        }
        else if(r2 == Double.NaN){
            rborder = r1;
        }
        else {
            lborder = (r1 < r2) ? r1 : r2;
        }
    }
    public double getLeftDomainBorder(){
        return lborder;
    }
    public double getRightDomainBorder(){
        return  rborder;
    }
    public double getFunctionValue(double x){
        return fun1.getFunctionValue(x)*fun2.getFunctionValue(x);
    }
}
