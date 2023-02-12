package com.ufrgs;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import java.security.Timestamp;
import java.util.List;

// É a música em si, seria a interface do programa com a biblioteca utilizada:
public class Music {
    List<Pattern> patterns;
    Player player;
    //  cria a música com os padrões dados;
    Music(List<Pattern> patterns){}
    // Começa a tocar a música;
    void Start(){}
    // Salva a musica no arquivo que corresponde ao caminho dado
    public void saveMusic(String pathTo){}
    //  Pausa a música;
    void Pause(){}
    // Diz qual tempo estamos na música;
    Timestamp getTime(){return null;}
}
