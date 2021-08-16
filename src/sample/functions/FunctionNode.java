package sample.functions;
import java.io.Serializable;

public class FunctionNode implements Serializable {
    protected FunctionPoint point;
    protected FunctionNode prev;
    protected FunctionNode next;
    private static final long serialVersionUID = 1L;
    public FunctionNode(double val_x , double val_y){
        point = new FunctionPoint(val_x , val_y);
        next = prev = this;
    }
    public FunctionNode(){
        point = new FunctionPoint();
        next = prev = this;
    }
}
