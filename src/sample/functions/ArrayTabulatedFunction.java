package sample.functions;

import java.io.*;

public class ArrayTabulatedFunction implements TabulatedFunction, Serializable,Externalizable ,Cloneable  {
private  FunctionPoint [] points ;
private static final long serialVersionUID = 1L;
//
public ArrayTabulatedFunction (){
    points = new FunctionPoint[2];
}
public ArrayTabulatedFunction ( FunctionPoint  Mpoints []) throws IllegalArgumentException{
    if( Mpoints.length < 2 ){
        throw new IllegalArgumentException();
    }
    for ( int i = 0; i<Mpoints.length-1; i++){
        if( Mpoints[i+1].Get_X() < Mpoints[i].Get_X() ){
            throw new IllegalArgumentException();
        }
    }
    points = new FunctionPoint[Mpoints.length];
    for( int i = 0 ; i < Mpoints.length; ++i){
        points[i] = Mpoints[i];
    }

}
public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalArgumentException
{
    if (leftX >= rightX || pointsCount <2 ) {//табулированная функцияя должна иметь 2+  элемента и левая граница меньше правой
        throw new IllegalArgumentException();
    }
    double xCut = rightX - leftX;
    double interval = xCut/(pointsCount-1);
    points = new FunctionPoint[pointsCount];
    for(int i = 0;i * interval + leftX <= rightX;i++) {
        FunctionPoint tPoint = new FunctionPoint(leftX + i * interval, 0);
        points[i] = tPoint;
    }
}
public ArrayTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalArgumentException
{
    if (leftX >= rightX || values.length <2 ) {//analog
        throw new IllegalArgumentException();
    }
    int pointsCount = values.length;
    double interval = (rightX - leftX) / pointsCount;
    points = new FunctionPoint[pointsCount];
    for (int i = 0 ; i * interval + leftX < rightX; i++)
    {
        FunctionPoint tPoint = new FunctionPoint(leftX + i * interval, values[i]);
        points[i] = tPoint;
    }
}
public ArrayTabulatedFunction(int pointsCount)
{
    points = new FunctionPoint[pointsCount];
}
@Override
public String toString(){
    StringBuilder tmp = new StringBuilder();
    tmp.append("{");
    //{(0.0; 1.2), (1.0; 3.8), (2.0;
    //15.2)},
    for ( int i = 0 ; i < points.length; i++){
        tmp.append(points[i].Get_X()).append("; ").append(points[i].Get_Y()).append(")");
        if(i != points.length - 1){
            tmp.append(", ");
        }
        else{
            tmp.append("}");
        }
    }
    return tmp.toString();
}
@Override
public int hashCode(){
    final int c = 3;
    int hash = 7;
    for(int i = 0; i < points.length; i++){
        hash = (int)(c*(points[i].Get_X()) + c*(points[i].Get_Y() )  + hash );
        hash = hash*c;
    }
    return hash;
}
@Override
public boolean equals(Object o){

    if (this == o){
        return true;
    }
    if ( o == null){
        return false;
    }


    if ( o instanceof TabulatedFunction ){
        if( o instanceof ArrayTabulatedFunction){
            ArrayTabulatedFunction tmpo = (ArrayTabulatedFunction) o;
            if(points.length ==   tmpo.points.length){
                for(int i = 0; i < points.length; i++){
                    if(tmpo.points[i].x != this.points[i].x || tmpo.points[i].y != this.points[i].y ){
                        return false;
                    }
                }
                return true;
            }
            else{
                return false;
            }
        }
        else {
            TabulatedFunction tmpo = (TabulatedFunction) o;
            if( points.length == tmpo.getPointsCount()){
                for (int i = 0; i < points.length; i++){
                    if( points[i].x != tmpo.getPointX(i) || points[i].y != tmpo.getPointY(i)){
                        return false;
                    }
                }
                return true;
            }
            else{
                return false;
            }
        }

    }
    else{
      return false;
    }
}
@Override
public Object clone()  throws CloneNotSupportedException{
    ArrayTabulatedFunction clone  = (ArrayTabulatedFunction) super.clone();
    clone.points = new FunctionPoint[this.points.length];
    for (int i = 0 ; i < 0 ; i++){
        clone.points[i] = (FunctionPoint)points[i].clone();
    }
    return  clone;
}
@Override
public void writeExternal(ObjectOutput out) throws IOException {
    out.writeObject(points);
}
@Override
public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    points = (FunctionPoint[]) in.readObject();
}
public double getLeftDomainBorder()
{
    return points[0].x;
}
public double getRightDomainBorder()
{
    return points[points.length-1].x;
}
public double getFunctionValue(double x)
{
    if ( x < points[0].x || x > points[points.length-1].x){
        return Double.NaN;}
    else
    {
        int i = 0;
        for (; x > points[i].x ; i++)
        {
        }
        double x2 = points[i].x;
        double y2 = points[i].y;
        double x1 = points[i-1].x;
        double y1 = points[i-1].y;
        double y = (x-x1)*(y2-y1)/(x2-x1)+y1;
        return y;
    }
}

