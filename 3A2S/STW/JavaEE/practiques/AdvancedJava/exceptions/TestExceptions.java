package exceptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class TestExceptions
 * To test exceptions of package
 * @author ramonguimera, davidcuadrado
 *
 */
public class TestExceptions {

    /**
     * Constructor
     */
    public TestExceptions() {}
    
    /**
     * Process file 1
     * @param fileName name of file
     * @throws FileNotFoundException if the file is not found
     * @throws IOException
     */
    private static void processFile(final String fileName) 
            throws FileNotFoundException, IOException{
        BufferedReader reader = 
                new BufferedReader(
                        new FileReader(
                                new File(fileName)
                                )
                        );
        String text = null;

        while ((text = reader.readLine()) != null) {
            System.out.println(text);
        }

        if (reader != null) {
            reader.close();
        }
    }
    
    /**
     * Process file 2
     * @param fileName name of file
     */
    private static void processFile2(final String fileName) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File(fileName)));
            String text = null;

            while ((text = reader.readLine()) != null) {
                System.out.println(text);
            }
            
            if (reader != null) {
                reader.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    
    /**
     * Process file 3
     * @param fileName name of file
     * @throws MyException throws custom exception
     */
    private static void processFile3(final String fileName) 
            throws MyException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File(fileName)));
            String text = null;

            while ((text = reader.readLine()) != null) {
                System.out.println(text);
            }
            
            if (reader != null) {
                reader.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new MyException("My exception thrown after FileNotFoundException");
        } catch (IOException e) {
            e.printStackTrace();
            throw new MyException("My exception thrown after IOException");
        } 
    }
    
    /**
     * Process file 4
     * @param fileName name of file
     */
    private static void processFile4(final String fileName) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File(fileName)));
            String text = null;

            while ((text = reader.readLine()) != null) {
                System.out.println(text);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Thank you for using our tool");
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }
    }
    
    /**
     * Tests file exceptions
     * @param args args
     * @throws FileNotFoundException if file not found
     * @throws IOException
     * @throws MyException custom to test
     */
    public static void main(String[] args) 
            throws FileNotFoundException, IOException, MyException {
        
        String fileName = "test.exe";
        
        processFile(fileName);
        processFile2(fileName);
        processFile3(fileName);
        processFile4(fileName);
    }
}
