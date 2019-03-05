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

//cant tab huh
//also need to implement autosolution tracker
//and implement which chars been used
//and implement when u have placed a char that already has been taken (via red mark)