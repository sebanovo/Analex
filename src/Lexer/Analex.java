package Lexer;

import java.util.HashMap;

public class Analex {
    private Cinta M;
    private Token R;
    private String ac;
    private int pos; // Posición de inicio del lexema del preanalisis(), calculado en el dt().
                     // Use Cinta.getPos() o sea pos=M.getPos();

    private HashMap<String, Integer> TPC;
    private TSVAR TS;

    public TSVAR getTS() {
        return TS;
    }

    public Analex(Cinta c) {
        M = c;
        R = new Token();

        TPC = new HashMap<>();
        TPC.put("div", Token.DIV);
        TPC.put("mod", Token.MOD);

        TS = new TSVAR();

        init();
    }

    public final void init() {
        // M.init();
        avanzar(); // Calcular el primer preanalisis.
    }

    public Token Preanalisis() {
        return R;
    }

    public String lexema() {
        return ac;
    }

    public void avanzar() {
        dt();
    }

    private void dt() {
        int estado = 0;
        ac = "";
        while (true) {
            char cc = M.cc();
            switch (estado) {
                case 0:
                    if (cc == Cinta.EOF) {
                        estado = 1;
                        continue;
                    }
                    pos = M.getPos();
                    M.avanzar();
                    if (espacio(cc)) {
                        estado = 0;
                        continue;
                    }
                    ac += cc;

                    if (cc == '(') {
                        estado = 3;
                    } else if (cc == ')') {
                        estado = 4;
                    } else if (cc == '*') {
                        estado = 5;
                    } else if (cc == '%') {
                        estado = 6;
                    } else if (cc == '+') {
                        estado = 7;
                    } else if (cc == '-') {
                        estado = 8;
                    } else if (cc == '/') {
                        estado = 10;
                    } else if (digito(cc)) {
                        estado = 11;
                    } else if (cc == '.') {
                        estado = 13;
                    } else if (letra(cc)) {
                        estado = 14;
                    } else {
                        estado = 2;
                    }
                    break;
                case 1:
                    R.set(Token.FIN, 0);
                    return;
                case 2:
                    R.set(Token.ERROR, 0);
                    return;
                case 3:
                    R.set(Token.PA, 0);
                    return;
                case 4:
                    R.set(Token.PC, 0);
                    return;
                case 5:
                    R.set(Token.POR, 0);
                    return;
                case 6:
                    R.set(Token.MOD, 0);
                    return;
                case 7:
                    R.set(Token.MAS, 0);
                    return;
                case 8:
                    if (cc == '>') {
                        estado = 9;
                        M.avanzar();
                        ac += cc;
                    } else {
                        estado = 18;
                    }
                    break;
                case 9:
                    R.set(Token.ASSIGN, 0);
                    return;
                case 10:
                    R.set(Token.DIV, 0);
                    return;
                case 11:
                    if (digito(cc)) {
                        estado = 11;
                        M.avanzar();
                        ac += cc;
                    } else if (cc == '.') {
                        estado = 12;
                        M.avanzar();
                        ac += cc;
                    } else {
                        estado = 15;
                    }
                    break;
                case 12:
                    if (digito(cc)) {
                        estado = 12;
                        M.avanzar();
                        ac += cc;
                    } else {
                        estado = 16;
                    }
                    break;
                case 13:
                    if (digito(cc)) {
                        estado = 12;
                        M.avanzar();
                        ac += cc;
                    } else {
                        estado = 2; // error
                    }
                    break;
                case 14:
                    if (letra(cc) || digito(cc)) {
                        estado = 14;
                        M.avanzar();
                        ac += cc;
                    } else {
                        estado = 17;
                    }
                    break;
                case 15:
                    R.set(Token.NUM, Integer.parseInt(ac));
                    return;
                case 16:
                    R.set(Token.NUMR, Float.parseFloat(ac));
                    return;
                case 17:
                    String lexemaLower = ac.toLowerCase();

                    if (TPC.containsKey(lexemaLower)) {
                        R.set(TPC.get(lexemaLower), 0);
                    } else {
                        int posTS = TS.add(ac);
                        R.set(Token.ID, posTS);
                    }
                    return;
                case 18:
                    R.set(Token.MENOS, 0);
                    return;
                default:
                    continue;
            }
        }
    }

    public void resaltar() { // Para resaltar el lexema del Preanalisis en el progFuente.
        comunicate(pos, lexema());
    }

    public void comunicate(int pos, String lexema) { // Overridable. Para la Interfaz.
    }

    // validadores
    private boolean espacio(char cc) {
        final int SPACE = 32, TAB = 9;
        return (cc == Cinta.EOLN || cc == SPACE || cc == TAB);
    }

    private boolean digito(char cc) {
        return ('0' <= cc && cc <= '9');
    }

    private boolean letra(char cc) {
        cc = Character.toUpperCase(cc); // Convertir a mayúsculas, porque es NO case-sensitive.
        return ('A' <= cc && cc <= 'Z');
    }
}
