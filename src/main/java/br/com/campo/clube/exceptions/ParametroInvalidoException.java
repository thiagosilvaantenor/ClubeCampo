package br.com.campo.clube.exceptions;

public class ParametroInvalidoException extends RuntimeException{
    public ParametroInvalidoException(String mensagem){
        super(mensagem);
    }
}
