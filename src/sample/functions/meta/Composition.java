package sample.functions.meta;

import sample.functions.Function;

public class Composition implements Function {
    private Function father;
    private Function daughter;

    public Composition (Function father, Function daughter){
        this.father = father;
        this.daughter = daughter;
    }

    public double getLeftDomainBorder(){
        return father.getLeftDomainBorder();
    }

    public double getRightDomainBorder(){
        return father.getRightDomainBorder();
    }
    public double getFunctionValue(double x){
        return father.getFunctionValue(daughter.getFunctionValue(x));
    }
}
