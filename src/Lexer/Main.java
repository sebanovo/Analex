package Lexer;

public class Main {

    public static void main(String[] args) {
        // ejecutar("x1 -> 15");
        // ejecutar("base -> 60.45");
        // // ejecutar("x1 + base");
        // ejemploVariables();
        token();
    }

    private static void token() {
        Token R = new Token();
        R.set(Token.NUMR, (float) 12.0);
        System.out.println(R.getAtr());
        System.out.println(R.toString());

        R.set(Token.NUM, 40);
        System.out.println(R.getAtr());
        System.out.println(R.toString());
    }

    public static void ejemploVariables() {
        Cinta cinta = new Cinta("60.45 -> Base");
        Analex analex = new Analex(cinta);
        Parser parser = new Parser(analex);
        System.out.println(parser.ejecutar());

        cinta.init("15 -> x1");
        analex.init();
        System.out.println(parser.ejecutar());

        cinta.init("(Base - (200 mod 12) * -10.84)/x1 -> a1BC");
        analex.init();
        System.out.println(parser.ejecutar());

        cinta.init("a1BC");
        analex.init();
        System.out.println(parser.ejecutar());
    }

    public static void calcular() {

        // String entrada = "5 + 3 * 2";
        // String entrada = "(5 + 3) * 2";
        String entrada = "10 + 4 * 3 mod 5";
        // String entrada = "10 mod 3";
        // String entrada = "5 + * 2";
        Cinta cinta = new Cinta(entrada);
        Analex analex = new Analex(cinta);
        Parser parser = new Parser(analex);

        float resultado = parser.evaluar();

        System.out.println("Entrada: " + entrada);
        System.out.println("Resultado: " + resultado);
    }

    public static void pruebaAnalex() {
        String fuente = "DIV div DiV MOD mod MoD x1 X1 Base BASE";

        Cinta cinta = new Cinta(fuente);
        Analex analex = new Analex(cinta);

        while (analex.Preanalisis().getNom() != Token.FIN) {

            System.out.println(
                    "Lexema: " + analex.lexema() +
                            "  Token: " + analex.Preanalisis());

            analex.avanzar();
        }
    }

    public static void listar() {
        for (int i = Token.FIN; i <= Token.ID; i++) {
            Token t = new Token(i, 0);
            System.out.println(i + "=" + t);
        }
    }
}
