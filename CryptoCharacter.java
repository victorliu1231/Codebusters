public class CryptoCharacter{
    private int x;
    private int y;
    private char character;
    private int index;

    public CryptoCharacter(int x, int y, int index, char c){
        this.x = x;
        this.y = y;
        character = c;
        this.index = index;
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

    public int index(){
        return index;
    }
}