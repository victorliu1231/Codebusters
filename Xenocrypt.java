public class Xenocrypt extends Cipher{
    private String str;
    private Key key;
    private String crypto;

    public Xenocrypt(String str){
        super(str);
        this.str = str;
        key = super.key();
        crypto = generateCryptogram();
    }

    public Xenocrypt(String str, int seed){
        super(str, seed);
        this.str = str;
        key = super.key();
        crypto = generateCryptogram();
    }

    public String generateCryptogram(){
        String ans = "";
        System.out.println(key);
        for (int i = 0; i < str.length(); i++){
            if (!key.plaintextL().contains(str.substring(i,i+1)) && !key.plaintextU().contains(str.substring(i,i+1))){ //if the letter isn't a letter (ex: ".;, ", etc)
                ans+= " ";
                System.out.println("plainLetter: "+str.charAt(i)+", cipherLetter: "+str.charAt(i));
            } else {
                String letter = str.substring(i,i+1);
                ans+= key.getCipherForPlain(letter);
                System.out.println("plainLetter: "+str.charAt(i)+", cipherLetter: "+key.getCipherForPlain(letter));
            }
        }
        return ans;
    }

    public String toString(){
        return crypto;
    }
}