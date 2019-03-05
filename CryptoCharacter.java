public class CryptoCharacter{
    private int x;
    private int y;
    private String character;
    private int index;
    private String guessedChar;

    public CryptoCharacter(int x, int y, int index, String c){
        this.x = x;
        this.y = y;
        character = c;
        this.index = index;
        guessedChar = " ";
    }

    public CryptoCharacter(int x, int y, int index, String c, String guessedChar){
        this.x = x;
        this.y = y;
        character = c;
        this.index = index;
        this.guessedChar = guessedChar;
    }

    public int x(){
        return x;
    }

    public int y(){
        return y;
    }

    public String character(){
        return character;
    }

    public int index(){
        return index;
    }

    public String guessedChar(){
        return guessedChar;
    }

    public void setGuessedChar(String s){
        guessedChar = s;
    }
}

//also need to implement autosolution tracker