public int getPointsCount()
{
    return points.length;
}
//FunctionPoint getPoint(int index);//должен возвращать ссылку наобъект, описывающий точку, имеющую указанный номер. При написании метода обеспечьтекорректную инкапсуляцию.
public FunctionPoint getPoint (int index)
{
    if ( index < 0 || index >= points.length) {
        throw new FunctionPointIndexOutOfBoundsException();
    }
    else if (index >= 0 && index < getPointsCount())
    {
        FunctionPoint reference = this.points[index];
        return reference;
    }
    else {
        return null;}
}
public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException
{
    if ( index < 0 || index >= points.length) {
        throw new FunctionPointIndexOutOfBoundsException();
    }
    return points[index].x;
}
/*
public void setPointX(int index, double x)throws InappropriateFunctionPointException,FunctionPointIndexOutOfBoundsException
{

    if ( x > points[index+1].x || x < points[index-1].x ) {
        throw new InappropriateFunctionPointException();
    }
    if ( index < 0 || index >= points.length) {
        throw new FunctionPointIndexOutOfBoundsException();
    }
    if( index <0 || index > points.length-1 )
        return;
    else if( x < points[0].x || x > points[points.length-1].x )
        return;
    else
        points[index].x = x;
}*/
public void setPointX(int index, double x) throws InappropriateFunctionPointException, FunctionPointIndexOutOfBoundsException
{
    if (index >= 0 && index < getPointsCount())
    {
        if (index == 0)
        {
            if (x < this.points[index + 1].x)
            {
                this.points[index].x = x;
            }
            else
            {
                throw new InappropriateFunctionPointException();
            }
        }
        else if (index == getPointsCount() - 1)
        {
            if (x > this.points[index - 1].x)
            {
                this.points[index].x = x;
            }
            else
            {
                throw new InappropriateFunctionPointException();
            }
        }
        else
        {
            if (x > this.points[index - 1].x && x < this.points[index + 1].x)
            {
                this.points[index].x = x;
            }
            else
            {
                throw new InappropriateFunctionPointException();
            }
        }
    }
    else
    {
        throw new FunctionPointIndexOutOfBoundsException();
    }
}
public double getPointY(int index)throws FunctionPointIndexOutOfBoundsException
{
    if ( index < 0 || index >= points.length) {
        throw new FunctionPointIndexOutOfBoundsException();
    }
    return points[index].y;
}
public void setPointY(int index, double y)throws FunctionPointIndexOutOfBoundsException
{
    if ( index < 0 || index >= points.length) {
        throw new FunctionPointIndexOutOfBoundsException();
    }
    points[index].y = y;
}
public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException,FunctionPointIndexOutOfBoundsException
{
    if ( point.x > points[index+1].x || point.x < points[index-1].x ) {
        throw new InappropriateFunctionPointException();
    }
    if ( index > 0 && index <= points.length) {

        if( point.x < points[0].x || point.x > points[points.length-1].x )
            return;
        else
            points[index] = point;

    }
    else{
        throw new FunctionPointIndexOutOfBoundsException();
    }

}

public void deletePoint(int index) throws IllegalStateException, FunctionPointIndexOutOfBoundsException
{
    if (getPointsCount() < 3)
    {
        throw new IllegalStateException();
    }
    if (index >= 0 && index < getPointsCount())
    {
        FunctionPoint [] tmp = new FunctionPoint[getPointsCount()-1];
        System.arraycopy(this.points, 0, tmp, 0, index);
        System.arraycopy(this.points, index+1, tmp, index, points.length - index -1);
        this.points = tmp;
    }
    else
    {
        throw new FunctionPointIndexOutOfBoundsException();
    }
}

public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException
{

    for(int i = 0 ; i < points.length; i++)
    {
        if (Math.abs(points[i].x - point.x) < 0.00001){
            throw new InappropriateFunctionPointException();
        }
    }
    ArrayTabulatedFunction tmp = new ArrayTabulatedFunction(points.length + 1);
    System.arraycopy(points, 0, tmp.points, 0, points.length);
    tmp.points[points.length] = point;
    points = tmp.points;
    FunctionPoint example  = new FunctionPoint(point);
    if ( point.x <= points[0].x )
    {
        System.arraycopy(points, 0, points, 1, points.length-1);
        points[0] = point;
    }
    else if ( point.x >= points[getPointsCount()-2].x )
    {

    }
    else
    {
        for (int i = 0; i < points.length - 2; i++)
        {
            if(point.x > points[i].x && point.x < points[i+1].x)
            {
                System.arraycopy(points, i+1, points, i+2, getPointsCount()-i-2);
                points[i+1] = point;
            }
        }

    }
}


public void printTabulated()
{
    for (int i = 0; i < getPointsCount(); i++)
    {
        System.out.println(i + 1 + ") " + "x = " + getPointX(i) + " y= " + getPointY(i));
    }
}




}
