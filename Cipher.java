public class Cipher{
    private String plainText;
    private int length;
    private Key key;

    public Cipher(String str){
        plainText = str;
        length = str.length();
        key = new Key();
        System.out.println(key);
    }

    public String toString(){
        return plainText;
    }
}