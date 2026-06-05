package Lexer;

public class Analex {
    private Cinta M;
    private Token R;
    private String ac;
    private int pos;        //Posición de inicio del lexema del preanalisis(), calculado en el dt(). 
                            //Use Cinta.getPos() o sea pos=M.getPos();
    
    public Analex(Cinta c){
        M = c;
        R = new Token();
        init();
    }
    
    public final void init(){
        M.init();
        avanzar();      //Calcular el primer preanalisis.
    }
    
    public Token Preanalisis(){
        return R;
    }
    
    public String lexema(){
        return ac;
    }
    
    public void avanzar(){
       dt();
    }
    
    private void dt(){
       int estado = 0;
       ac = "";
       while (true){
        char cc = M.cc();
        switch(estado) {
            case 0:
                if(cc == Cinta.EOF) {
                    estado = 9;
                    continue;
                }
                pos = M.getPos();
                ac += cc;
                M.avanzar();
                if (cc == '+') estado = 1;
                else if(letra(cc)) estado = 6;
                else if(digito(cc)) estado = 6;
                else estado = 8; 
                break;
            case 1:
                if(cc == '+' ){
                    estado = 3;
                    M.avanzar();
                    ac += cc;
                }
                else estado = 2;
                break;
            case 2:
                R.set(Token.MAS, 0);
                break;              
            case 3:
                break;                
            case 4:
                if(digito(cc) || letra(cc)) {
                    estado = 4;
                    M.avanzar();
                    ac += cc;
                }
                else estado = 5;
                break;
            case 5:
                if(Token.keywords.containsKey(ac)) {
                    R.set(Token.keywords.get(ac), 0);
                } else {
                    R.set(Token.ID, -1);
                }
                return;                
            case 6:
                if(digito(cc)) {
                    estado = 6;
                    M.avanzar();
                    ac += cc;
                }
                else estado = 7;
                break;
            case 7:
                R.set(Token.NUM, Integer.parseInt(ac));
                return;
            case 8:
                R.set(Token.ERROR, 0);
                return;
            case 9:
                R.set(Token.FIN, 0);
                return;  
            default:
                continue;
        }
       }
    }
    
    public void resaltar(){    //Para resaltar el lexema del Preanalisis en el progFuente.
        comunicate(pos, lexema());
    }
    
    public void comunicate(int pos, String lexema){ //Overridable. Para la Interfaz.
        
    }
    
//------------------------------------------------------------------------------
    private boolean espacio(char cc){
        final int SPACE=32, TAB=9;
        return (cc == Cinta.EOLN || cc == SPACE || cc== TAB);
    }
    
    private boolean digito (char cc){
        return ('0'<=cc && cc<='9');
    }
    
    private boolean letra(char cc){
        cc = Character.toUpperCase(cc);   //Convertir a mayúsculas, porque es NO case-sensitive.
        return ('A'<=cc && cc<='Z');
    }
}
