package com.ufrgs;

import org.jfugue.pattern.Pattern;

import java.util.List;

// Classe que cuida do processo da geração da música através de um texto;
public class MusicFactory {
    // Gera a Musica correspondente através do texto passado;
    public static Music createMusic(String textMusic){return null;}
    // Salva a musica no arquivo que corresponde ao caminho dado
    public static void SaveMusic(Music m, String pathTo){}
    // Gera lista de padrões Musicais correspondente ao texto passado
    private static List<Pattern> convertStringToPatterns(String textMusic){return null; }

}
