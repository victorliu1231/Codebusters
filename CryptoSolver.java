//API : http://mabe02.github.io/lanterna/apidocs/2.1/
import com.googlecode.lanterna.terminal.Terminal.SGR;
import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.Key.Kind;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.Terminal.Color;
import com.googlecode.lanterna.terminal.TerminalSize;

import java.io.FileNotFoundException;

import com.googlecode.lanterna.LanternaException;
import com.googlecode.lanterna.input.CharacterPattern;
import com.googlecode.lanterna.input.InputDecoder;
import com.googlecode.lanterna.input.InputProvider;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.KeyMappingProfile;

import java.util.ArrayList;


public class CryptoSolver {
    public static Terminal terminal;
    public static Keyy keyy;
    public static int x = 10;
    public static int y = 10;
    public static String crypto;
    public static ArrayList<CryptoCharacter> CryptoCharacters = new ArrayList<CryptoCharacter>();
    public static String letters = "abcdefghijklmnopqrstuvwxyz\u00e1\u00e9\u00ED\u00F3\u00FA\u00FC\u00F1"+"ABCDEFGHIJKLMNOPQRSTUVWXYZ\u00C1\u00c9\u00CD\u00D3\u00DA\u00DC\u00D1";

    public static void putString(int r, int c, String s){
        terminal.moveCursor(r,c);
        for(int i = 0; i < s.length();i++){
            terminal.putCharacter(s.charAt(i));
        }
    }

    public static void putString(int r, int c,
            String s, Terminal.Color forg, Terminal.Color back ){
        terminal.moveCursor(r,c);
        terminal.applyBackgroundColor(forg);
        terminal.applyForegroundColor(Terminal.Color.BLACK);

        for(int i = 0; i < s.length();i++){
            terminal.putCharacter(s.charAt(i));
        }
        terminal.applyBackgroundColor(Terminal.Color.DEFAULT);
        terminal.applyForegroundColor(Terminal.Color.DEFAULT);
    }

    public static void putCryptoOnScreen(Xenocrypt cipher){
        keyy = cipher.key();
        crypto = cipher.crypto();
        terminal.applyBackgroundColor(Terminal.Color.MAGENTA);
        for (int i = 0; i < crypto.length(); i++){
            terminal.moveCursor(x,y);
            if (crypto.charAt(i) == ' '){
                terminal.applyBackgroundColor(Terminal.Color.DEFAULT);
                terminal.putCharacter(' ');
                terminal.applyBackgroundColor(Terminal.Color.MAGENTA);
            } else {
                CryptoCharacters.add(new CryptoCharacter(x,y,crypto.charAt(i)));
                terminal.putCharacter(' ');
            }
            x++;
        }
        y++;
        x = 10;
        terminal.applyBackgroundColor(Terminal.Color.DEFAULT);
        for (int i = 0; i < crypto.length(); i++){
            terminal.moveCursor(x,y);
            terminal.putCharacter(crypto.charAt(i));
            x++;
        }
        System.out.println(crypto);
    }

    //pre-condition: the character inserted must be part of the "letters" container
    //pre-condition: x and y are actually on the pink or green background color coordinates
    public static void placeLetter(int x, int y, Key key){
        char desiredChar = '\0';
        terminal.moveCursor(x,y);
        for (int i = 0; i < CryptoCharacters.size(); i++){
            CryptoCharacter cryptoChar = CryptoCharacters.get(i);
            if (cryptoChar.x() == x && cryptoChar.y() == y){
                desiredChar = cryptoChar.character();
            }
        }
        char letter = bashKeyCode(key);
        for (int i = 0; i < CryptoCharacters.size(); i++){
            CryptoCharacter cryptoChar = CryptoCharacters.get(i);
            if (cryptoChar.character() == desiredChar){
                terminal.moveCursor(cryptoChar.x(),cryptoChar.y());
                terminal.applyBackgroundColor(Terminal.Color.GREEN);
                terminal.putCharacter(letter);
                terminal.applyBackgroundColor(Terminal.Color.DEFAULT);
            }
        }
    }
    
    public static void main(String[] args) {

        terminal = TerminalFacade.createTerminal();
        terminal.enterPrivateMode();

        TerminalSize size = terminal.getTerminalSize();
        terminal.setCursorVisible(false);

        boolean running = true;
        long startTime =  System.currentTimeMillis();
        try {
            if (args.length == 1){
                Xenocrypt cipher = Generator.generate(args[0]);
                putCryptoOnScreen(cipher);
            }
            if (args.length == 2){
                int seed = Integer.parseInt(args[1]);
                Xenocrypt cipher = Generator.generate(args[0], seed);
                putCryptoOnScreen(cipher);
            }
        } catch (FileNotFoundException e){
            System.out.println("use a valid filename!");
        }
        
        boolean toggleTimeDisplay = false;
        long endTime;
        long lastTime = System.currentTimeMillis();
        putString(5,5,"Press Tab to toggle on display of time!");


        while(running){
            Key key = terminal.readInput();
            
            if (toggleTimeDisplay){
                endTime = System.currentTimeMillis();
                long timer = endTime - startTime;
                timer = timer / 1000;
                if (timer > lastTime){
                    lastTime = timer;
                    String minutes = String.valueOf(lastTime / 60);
                    String seconds = String.valueOf(lastTime % 60);
                    if (seconds.length() < 2){
                        seconds = "0" + seconds; //to fix the seconds notation
                    }
                    minutes+= ":"+seconds;
                    putString(15,20,minutes);
                }
            }

            if (key != null)
            {
                placeLetter(10, 10, key);

                if (key.getKind() == Key.Kind.Escape){
                    terminal.exitPrivateMode();
                }

                if (key.getKind() == Key.Kind.Tab){
                    if (!toggleTimeDisplay){
                        endTime = System.currentTimeMillis();
                        lastTime = (endTime - startTime) / 1000;
                        toggleTimeDisplay = true;
                    } else {
                        putString(15,20,"        ");
                        toggleTimeDisplay = false;
                    }
                }



            }

        //terminal.applySGR(Terminal.SGR.RESET_ALL);


        }


    }

    public static char bashKeyCode(Key key){
        for (int i = 0; i < letters.length(); i++){
            if (key.getCharacter() == letters.charAt(i)){
                return letters.charAt(i);
            }
        }
        return '\0';
    }
}