import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.ArrayList;
import java.io.FileInputStream;

public class Generator{
    public static void main(String[]args){
        try{
            generate("test.txt");
            Key key = new Key();
        } catch (FileNotFoundException e){
            System.out.println("Use a correct filename!");
        }
    }

    public static void generate(String filename) throws FileNotFoundException{
        File file = new File(filename);
        Scanner s = new Scanner(new FileInputStream(file));
        ArrayList<String> lines = new ArrayList<>();
        while (s.hasNextLine()){
            lines.add(s.nextLine());
        }
        Random r = new Random();
        Cipher c = new Cipher(lines.get(Math.abs(r.nextInt(lines.size()))));
        s.close();
    }
}