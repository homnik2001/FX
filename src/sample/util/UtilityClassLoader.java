package sample.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class UtilityClassLoader extends ClassLoader {

    /**
     * Путь до директории с модулями.
     */

    public UtilityClassLoader( ClassLoader parent) {
        super(parent);
    }

    @Override
    public Class<?> findClass(String className) throws ClassNotFoundException {
        try {
/**
 * Получем байт-код из файла и загружаем класс в рантайм
 */         System.out.println(className);

            byte b[] = fetchClassFromFS( className +".class");
            String classnamefrdefine = className.substring(className.length()- 3 , className.length());

            return defineClass("sample.functions.basic"+classnamefrdefine, b, 0, b.length);
        } catch (FileNotFoundException ex) {
            return super.findClass(className);
        } catch (IOException ex) {
            return super.findClass(className);
        }

    }

    private byte[] fetchClassFromFS(String path) throws FileNotFoundException, IOException {
        InputStream is = new FileInputStream(new File(path));

// Get the size of the file
        long length = new File(path).length();

        if (length > Integer.MAX_VALUE) {
// File is too large
        }

// Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];

// Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

// Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+path);
        }

// Close the input stream and return bytes
        is.close();
        return bytes;

    }


}