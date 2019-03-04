import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.ArrayList;
import java.io.FileInputStream;

//use interfaces if expanding this program to patristocrats

public class Generator{
    /*public static void main(String[]args){
        try{
            Cipher c1 = generate("xeno1.dat",1);
            System.out.println(c1); System.out.println();
            System.out.println(c1.solution());
        } catch (FileNotFoundException e){
            System.out.println("Use a correct filename!");
        }
    }*/

    public static Xenocrypt generate(String filename) throws FileNotFoundException{
        File file = new File(filename);
        Scanner s = new Scanner(new FileInputStream(file));
        ArrayList<String> lines = new ArrayList<>();
        while (s.hasNextLine()){
            lines.add(s.nextLine());
        }
        Random r = new Random();
        Xenocrypt cip = new Xenocrypt(lines.get(Math.abs(r.nextInt(lines.size()))));
        s.close();
        return cip;
    }

    public static Xenocrypt generate(String filename, int seed) throws FileNotFoundException{
        File file = new File(filename);
        Scanner s = new Scanner(new FileInputStream(file));
        ArrayList<String> lines = new ArrayList<>();
        while (s.hasNextLine()){
            lines.add(s.nextLine());
        }
        Random r = new Random(seed);
        Xenocrypt cip = new Xenocrypt(lines.get(Math.abs(r.nextInt(lines.size()))), seed); //will diversify to patristos later?
        s.close();
        return cip;
    }
}