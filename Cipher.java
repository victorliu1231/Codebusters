public class Cipher{
    private String plainText;
    private int length;
    private Key key;

    public Cipher(String str){
        plainText = str;
        length = plainText.length();
        key = new Key(14355);
        System.out.println(key.seed);
        System.out.println(key);
        System.out.println(key.doesMatch("k", 'z')); //should be true
        System.out.println(key.doesMatch("k", 'a')); //should be false
    }

    public String toString(){
        return plainText;
    }
}