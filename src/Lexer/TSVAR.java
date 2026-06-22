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
 * 
 * @author ginobarroso@uagrm.edu.bo
 */
public class TSVAR {
    private ArrayList<String> nombres;
    private ArrayList<Float> valores;

    public TSVAR() {
        nombres = new ArrayList<>();
        valores = new ArrayList<>();
    }

    /**
     * - Si nombre ya existe en la tabla, devolver (return) su posición (indice).
     * - Si nombre no existe, insertar este en la tabla y devolver la posición donde
     * fue insertado.
     * 
     * @param nombre nombre del ID en la TSVAR
     * @return pTS posicion de un ID en la TSVAR
     */
    public int add(String nombre) {
        for (int i = 0; i < nombres.size(); i++) {
            if (nombres.get(i).equalsIgnoreCase(nombre)) {
                return i;
            }
        }

        nombres.add(nombre);
        valores.add(0f);
        return nombres.size() - 1;
    }

    /**
     * Dada la posición pTS de un ID, devolver su valor. Por ejemplo, usando
     * la Tabla de arriba: getValor(0)=15, getValor(1)=0, getValor(2)=60.45
     * 
     * @param pTS posicion de un ID en la TSVAR
     * @return valor del ID en la TSVAR
     */
    public float getValor(int pTS) {
        return valores.get(pTS);
    }

    /**
     * Dada la posición pTS de un ID, modifica su valor. Por ejemplo, usando la
     * Tabla de arriba: setValor(1, 3.1416), modificará el valor de la variable que
     * se encuentra en la posición 1 ("a1BC") a 3.1416.
     * 
     * @param pTS   posicion de un ID en la TSVAR
     * @param valor valor del ID de la TSVAR
     */
    public void setValor(int pTS, float valor) {
        /*
         */
        valores.set(pTS, valor);
    }
}
