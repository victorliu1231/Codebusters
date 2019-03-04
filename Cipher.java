public class Cipher{
    private String plainText;
    private int length;
    private Keyy key;

    public Cipher(String str){
        plainText = str;
        length = plainText.length();
        key = new Keyy();
    }

    public Cipher(String str, int seed){
        plainText = str;
        length = plainText.length();
        key = new Keyy(seed);
    }

    public Keyy key(){
        return key;
    }

    public int length(){
        return length;
    }

    public String solution(){
        String ans = "";
        for (int i = 0; i < plainText.length(); i++){
            ans+= plainText.charAt(i);
        }
        return ans;
    }
}