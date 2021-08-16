package sample.util;
import sample.functions.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DocUtil implements TabulatedFunction, Cloneable {
    private ArrayTabulatedFunction currFunction = new ArrayTabulatedFunction(0 ,10 , 11);
    private String currDocName = "Usual";
    private boolean modified  = false;
    private boolean fileNameAssigned = false;

    public void newFunction(double leftX, double rightX, int pointsCount){
        currFunction = new ArrayTabulatedFunction(leftX ,rightX,pointsCount);
        modified = true;
    }
    /*
    public void saveFunction (){
        try (FileWriter writer = new FileWriter(currDocName)){
            TabulatedFunctions.writeTabulatedFunction(currFunction ,File file );

        }
        catch (IOException e ){

        }
    }*/

    /*
    public void saveFunctionAs (String fileName){
        try (FileWriter writer = new FileWriter(fileName)){
            TabulatedFunctions.writeTabulatedFunction(currFunction ,writer );

        }
        catch (IOException e ){

        }
    }
    */
    public void saveFunction(File file) throws IOException
    {
        //FileWriter writer = new FileWriter(file); //изменено
        TabulatedFunctions.writeTabulatedFunction(currFunction, file);
        modified = false;
        //fileNameAssigned = true;
    }

    public void saveFunctionAs(File file) throws IOException //изменено
    {
        //FileWriter writer = new FileWriter(file);
        TabulatedFunctions.writeTabulatedFunction(currFunction, file);
        currDocName = file.getName();
        fileNameAssigned = true;
        modified = false;
    }
    public void loadFunction(File fileName){
        try (FileReader reader = new FileReader(fileName)){
            currDocName = fileName.getName();
            TabulatedFunction hz =  TabulatedFunctions.readTabulatedFunction(reader);
            currFunction = (ArrayTabulatedFunction) hz;
            fileNameAssigned = true;
            modified = false;
        }
        catch (IOException e){

        }

    }
    public void tabulateFunction(Function function, double leftX, double rightX, int pointsCount){
        currFunction = (ArrayTabulatedFunction) TabulatedFunctions.tabulate(function ,leftX,rightX,pointsCount);
        modified = true;
    }
    public boolean isModified (){
        return modified;
    }
    public boolean isFileNameAssigned(){
        return fileNameAssigned;
    }


    public boolean getFileNameAssigned (){
        return fileNameAssigned;
    }

    @Override
    public FunctionPoint getPoint(int index) {
        return currFunction.getPoint(index);
    }

    @Override
    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException {
        return currFunction.getPointX(index);
    }

    @Override
    public void setPointX(int index, double x) throws InappropriateFunctionPointException, FunctionPointIndexOutOfBoundsException {
        currFunction.setPointX(index , x );
        modified = true;
    }

    @Override
    public double getPointY(int index) {
        return currFunction.getPointY(index);
    }

    @Override
    public void setPointY(int index, double y) {
        currFunction.setPointY(index ,y );
        modified = true;

    }

    @Override
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException, FunctionPointIndexOutOfBoundsException {
        currFunction.setPoint(index , point);
        modified = true;

    }

    @Override
    public void deletePoint(int index) {
        currFunction.deletePoint(index);
    }

    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        currFunction.addPoint(point);
        modified = true;

    }

    @Override
    public void printTabulated() {
        currFunction.printTabulated();
    }

    @Override
    public int getPointsCount() {
        return currFunction.getPointsCount();
    }



    @Override
    public double getLeftDomainBorder() {
        return currFunction.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return currFunction.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return currFunction.getFunctionValue(x);
    }
    @Override
    public String toString (){
        StringBuilder tmp = new StringBuilder();
        tmp.append(currFunction.toString());
        tmp.append(currDocName);
        tmp.append(fileNameAssigned);
        tmp.append(modified);
        return tmp.toString();
    }

    @Override
    public int hashCode(){
        return  this.toString().hashCode();
    }

    @Override
    public boolean equals ( Object o) {

        if( this == o){
            return true;
        }

        if(o == null ){
            return false;
        }

        if( o instanceof DocUtil){
            DocUtil tmpo = (DocUtil) o;
            if( (currFunction.equals(tmpo.currFunction))&&
            modified == tmpo.modified && currDocName == tmpo.currDocName&&
            fileNameAssigned ==tmpo.fileNameAssigned)
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        DocUtil clone = new DocUtil();
        clone.currFunction = (ArrayTabulatedFunction) currFunction.clone();
        clone.fileNameAssigned = fileNameAssigned;
        clone.currDocName = currDocName;
        clone.modified = modified;
        return clone;
    }



}
