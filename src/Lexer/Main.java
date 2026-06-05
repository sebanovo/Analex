package Lexer;

public class Main {
   
    public static void main(String[] args) {
       for (int i=Token.FIN; i <= Token.ID; i++){
            Token t = new Token(i, 0);
            System.out.println(i + "=" +t);
        }
    }
    
}
