public class CryptoCharacter{
    private int x;
    private int y;
    private char character;

    public CryptoCharacter(int x, int y, char c){
        this.x = x;
        this.y = y;
        character = c;
    }

    public int x(){
        return x;
    }

    public int y(){
        return y;
    }

    public char character(){
        return character;
    }
}