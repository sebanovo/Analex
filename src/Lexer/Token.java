package Lexer;

import java.util.HashMap;
import java.util.Map;

public class Token {
    // @formatter:off
    //Para el NOMBRE del token
    public static final int FIN    = 0;
    public static final int ERROR  = 1;
    public static final int PC     = 3;    // "("
    public static final int PA     = 2;    // ")"
    public static final int POR    = 4;    // "*"
    public static final int MOD    = 5;    // "%" y "mod"
    public static final int MAS    = 6;    // "+"
    public static final int MENOS  = 7;    // "-"
    public static final int DIV    = 8;    // "/" y "div"    
    public static final int ASSIGN = 9;    // "->" 
    
    public static final int NUM    = 10;
    public static final int NUMR   = 11;
    public static final int ID     = 12;
    public static final int MAIN   = 13;
    // @formatter:on

    // Campos de la class: <nom, atr>
    private int nom;
    private float atr;

    public Token() {
        this(FIN, 0);
    }

    public Token(int nombre) {
        this(nombre, 0);
    }

    public Token(int nombre, int atributo) {
        set(nombre, atributo);
    }

    public void set(int nombre, int atributo) {
        setNom(nombre);
        setAtr(atributo);
    }

    public void set(int nombre, float atributo) {
        setNom(nombre);
        setAtr(atributo);
    }

    public void setNom(int nom) {
        this.nom = nom;
    }

    public void setAtr(float atr) {
        if (nom != NUMR)
            atr = (int) atr;

        this.atr = atr;
    }

    public int getNom() {
        return nom;
    }

    public float getAtr() {
        return atr;
    }

    @Override
    public String toString() {
        return "<" + get(NOMtokenSTR, nom) + "," + atrToString(nom) + ">";
    }

    private String atrToString(int nom) { // Devuelve el atributo del token nom.
        if (FIN <= nom && nom <= ASSIGN)
            return "_";

        if (nom != NUMR)
            return "" + (int) atr;

        return "" + atr;
    }

    private String get(String v[], int i) {
        try {
            return v[i];
        } catch (Exception e) {
            return DESCONOCIDO;
        }
    }

    private static final String NOMtokenSTR[] = {
            "FIN", "ERROR", "PA", "PC", "POR",
            "MOD", "MAS", "MENOS", "DIV", "ASSIGN",
            "NUM", "NUMR", "ID", "MAIN"
    };

    private static final String DESCONOCIDO = "??";
}
