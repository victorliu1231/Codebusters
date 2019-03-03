public class Cipher{
    private String plainText;
    private int length;
    private Key key;

    public Cipher(String str){
        plainText = str;
        length = plainText.length();
        key = new Key();
    }

    public Cipher(String str, int seed){
        plainText = str;
        length = plainText.length();
        key = new Key(seed);
    }

    public Key key(){
        return key;
    }

    public int length(){
        return length;
    }

    public String solution(){
        return plainText;
    }
}