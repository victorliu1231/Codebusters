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


public class CryptoSolver {
    public static Terminal terminal;
    public static Keyy keyy;
    public static int x = 10;
    public static int y = 10;

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
        String crypto = cipher.crypto();
        terminal.applyBackgroundColor(Terminal.Color.MAGENTA);
        for (int i = 0; i < crypto.length(); i++){
            terminal.moveCursor(x,y);
            if (crypto.charAt(i) == ' '){
                terminal.applyBackgroundColor(Terminal.Color.DEFAULT);
                terminal.putCharacter(' ');
                terminal.applyBackgroundColor(Terminal.Color.MAGENTA);
            } else {
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
                if (key.getKind() == Key.Kind.Escape){
                    terminal.exitPrivateMode();
                }

                if (key.getCharacter() == 't'){
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
}