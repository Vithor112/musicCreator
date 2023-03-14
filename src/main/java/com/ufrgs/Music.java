package com.ufrgs;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.midi.MidiFileManager;
import org.staccato.StaccatoParser;
import org.jfugue.tools.ComputeDurationForEachTrackTool;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.io.File;
import java.io.IOException;

import java.security.Timestamp;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

// É a música em si, seria a interface do programa com a biblioteca utilizada:
public class Music {

    Pattern pattern;
    Player player;

    boolean firstTime = true;

    AtomicLong nanoSecondsPlayed = new AtomicLong(0L);

    ExecutorService executorService = Executors.newSingleThreadExecutor();
    AtomicBoolean pause = new AtomicBoolean(false);

    //  cria a música com os padrões dados;
    public Music(Pattern p){
        pattern = p;
        calcDuration();
        player = new Player();
    }
    //Calcula a duração da música a partir do pattern
    public double calcDuration(){
        StaccatoParser parser = new StaccatoParser();
        ComputeDurationForEachTrackTool tool = new ComputeDurationForEachTrackTool();
        parser.addParserListener(tool);
        parser.parse(pattern);
        double[] durationsOfEachVoice = tool.getDurations();
        return durationsOfEachVoice[0];
    }

    // Salva o arquivo MIDI que corresponde ao caminho dado
    public void saveMusicMIDI(String pathTo) throws IOException {
        File file = new File(pathTo);
        MidiFileManager.savePatternToMidi(pattern, file );
    }
    //  Pausa a música ou a retoma;
    public void Pause(){
        if (firstTime) {
            firstTime = false;
            executorService.submit(() -> {
                try {
                    player.getManagedPlayer().start(player.getSequence(pattern));
                } catch (InvalidMidiDataException | MidiUnavailableException e) {
                    throw new RuntimeException(e);
                }
                long nanoStart = System.nanoTime();
                boolean newStart = true;

                while(!player.getManagedPlayer().isFinished()) {
                    if (!pause.get()) {
                        player.getManagedPlayer().resume();
                        long nanoPlayed = System.nanoTime();
                        if (newStart) {
                            nanoStart = System.nanoTime() - nanoSecondsPlayed.get();
                            newStart = false;
                        }
                        nanoSecondsPlayed.set(nanoPlayed - nanoStart);
                    } else {
                        newStart = true;
                        player.getManagedPlayer().pause();
                    }
                }
            });
        } else {
            pause.set(!pause.get());
        }
    }
    // Diz qual tempo estamos na música;
    public Long getTime(){return nanoSecondsPlayed.get();}

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        executorService.shutdownNow();
    }
}
