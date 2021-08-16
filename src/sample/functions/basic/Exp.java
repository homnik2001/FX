package sample.functions.basic;

import sample.functions.Function;

public class  Exp extends TrigonometricFunction implements Function  {

    public double getFunctionValue(double x){
        return Math.exp(x);
    }
}
