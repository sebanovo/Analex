package Lexer;

import java.util.ArrayList;

/**
 * Esta class Implementa la tabla que almacena las variables (ID's) con su
 * valor.
 * Por ejemplo:
 * 
 * NOMBRE VALOR
 * 0 |"X1" | 15 |
 * 1 |"a1BC" | 0 |
 * 2 |"Base" | 60.45 |
 */
public class TSVAR {
    private ArrayList<String> nombres;
    private ArrayList<Float> valores;

    public TSVAR() {
        nombres = new ArrayList<>();
        valores = new ArrayList<>();
    }

    // Los servicios (métodos public), que minimamente se necesitan son:
    public int add(String nombre) {
        /*
         * -Si nombre ya existe en la tabla, devolver (return) su posición (indice).
         * -Si nombre no existe, insertar este en la tabla y devolver la posición donde
         * fue insertado.
         */
        for (int i = 0; i < nombres.size(); i++) {
            if (nombres.get(i).equalsIgnoreCase(nombre)) {
                return i;
            }
        }

        nombres.add(nombre);
        valores.add(0f);
        return nombres.size() - 1;

    }

    public float getValor(int pTS) {
        /*
         * Dada la posición pTS de un ID, devolver su valor. Por ejemplo, usando
         * la Tabla de arriba: getValor(0)=15, getValor(1)=0, getValor(2)=60.45
         */ return valores.get(pTS);
    }

    public void setValor(int pTS, float valor) {
        /*
         * Dada la posición pTS de un ID, modifica su valor. Por ejemplo, usando
         * la Tabla de arriba: setValor(1, 3.1416), modificará el valor de la variable
         * que se encuentra en la posición 1 ("a1BC") a 3.1416.
         */
        valores.set(pTS, valor);
    }

}
