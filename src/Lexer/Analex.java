package Lexer;

public class Analex {
    private Cinta M;
    private Token R;
    private String ac;
    private int pos; // Posición de inicio del lexema del preanalisis(), calculado en el dt().
                     // Use Cinta.getPos() o sea pos=M.getPos();

    public Analex(Cinta c) {
        M = c;
        R = new Token();
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
                    } else if (cc == 'm' || cc == 'M') {
                        estado = 7;
                    } else if (cc == '+') {
                        estado = 9;
                    } else if (cc == '-') {
                        estado = 10;
                    } else if (cc == '/') {
                        estado = 13;
                    } else if (cc == 'd' || cc == 'D') {
                        estado = 14;
                    } else if (digito(cc)) {
                        estado = 16;
                    } else if (cc == '.') {
                        estado = 19;
                    } else if (letraSinMD(cc)) {
                        estado = 21;
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
                    if (cc == 'o' || cc == 'O') {
                        estado = 8;
                        M.avanzar();
                        ac += cc;
                    } else if (letraSinO(cc)) {
                        estado = 21;
                        M.avanzar();
                        ac += cc;
                    } else {
                        estado = 22;
                    }
                    break;
                case 8:
                    if (cc == 'd' || cc == 'D') {
                        estado = 6;
                        M.avanzar();
                        ac += cc;
                    } else if (letraSinD(cc)) {
                        estado = 21;
                        M.avanzar();
                        ac += cc;
                    } else {
                        estado = 22;
                    }
                    break;
                case 9:
                    R.set(Token.MAS, 0);
                    return;
                case 10:
                    if (cc == '>') {
                        estado = 12;
                        M.avanzar();
                        ac += cc;
                    } else {
                        estado = 11;
                    }
                    break;
                case 11:
                    R.set(Token.MENOS, 0);
                    return;
                case 12:
                    R.set(Token.ASSIGN, 0);
                    return;
                case 13:
                    R.set(Token.DIV, 0);
                    return;
                case 14:
                    if (cc == 'i' || cc == 'I') {
                        estado = 15;
                        M.avanzar();
                        ac += cc;
                    } else if (letraSinI(cc)) {
                        estado = 21;
                        M.avanzar();
                        ac += cc;
                    } else {
                        estado = 22;
                    }
                    break;
                case 15:
                    if (cc == 'v' || cc == 'V') {
                        estado = 13;
                        M.avanzar();
                        ac += cc;
                    } else if (letraSinV(cc)) {
                        estado = 21;
                        M.avanzar();
                        ac += cc;
                    } else {
                        estado = 22;
                    }
                    break;
                case 16:
                    if (digito(cc)) {
                        estado = 16;
                        M.avanzar();
                        ac += cc;
                    } else if (cc == '.') {
                        estado = 18;
                        M.avanzar();
                        ac += cc;
                    } else {
                        estado = 17;
                    }
                    break;
                case 17:
                    R.set(Token.NUM, Integer.parseInt(ac));
                    return;
                case 18:
                    if (digito(cc)) {
                        estado = 18;
                        M.avanzar();
                        ac += cc;
                    } else {
                        estado = 20;
                    }
                    break;
                case 19:
                    if (digito(cc)) {
                        estado = 18;
                        M.avanzar();
                        ac += cc;
                    } else {
                        estado = 2; // error
                    }
                    break;
                case 20:
                    R.set(Token.NUMR, Float.parseFloat(ac));
                    return;
                case 21:
                    if (letra(cc) || digito(cc)) {
                        estado = 21;
                        M.avanzar();
                        ac += cc;
                    } else {
                        estado = 22;
                    }
                    break;
                case 22:
                    R.set(Token.ID, 0); // pts = posicion en la tsVar
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

    // ------------------------------------------------------------------------------
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

    private boolean letraSinMD(char cc) {
        return letra(cc) && cc != 'm' && cc != 'M' && cc != 'd' && cc != 'D';
    }

    private boolean letraSinO(char cc) {
        return letra(cc) && cc != 'o' && cc != 'O';
    }

    private boolean letraSinD(char cc) {
        return letra(cc) && cc != 'd' && cc != 'D';
    }

    private boolean letraSinI(char cc) {

        return letra(cc) && cc != 'i' && cc != 'I';
    }

    private boolean letraSinV(char cc) {
        return letra(cc) && cc != 'v' && cc != 'V';

    }
}
