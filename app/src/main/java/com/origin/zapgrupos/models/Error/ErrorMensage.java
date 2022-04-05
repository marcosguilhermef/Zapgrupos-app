package com.origin.zapgrupos.models.Error;

public class ErrorMensage {
    private String mensagem;
    private int code;

    public ErrorMensage(Integer cod){
        code = cod;
    }

    public ErrorMensage(String msg, int value){
        mensagem = msg;
        code = value;
    }

    public String getMensagem() {
        return mensagem;
    }

    public int getCode() {
        return code;
    }
}
