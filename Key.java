import java.util.Random;
import java.util.ArrayList;

public class Key{
    private String plaintextL = "abcdefghijklmnopqrstuvwxyz\u00e1\u00e9\u00ED\u00F3\u00FA\u00FC\u00F1";
    private String plaintextU = "ABCDEFGHIJKLMNOPQRSTUVWXYZ\u00C1\u00c9\u00CD\u00D3\u00DA\u00DC\u00D1";
    private String ciphertextL = "abcdefghijklmnopqrstuvwxyz\u00e1\u00e9\u00ED\u00F3\u00FA\u00FC\u00F1";
    private String ciphertextU = "ABCDEFGHIJKLMNOPQRSTUVWXYZ\u00C1\u00c9\u00CD\u00D3\u00DA\u00DC\u00D1";
    private ArrayList<String> aryPtxtLower = new ArrayList<>();
    private ArrayList<String> aryPtxtUpper = new ArrayList<>();
    private ArrayList<String> aryCtxtLower = new ArrayList<>();
    private ArrayList<String> aryCtxtUpper = new ArrayList<>();
    public long seed;
    
    public Key(){
        ciphertextL = randomizeStr(ciphertextL);
        ciphertextU = randomizeStr(ciphertextU);
        arrayListTheString(aryPtxtLower, plaintextL);
        arrayListTheString(aryPtxtUpper, plaintextU);
        arrayListTheString(aryPtxtLower, ciphertextL);
        arrayListTheString(aryCtxtUpper, ciphertextU);
        seed = System.currentTimeMillis() % 100000;
    }

    public Key(int seed){
        ciphertextL = randomizeStr(ciphertextL);
        ciphertextU = randomizeStr(ciphertextU);
        arrayListTheString(aryPtxtLower, plaintextL);
        arrayListTheString(aryPtxtUpper, plaintextU);
        arrayListTheString(aryPtxtLower, ciphertextL);
        arrayListTheString(aryCtxtUpper, ciphertextU);
        this.seed = seed;
    }

    private void arrayListTheString(ArrayList<String> ary, String str){
        for (int i = 0; i < str.length(); i++){
            ary.add(str.substring(i,i+1));
        }
    }

    private String randomizeStr(String str){
        Random r = new Random(seed);
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

    public char getCipherForPlain(String plainLetter){
        if (plaintextL.contains(plainLetter)){ //if lowercase
            int index = plaintextL.indexOf(plainLetter);
            return ciphertextL.charAt(index);
        }
        else {//if uppercase
            int index = plaintextU.indexOf(plainLetter);
            //System.out.println("plainLetter: "+plainLetter+", index: "+index);
            return ciphertextU.charAt(index);
        }
    }

    public String plaintextL(){
        return plaintextL;
    }

    public String plaintextU(){
        return plaintextU;
    }

    public String ciphertextL(){
        return ciphertextL;
    }

    public String ciphertextU(){
        return ciphertextU;
    }

    //sees if the given guessed letter is correct
    //pre-condition: guess and cipher have to be both uppercase or both lowercase
    public boolean doesMatch(String guess, char cipher){
        if (plaintextL.contains(guess)){ //if lowercase
            int pair = plaintextL.indexOf(guess);
            char guessedCipherLetter = ciphertextL.charAt(pair);
            return guessedCipherLetter == cipher;
        }
        else {//if uppercase
            int pair = plaintextU.indexOf(guess);
            char guessedCipherLetter = ciphertextU.charAt(pair);
            return guessedCipherLetter == cipher;
        }
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