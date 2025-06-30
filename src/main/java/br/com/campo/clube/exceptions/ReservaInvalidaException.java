package br.com.campo.clube.exceptions;

public class ReservaInvalidaException extends RuntimeException{
    public ReservaInvalidaException(String mensagem){
        super(mensagem);
    }
}
