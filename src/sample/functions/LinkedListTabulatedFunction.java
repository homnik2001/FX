package sample.functions;

import java.io.Serializable;

public class LinkedListTabulatedFunction implements TabulatedFunction, Serializable ,Cloneable{
    private final FunctionNode head = new FunctionNode();
    private FunctionNode CurEl;
    private int size;
    private static final long serialVersionUID = 7L;

    // отптимизацию доступа дописать после(мб добавив индекс текущего элемента)
    public LinkedListTabulatedFunction (FunctionPoint Mpoints []) throws IllegalArgumentException{
        if( Mpoints.length < 2 ){
            throw new IllegalArgumentException();
        }

        for ( int i = 0; i<Mpoints.length-1; i++){
            if( Mpoints[i+1].Get_X() < Mpoints[i].Get_X() ){
                throw new IllegalArgumentException();
            }
        }

        for ( int i = 0; i < Mpoints.length ; i++){
            FunctionNode tmp = new FunctionNode(Mpoints[i].Get_X() ,Mpoints[i].Get_Y());
            addNodeToTail(tmp);
        }
    }
    public LinkedListTabulatedFunction (double leftX, double rightX, int pointsCount) throws IllegalArgumentException
    {
        if (leftX >= rightX || pointsCount <2 ) {//табулированная функцияя должна иметь 2+  элемента и левая граница меньше правой
            throw new IllegalArgumentException();
        }
        double xCut = rightX - leftX;
        double interval = xCut/pointsCount;
        for(int i = 0;i * interval + leftX <= rightX;i++) {
            FunctionNode tmp = new FunctionNode(leftX + i * interval, 0);
            this.addNodeToTail(tmp);
        }

    }

    public LinkedListTabulatedFunction (double leftX, double rightX, double[] values) throws IllegalArgumentException
    {
        if (leftX >= rightX || values.length <2 ) {//analog
            throw new IllegalArgumentException();
        }
        int pointsCount = values.length;
        double interval = (rightX - leftX) / pointsCount;
        for (int i = 0 ; i * interval + leftX < rightX; i++)
        {
            FunctionNode tmp = new FunctionNode (leftX + i * interval, values[i]);
            this.addNodeToTail(tmp);
        }

    }

