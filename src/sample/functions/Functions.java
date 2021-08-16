package sample.functions;

import sample.functions.meta.*;

final class Functions {
    private Functions(){
        throw new UnsupportedOperationException();
    }
    public static Function shift(Function f, double shiftX, double shiftY){
        return new Shift(f, shiftX, shiftY);
    }
    public static Function scale(Function f, double scaleX, double scaleY){
        return new Scale(f, scaleX, scaleY);
    }
    public static Function power(Function f, double power){
        return new Power(f, power);
    }
    public static Function sum(Function f1, Function f2){
        return new Sum(f1, f2);
    }
    public static Function mult(Function f1, Function f2){
        return new Mult(f1,f2);
    }
    public static Function composition(Function f1, Function f2){
        return  new Composition(f1,f2);
    }


}
