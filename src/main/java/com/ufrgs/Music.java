package com.ufrgs;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.midi.MidiFileManager;
import org.staccato.StaccatoParser;
import org.jfugue.tools.ComputeDurationForEachTrackTool;
import java.io.File;
import java.io.IOException;

import java.security.Timestamp;
import java.util.List;

// É a música em si, seria a interface do programa com a biblioteca utilizada:
public class Music {

    Pattern pattern;
    Player player;
    double durationSeconds;

    //  cria a música com os padrões dados;
    public Music(Pattern p){
        pattern = p;
        calcDuration();
        player = new Player();
    }
    //Calcula a duração da música a partir do pattern
    public void calcDuration(){
        StaccatoParser parser = new StaccatoParser();
        ComputeDurationForEachTrackTool tool = new ComputeDurationForEachTrackTool();
        parser.addParserListener(tool);
        parser.parse(pattern);
        double[] durationsOfEachVoice = tool.getDurations();
        durationSeconds = durationsOfEachVoice[0];
    }
    
    // Salva o arquivo MIDI que corresponde ao caminho dado
    public void saveMusicMIDI(String pathTo) throws IOException {
        File file = new File(pathTo);
        MidiFileManager.savePatternToMidi(pattern, file );
    }
    //  Pausa a música ou a retoma;
    public void Pause(){
        if (player.getManagedPlayer().isStarted()) {
            if (player.getManagedPlayer().isPaused()) {
                player.getManagedPlayer().resume();
            } else if (!player.getManagedPlayer().isFinished()) {
                player.getManagedPlayer().pause();
            }
        } else {
            player.play(pattern);
        }
    }
    // Diz qual tempo estamos na música;
    public Timestamp getTime(){return null;}
}
