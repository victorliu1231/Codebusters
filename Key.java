import java.util.Random;
import java.util.ArrayList;

public class Key{
    private String plaintextL = "abcdefghijklmnopqrstuvwxyz\u00e1\u00e9\u00ED\u00F3\u00FA\u00FC\u00F1";
    private String plaintextU = "ABCDEFGHIJKLMNOPQRSTUVWXYZ\u00C1\u00c9\u00CD\u00D3\u00DA\u00DC\u00D1";
    private String ciphertextL = "abcdefghijklmnopqrstuvwxyz\u00e1\u00e9\u00ED\u00F3\u00FA\u00FC\u00F1";
    private String ciphertextU = "ABCDEFGHIJKLMNOPQRSTUVWXYZ\u00C1\u00c9\u00CD\u00D3\u00DA\u00DC\u00D1";
    
    public Key(){
        ciphertextL = randomizeStr(ciphertextL);
        ciphertextU = randomizeStr(ciphertextU);
    }

    private String randomizeStr(String str){
        Random r = new Random();
        ArrayList<String> chars = new ArrayList<>();
        for (int i = 0; i < str.length(); i++){
            chars.add(str.substring(i,i+1));
        }
        String ans = "";
        for (int i = 0; i < str.length(); i++){
            String c = chars.get(Math.abs(r.nextInt(chars.size())));
            if (c.equals(str.substring(i,i+1))){
                c = chars.get(Math.abs(r.nextInt(chars.size()))); //randomizes char again in case the ciphertext and plaintext letters match to each other
            }
            chars.remove(c);
            ans+= c;
        }
        return ans;
    }

    public String toString(){
        String[] ans = new String[4];
        ans[0] = plaintextL;
        ans[1] = plaintextU;
        ans[2] = ciphertextL;
        ans[3] = ciphertextU;
        String print = "[";
        for (int i = 0; i < ans.length; i++){
            if (i == 1 || i == 3){
                print+= ", ";
            }
            if (i == 2){
                print+= "]\n[";
            }
            print+= ans[i];
        }
        print+= "]";
        return print;
    }
}