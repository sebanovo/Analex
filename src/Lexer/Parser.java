/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lexer;

import Lexer.Analex;
import Lexer.Token;

/**
 *
 * @author HP
 */
public class Parser {
    private Analex analex;

    public Parser(Analex analex) {
        this.analex = analex;
    }

    public float evaluar() {
        float resultado = expr();

        if (analex.Preanalisis().getNom() != Token.FIN) {
            throw new RuntimeException("Token inesperado: " + analex.lexema());
        }

        return resultado;
    }

    private float expr() {
        float resultado = term();

        while (analex.Preanalisis().getNom() == Token.MAS ||
                analex.Preanalisis().getNom() == Token.MENOS) {

            int token = analex.Preanalisis().getNom();
            analex.avanzar();

            float valor = term();

            if (token == Token.MAS)
                resultado += valor;
            else
                resultado -= valor;
        }

        return resultado;
    }

    private float term() {
        float resultado = factor();

        while (analex.Preanalisis().getNom() == Token.POR ||
                analex.Preanalisis().getNom() == Token.DIV ||
                analex.Preanalisis().getNom() == Token.MOD) {

            int token = analex.Preanalisis().getNom();
            analex.avanzar();

            float valor = factor();

            if (token == Token.POR)
                resultado *= valor;
            else if (token == Token.DIV)
                resultado /= valor;
            else
                resultado %= valor;
        }

        return resultado;
    }

    private float factor() {
        Token t = analex.Preanalisis();

        if (t.getNom() == Token.MENOS) {
            analex.avanzar();
            return -factor();
        }

        if (t.getNom() == Token.NUM || t.getNom() == Token.NUMR) {
            float valor = Float.parseFloat(analex.lexema());
            analex.avanzar();
            return valor;
        }

        if (t.getNom() == Token.ID) {
            float valor = analex.getTS().getValor((int) t.getAtr());
            analex.avanzar();
            return valor;
        }

        if (t.getNom() == Token.PA) {
            analex.avanzar();
            float valor = expr();

            if (analex.Preanalisis().getNom() != Token.PC) {
                throw new RuntimeException("Falta cerrar paréntesis");
            }

            analex.avanzar();
            return valor;
        }

        throw new RuntimeException("Se esperaba número, variable o paréntesis: " + analex.lexema());
    }

    public float ejecutar() {
        float resultado = expr();

        if (analex.Preanalisis().getNom() == Token.ASSIGN) {
            analex.avanzar();

            if (analex.Preanalisis().getNom() != Token.ID) {
                throw new RuntimeException("Después de -> se esperaba una variable");
            }

            float posTS = analex.Preanalisis().getAtr();
            analex.getTS().setValor((int) posTS, resultado);
            analex.avanzar();
        }

        if (analex.Preanalisis().getNom() != Token.FIN) {
            throw new RuntimeException("Token inesperado: " + analex.lexema());
        }

        return resultado;
    }
}