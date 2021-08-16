package sample.functions;
//
import java.io.*;

public class FunctionPoint implements Serializable , Externalizable , Cloneable{
    double x,y;
    private static final long serialVersionUID = 1L;

    public FunctionPoint (double x1, double y1 )
    {
        x = x1;
        y = y1;
    }
    public FunctionPoint(FunctionPoint point)
    {
        this.x = point.x;
        this.y = point.y;
    }
    public FunctionPoint( )
    {
        x = 0;
        y = 0;
    }
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        x = (double) in.readObject();
        y = (double) in.readObject();
    }
    @Override
    public void writeExternal ( ObjectOutput out )throws IOException{
        out.writeObject(x);
        out.writeObject(y);
    }
    @Override
    public String toString (){
        StringBuilder tmp = new StringBuilder();
        tmp.append("(").append(x).append("; ").append(y).append(")");
        return tmp.toString();
    }

    @Override
    public int hashCode(){//hash depending on the fields
        final int prime = 31;
        double res = 1;
        res = res*prime + this.x;
        res = res*prime + this.y;
        return (int)res;
    }
    @Override
    public boolean equals(Object obj){
        if(this == obj){//совпадение ссылка
            return true;
        }
        if (obj == null){
            return false;
        }
        if (getClass() != obj.getClass()){//equals of class
            return false;
        }

        FunctionPoint other = (FunctionPoint) obj;
        if (Math.abs(x - other.x) > 0.0001) { // checking fields
            return false;
        }
        if  (Math.abs(y - other.y) > 0.0001) {
            return false;
        }
        return true;
    }
    @Override
    public Object clone()  throws CloneNotSupportedException{
        return super.clone();
    }

    public double Get_X(){
        return x;
    }
    public double Get_Y(){
        return y;
    }
    public void Set_X(double val){
        x = val;
    }

    public void Set_Y(double val){
        y = val;
    }





}
