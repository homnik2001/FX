package sample.functions;

import java.io.*;

public class TabulatedFunctions implements Serializable {
    private TabulatedFunctions (){
        throw new UnsupportedOperationException();
    }

    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount)throws IllegalArgumentException{
        if(leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder()){
            throw new IllegalArgumentException();
        }
        ArrayTabulatedFunction tmpToRet = new  ArrayTabulatedFunction( leftX, rightX, pointsCount);
        double interval = (rightX - leftX)/(pointsCount-1);
        double masOfVal [] = new double[pointsCount];
        FunctionPoint masPoints [] = new FunctionPoint[pointsCount];
        for ( int i = 0 ; i < pointsCount;i++ ){
            masPoints[i] =  new FunctionPoint(leftX + interval*i,function.getFunctionValue(leftX + interval*i));
        }
        return new ArrayTabulatedFunction(masPoints);
    }
    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) throws IOException{
        int size = function.getPointsCount();
        DataOutputStream stream = new DataOutputStream(out);
        stream.writeInt(size);

        for (int i = 0 ; i < size;  i++){
            stream.writeDouble(function.getPointX(i));
            stream.writeDouble(function.getPointY(i));
        }
        out.flush();//потоки не закрываем, так как неизвестно, пригодяться ли они в программе дальше
    }
    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException{
        DataInputStream is = new DataInputStream(in);
        int size = is.readInt();
        FunctionPoint mas [] = new FunctionPoint [size];
        for(int i = 0; i < size; i++){
            mas[i] = new FunctionPoint(is.readDouble() , is.readDouble());
        }

        return new ArrayTabulatedFunction(mas);
    }
    /*
    public static void writeTabulatedFunction(TabulatedFunction function, Writer out) throws IOException{
        PrintWriter lnout = new PrintWriter(out);
        int size = function.getPointsCount();
        lnout.print(size + "\n");

        for (int i = 0; i < size; i++){
            lnout.print(function.getPointX(i) + " ");
            lnout.print(function.getPointY(i) + " ");
        }
        out.flush();
    }*/
    public static void writeTabulatedFunction(TabulatedFunction function, File file) throws FileNotFoundException //изменено
    {
        PrintWriter writer = new PrintWriter(file);
        int count = function.getPointsCount();
        writer.print(count);
        writer.print('\n');
        for (int i = 0; i < count; i++)
        {
            writer.print(function.getPointX(i));
            writer.print(" ");
            writer.print(function.getPointY(i));
            writer.print('\n');
        }
        writer.flush();
       // writer.close();
    }

    public static TabulatedFunction readTabulatedFunction(Reader in)throws IOException{
        StreamTokenizer tokenizer = new StreamTokenizer(in);
        tokenizer.nextToken();
        int pointsCount = (int)tokenizer.nval;
        FunctionPoint mpoint[] = new FunctionPoint [pointsCount];
        double x,y;
        for(int i = 0; i < pointsCount; i++){
            tokenizer.nextToken();
            x = tokenizer.nval;
            tokenizer.nextToken();
            y = tokenizer.nval;
            mpoint[i] = new FunctionPoint(x, y);
        }
        return new ArrayTabulatedFunction(mpoint);

    }
}