    public LinkedListTabulatedFunction ( int pointsCount)
    {
        for (int i = 0; i < pointsCount; i++ ){
            FunctionNode tmp = new FunctionNode();
            this.addNodeToTail(tmp);
        }
    }
    public LinkedListTabulatedFunction (){
        head.prev = head;
        head.next = head;
        CurEl = head;
        size = 0;
    }
    @Override
    public String toString(){
        StringBuilder tmp = new StringBuilder();
        tmp.append("{");
        //{(0.0; 1.2), (1.0; 3.8), (2.0;
        //15.2)},
        for ( int i = 0 ; i < size; i++){
            tmp.append(getNodeByIndex(i).toString());
            if(i != size - 1){
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
        for(int i = 0; i < size; i++){
            hash = (int)(c*(getPointX(i)) + c*(getPointX(i) )  + hash );
            hash = hash*c;
        }
        return hash;
    }
    public FunctionNode getNodeByIndex(int index){
        CurEl = head.next;
        for( int i = 0 ; i < index ; i++){
            CurEl = CurEl.next;
        }
        return CurEl;
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
            if( o instanceof LinkedListTabulatedFunction){
                LinkedListTabulatedFunction tmpo = (LinkedListTabulatedFunction) o;
                if(size == tmpo.size){
                    FunctionNode tmpcur =  tmpo.head.next;
                    CurEl = head.next;
                    while( CurEl != head){
                        if(!CurEl.point.equals(tmpcur)){
                            return false;
                        }
                        CurEl = CurEl.next;
                        tmpcur = tmpcur.next;
                    }
                    return true;
                }
                else{
                    return false;
                }
            }
            else {
                TabulatedFunction tmpo = (TabulatedFunction) o;
                if( size == tmpo.getPointsCount()){
                    CurEl = head.next;
                    for (int i = 0; i < size; i++){
                        if( !CurEl.point.equals(tmpo.getPoint(i))){
                            return false;
                        }
                        CurEl = CurEl.next;
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
    public Object clone()  throws CloneNotSupportedException {
        FunctionPoint clops [] = new FunctionPoint[size];
        CurEl = head.next;
        for (int i = 0 ; i < size; i++){
            clops[i] = (FunctionPoint) CurEl.point.clone();
            CurEl = CurEl.next;
        }
        return new LinkedListTabulatedFunction(clops);
    }
    public FunctionNode addNodeToTail(FunctionNode added){
        CurEl = head.prev;
        CurEl.next = added;
        added.next = head;
        added.prev = CurEl;
        head.prev = added;
        size++;

        return added;
    }
    public FunctionNode addNodeByIndex(int index,FunctionNode added){
        CurEl = head.next;
        for(int i = 0 ; i<= index;i++){
            CurEl = CurEl.next;
        }
        CurEl.prev.next = added;
        added.next = CurEl;
        added.prev = CurEl.prev;
        CurEl.prev = added;
        size++;
        return added;
    }
    public FunctionNode deleteNodeByIndex(int index){
        CurEl = head.next;
        for(int i = 0 ; i < index; i++){
            CurEl = CurEl.next;
        }
        CurEl.prev.next = CurEl.next;
        CurEl.next.prev = CurEl.prev;
        size--;

        return CurEl;
    }
    public int getPointsCount (){
        return size;
    }
    /// методы из TF
    public double getLeftDomainBorder()
    {
        return head.next.point.Get_X();
    }

    public double getRightDomainBorder()
    {
        return head.prev.point.Get_X();
    }

    public double getFunctionValue(double x)
    {
        if ( x < getLeftDomainBorder() || x > getRightDomainBorder()){
            return Double.NaN;}
        else
        {
            int i = 0;
            for (; x > getNodeByIndex(i).point.Get_X(); i++)
            {
            }
            double x2 = getNodeByIndex(i).point.Get_X();
            double y2 = getNodeByIndex(i).point.Get_Y();
            double x1 = getNodeByIndex(i-1).point.Get_X();
            double y1 = getNodeByIndex(i-1).point.Get_Y();
            double y = (x-x1)*(y2-y1)/(x2-x1)+y1;
            return y;
        }
    }
    public FunctionPoint getPoint (int index)
    {
        if ( index < 0 || index >= getPointsCount()) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        else if (index >= 0 && index < getPointsCount())
        {
            FunctionPoint reference = getNodeByIndex(index).point;//this.points[index];
            return reference;
        }
        else {
            return null;}
    }

    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException
    {
        if ( index < 0 || index >= getPointsCount()) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return getNodeByIndex(index).point.Get_X();
    }

    public void setPointX(int index, double x)throws InappropriateFunctionPointException,FunctionPointIndexOutOfBoundsException
    {

        if ( x > getNodeByIndex(index+1).point.Get_X() || x < getNodeByIndex(index-1).point.Get_X() ) {
            throw new InappropriateFunctionPointException();
        }
        if ( index < 0 || index >= getPointsCount()) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        if( index <0 || index > getPointsCount()-1 )
            return;
        else if( x < getLeftDomainBorder() || x > getRightDomainBorder())
            return;
        else
            getNodeByIndex(index).point.Set_X(x);
    }
    public double getPointY(int index)throws FunctionPointIndexOutOfBoundsException
    {
        if ( index < 0 || index >= getPointsCount()) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return getNodeByIndex(index).point.Get_Y();
    }
    public void setPointY(int index, double y)throws FunctionPointIndexOutOfBoundsException
    {
        if ( index < 0 || index >= getPointsCount()) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        getNodeByIndex(index).point.Set_Y(y);
    }
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException,FunctionPointIndexOutOfBoundsException
    {
        if ( point.Get_X() > getNodeByIndex(index+1).point.Get_X()  || point.Get_X() <  getNodeByIndex(index-1).point.Get_X()) {
            throw new InappropriateFunctionPointException();
        }
        if ( index >= 0 && index < getPointsCount()) {

            if( point.Get_X() < getLeftDomainBorder() || point.Get_X() > getRightDomainBorder() )
                return;
            else {
                FunctionNode tmp =  getNodeByIndex(index);
                tmp.point = point;
            }
        }
        else{
            throw new FunctionPointIndexOutOfBoundsException();
        }
    }
    public void deletePoint(int index) throws IllegalStateException,FunctionPointIndexOutOfBoundsException
    {   if (getPointsCount() < 3 ){
        throw new IllegalStateException();
         }
        if ( index < 0 || index >= getPointsCount()) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        deleteNodeByIndex(index);
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException
    {

        for(int i = 0 ; i < getPointsCount(); i++)
        {
            if (Math.abs(getNodeByIndex(i).point.Get_X() - point.Get_X()) < 0.00001){
                throw new InappropriateFunctionPointException();
            }
        }
        FunctionNode tmpl = new FunctionNode(point.Get_X() , point.Get_Y());


        if ( point.Get_X() < getLeftDomainBorder() )
        {
            //переписать по индексы прибавку
            //FunctionNode tmp = getNodeByIndex(0);
            tmpl.prev = head;
            tmpl.next = head.next;
            head.next.prev = tmpl;
            head.next = tmpl;
            size++;

        }
        else if ( point.Get_X() >= getNodeByIndex(getPointsCount()-1).point.Get_X())
        {
            addNodeToTail(tmpl);
        }
        else
        {
            for (int i = 0; i < getPointsCount() - 2; i++)
            {
                if(point.Get_X() > getNodeByIndex(i).point.Get_X() && point.Get_X() < getNodeByIndex(i+1).point.Get_X())
                {
                    addNodeByIndex(i , tmpl);
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
