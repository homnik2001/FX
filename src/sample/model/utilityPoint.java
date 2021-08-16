package sample.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sample.functions.FunctionPoint;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class utilityPoint {
    StringProperty x ,y;



    public utilityPoint (FunctionPoint point){
        x = new SimpleStringProperty( ((Double)point.Get_X()).toString());
        y = new SimpleStringProperty( ((Double)point.Get_Y()).toString());
    }
    public utilityPoint (double x , double y){
        this.x = new SimpleStringProperty( ((Double)x).toString() );
        this.y = new SimpleStringProperty( ((Double)y).toString() );
    }


    public FunctionPoint toFunctionPoint(){
        return new FunctionPoint ( Double.parseDouble(x.getValue()), Double.parseDouble(y.getValue()));
    }

    public void setX(double x) {
        this.x = new SimpleStringProperty( ((Double)x).toString());
    }

    public double getX() {
        return Double.parseDouble( x.getValue() );
    }
    public StringProperty xProperty (){
        return x;
    }

    public void setY(double y) {
        this.y = new SimpleStringProperty( ((Double)y).toString());
    }

    public double getY() {
        return Double.parseDouble( y.getValue() );
    }
    public StringProperty yProperty (){
        return y;
    }

}
