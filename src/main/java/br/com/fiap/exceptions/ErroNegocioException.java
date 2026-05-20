package br.com.fiap.exceptions;

public class ErroNegocioException extends Exception {

    public ErroNegocioException(String mensagem) {
        super(mensagem);
    }
}