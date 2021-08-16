package sample.functions;

public interface TabulatedFunction extends Function{
    public FunctionPoint getPoint (int index);
    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException;
    public void setPointX(int index, double x) throws InappropriateFunctionPointException,FunctionPointIndexOutOfBoundsException;
    public double getPointY(int index);
    public void setPointY(int index, double y);
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException,FunctionPointIndexOutOfBoundsException;
    public void deletePoint(int index);
    public void addPoint(FunctionPoint point)  throws InappropriateFunctionPointException;
    public void printTabulated();
    public int getPointsCount();
    public  Object clone() throws CloneNotSupportedException;
}
