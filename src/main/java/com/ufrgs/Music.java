package com.ufrgs;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.midi.MidiFileManager;
import java.io.File;
import java.io.IOException;

import java.security.Timestamp;
import java.util.List;

// É a música em si, seria a interface do programa com a biblioteca utilizada:
public class Music {

    Pattern pattern;
    Player player;
    //  cria a música com os padrões dados;
    public Music(Pattern p){
        pattern = p;
        //System.out.println(pattern.getPattern());
        player = new Player();
    }
    // Começa a tocar a música;
    void start(){
        player.play(pattern);
    }
    // Salva o output da musica no arquivo que corresponde ao caminho dado
    public void saveMusicOutput(String pathTo){}
    // Salva o arquivo MIDI que corresponde ao caminho dado
    public void saveMusicMIDI(String pathTo){
        try {
            File file = new File("teste.midi");
            MidiFileManager.savePatternToMidi(pattern, file );
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    //  Pausa a música;
    public void Pause(){}
    // Diz qual tempo estamos na música;
    public Timestamp getTime(){return null;}
}
