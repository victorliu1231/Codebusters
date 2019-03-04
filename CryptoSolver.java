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
    public static String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

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
            if (!letters.contains(crypto.substring(i,i+1))){
                terminal.applyBackgroundColor(Terminal.Color.DEFAULT);
                terminal.putCharacter(' ');
                terminal.applyBackgroundColor(Terminal.Color.MAGENTA);
            } else {
                terminal.putCharacter(' ');
            }
            CryptoCharacters.add(new CryptoCharacter(x,y,i,crypto.substring(i,i+1)));
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
        String desiredChar = null;
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
            if (cryptoChar.character().equals(desiredChar)){
                terminal.moveCursor(cryptoChar.x(),cryptoChar.y());
                terminal.applyBackgroundColor(Terminal.Color.MAGENTA);
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
        terminal.moveCursor(10,10);
        String desiredChar = CryptoCharacters.get(0).character();
        for (int i = 0; i < CryptoCharacters.size(); i++){ //makes all the common CryptoChars green
            CryptoCharacter cryptoChar = CryptoCharacters.get(i);
            if (cryptoChar.character().equals(desiredChar)){
                terminal.moveCursor(cryptoChar.x(),cryptoChar.y());
                terminal.applyBackgroundColor(Terminal.Color.GREEN);
                terminal.putCharacter(' ');
                terminal.applyBackgroundColor(Terminal.Color.DEFAULT);
            }
        }
        terminal.applyBackgroundColor(Terminal.Color.DEFAULT);
        CryptoCharacter currentCryptoChar = CryptoCharacters.get(0);
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
                    terminal.applyBackgroundColor(Terminal.Color.DEFAULT);
                    putString(15,20,minutes);
                }
            }

            if (key != null)
            {
                //placeLetter(11, 10, key);

                if (key.getKind() == Key.Kind.Escape){
                    terminal.exitPrivateMode();
                }

                if (key.getKind() == Key.Kind.Tab){
                    if (!toggleTimeDisplay){
                        endTime = System.currentTimeMillis();
                        lastTime = (endTime - startTime) / 1000;
                        toggleTimeDisplay = true;
                    } else {
                        terminal.applyBackgroundColor(Terminal.Color.DEFAULT);
                        putString(15,20,"        ");
                        toggleTimeDisplay = false;
                    }
                }

                if (key.getKind() == Key.Kind.ArrowRight){
                    if (currentCryptoChar.index() == CryptoCharacters.size() - 1){ //if it's just on the verge of out of bounds
                        currentCryptoChar = CryptoCharacters.get(0); //then loop back to beginning
                    } else {
                        currentCryptoChar = CryptoCharacters.get(currentCryptoChar.index()+1); //else next cryptochar
                    }
                    for (int i = 0; i < CryptoCharacters.size(); i++){ //cleans the board of terminal color before replacing stuff with blue
                        CryptoCharacter cleaningCryptoChar = CryptoCharacters.get(i);
                        if (letters.contains(cleaningCryptoChar.character())){
                            terminal.moveCursor(cleaningCryptoChar.x(), cleaningCryptoChar.y());
                            terminal.applyBackgroundColor(Terminal.Color.MAGENTA);
                            terminal.putCharacter(' '); //find a way to find previous guesses
                        }
                    }
                    if (currentCryptoChar.index() != 0){
                        if (letters.contains(CryptoCharacters.get(currentCryptoChar.index()-1).character())){ //if previous character isn't in list
                            terminal.moveCursor(currentCryptoChar.x()-1, currentCryptoChar.y());
                            terminal.applyBackgroundColor(Terminal.Color.MAGENTA);
                            terminal.putCharacter(' '); //change later to preserve previous guesses
                        }
                    } else {
                        terminal.moveCursor(CryptoCharacters.get(CryptoCharacters.size() - 2).x(), CryptoCharacters.get(CryptoCharacters.size()-1).y()); //moves to end
                        terminal.applyBackgroundColor(Terminal.Color.MAGENTA);
                        terminal.putCharacter(' '); //change later to preserve previous guesses
                    }
                    terminal.moveCursor(currentCryptoChar.x(), currentCryptoChar.y());
                    x = currentCryptoChar.x();
                    y = currentCryptoChar.y();
                    if (letters.contains(currentCryptoChar.character())){
                        for (int i = 0; i < CryptoCharacters.size(); i++){ //finding the CryptoCharacter the mouse is hovering over
                            CryptoCharacter cryptoChar = CryptoCharacters.get(i);
                            if (cryptoChar.x() == x && cryptoChar.y() == y){
                                desiredChar = cryptoChar.character();
                            }
                        }
                        for (int i = 0; i < CryptoCharacters.size(); i++){ //makes all the common CryptoChars green
                            CryptoCharacter cryptoChar = CryptoCharacters.get(i);
                            if (cryptoChar.character().equals(desiredChar)){
                                terminal.moveCursor(cryptoChar.x(),cryptoChar.y());
                                terminal.applyBackgroundColor(Terminal.Color.GREEN);
                                terminal.putCharacter(' ');
                                terminal.applyBackgroundColor(Terminal.Color.DEFAULT);
                            }
                        }
                    }
                }

                if (key.getKind() == Key.Kind.ArrowLeft){
                    if (currentCryptoChar.index() == 0){ //if it's just on the verge of out of bounds
                        currentCryptoChar = CryptoCharacters.get(CryptoCharacters.size() - 1); //then loop back to end
                    } else {
                        currentCryptoChar = CryptoCharacters.get(currentCryptoChar.index()-1); //else last cryptochar
                    }
                    for (int i = 0; i < CryptoCharacters.size(); i++){ //cleans the board of terminal color before replacing stuff with blue
                        CryptoCharacter cleaningCryptoChar = CryptoCharacters.get(i);
                        if (letters.contains(cleaningCryptoChar.character())){
                            terminal.moveCursor(cleaningCryptoChar.x(), cleaningCryptoChar.y());
                            terminal.applyBackgroundColor(Terminal.Color.MAGENTA);
                            terminal.putCharacter(' '); //find a way to find previous guesses
                        }
                    }
                    if (currentCryptoChar.index() != CryptoCharacters.size() - 1){
                        if (letters.contains(CryptoCharacters.get(currentCryptoChar.index()+1).character())){ //if next character isn't in list
                            terminal.moveCursor(currentCryptoChar.x()+1, currentCryptoChar.y());
                            terminal.applyBackgroundColor(Terminal.Color.MAGENTA);
                            terminal.putCharacter(' '); //change later to preserve previous guesses
                        }
                    } else {
                        terminal.moveCursor(CryptoCharacters.get(0).x(), CryptoCharacters.get(0).y()); //moves to end
                        terminal.applyBackgroundColor(Terminal.Color.MAGENTA);
                        terminal.putCharacter(' '); //change later to preserve previous guesses
                    }
                    terminal.moveCursor(currentCryptoChar.x(), currentCryptoChar.y());
                    x = currentCryptoChar.x();
                    y = currentCryptoChar.y();
                    if (letters.contains(currentCryptoChar.character())){
                        for (int i = 0; i < CryptoCharacters.size(); i++){ //finding the CryptoCharacter the mouse is hovering over
                            CryptoCharacter cryptoChar = CryptoCharacters.get(i);
                            if (cryptoChar.x() == x && cryptoChar.y() == y){
                                desiredChar = cryptoChar.character();
                            }
                        }
                        for (int i = 0; i < CryptoCharacters.size(); i++){ //makes all the common CryptoChars green
                            CryptoCharacter cryptoChar = CryptoCharacters.get(i);
                            if (cryptoChar.character().equals(desiredChar)){
                                terminal.moveCursor(cryptoChar.x(),cryptoChar.y());
                                terminal.applyBackgroundColor(Terminal.Color.GREEN);
                                terminal.putCharacter(' ');
                                terminal.applyBackgroundColor(Terminal.Color.DEFAULT);
                            }
                        }
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