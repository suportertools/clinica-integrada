package br.com.clinicaintegrada.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SalvaArquivos {

    private final byte[] bytes;
    private final String nomeArquivo;
    private final boolean verifica;
    private File file;

    public SalvaArquivos(byte[] bytes, String nomeArquivo, boolean verifica) {
        this.bytes = bytes;
        this.nomeArquivo = nomeArquivo;
        this.verifica = verifica;
    }

    public void salvaNaPasta(String caminho) {
        try {
            File fl = new File(caminho + "/" + nomeArquivo);
            file = fl;
            FileOutputStream out = new FileOutputStream(fl);
            out.write(bytes);
            out.flush();
            out.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void remover() {
        if (file.exists()) {
            file.delete();
            file = null;
        }
    }
}
