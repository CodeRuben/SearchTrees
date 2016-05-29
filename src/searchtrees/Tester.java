
package searchtrees;

import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.Double.parseDouble;
import java.util.Scanner;
import java.util.StringTokenizer;
import data.IPAddress;
import data_structures.*;

public class Tester {
  
    public static void main(String[] args) throws FileNotFoundException {
        SplayTree<IPAddress, String> splay = new SplayTree<>();
        AVLTree<IPAddress> avl = new AVLTree<>();

        String[] array = new String[10000];
        String stream = "", ip = "", country = "";
        double latitude = 0.0, longitude = 0.0;
        int ticker = 0, index = 0;

        File input = new File("ip2country.tsv");
        Scanner in = new Scanner(input);
        StringTokenizer line;	

        stream = in.nextLine(); // Remove first line

        // Read lines until the end of the file
        while(in.hasNextLine()) {

            stream = in.nextLine();

            line = new StringTokenizer(stream);
            ip = line.nextToken();
            country = line.nextToken();

            // Handle country names consisting of more than one word
            if(line.countTokens() > 2) {
                int count = line.countTokens() - 2;
                for(int j = 0; j < count; j++)
                    country = country + " " + line.nextToken();
            }

            // Read in floating point values
            latitude = parseDouble(line.nextToken());
            longitude = parseDouble(line.nextToken());

            // Choose 10000 IP addresses for later lookup
            if(ticker % 3 == 0) {
                if(index < 10000)
                    array[index++] = ip;
            }
            ticker++;

            splay.add(new IPAddress(ip, country, latitude, longitude), ip);
            avl.add(new IPAddress(ip, country, latitude, longitude));
        } // End of addition operations
        
        // Run tests on search operations
        test(array, avl, splay); 
    }
    
    /* Function does search operations on an AVL and Splay tree.
     * String array contains 10000 ip addresses to be used for search operations.
     */
    public static void test(String[] array, AVLTree<IPAddress> avl, 
            SplayTree<IPAddress, String> splay) {
        
        IPAddress tmp = null;
        boolean error = false;
        //Test splay tree
        long start = System.currentTimeMillis();            
        for(int i=0; i < 10000; i++)
            tmp = splay.get(new IPAddress(array[i], null, 0, 0));
        long stop = System.currentTimeMillis();

        System.out.println("Time for Splay tree search operation with 10000 elements: " +
        		(stop-start));
        
        //Test avl tree search operations
        start = System.currentTimeMillis();            
        for(int i=0; i < 10000; i++)
            tmp = avl.get(new IPAddress(array[i], null, 0, 0));
        stop = System.currentTimeMillis();

        System.out.println("\nTime for AVL tree search operation with 10000 elements: " +
        		(stop-start));
        
        System.out.println("\nNow testing delete operations....");
        
        // Test delete operation is the splay tree
        for(IPAddress e : splay)
            splay.remove(e);
        if(splay.size() != 0) {
            System.out.println("Error in Splay tree delete operation");
            error = true;
        }
        
        // Test delete operation is the avl tree
        for(IPAddress e : avl)
            avl.delete(e);
        if(avl.size() != 0) {
            System.out.println("Error in AVL tree delete operation");
            error = true;
        }
        
        if(error)
            System.out.println("\nTesting complete.");
        else
            System.out.println("\nTesting complete. No errors found.");
    }
}
