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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

// É a música em si, seria a interface do programa com a biblioteca utilizada:
public class Music {

    Pattern pattern;
    Player player;

    AtomicBoolean firstTime = new AtomicBoolean(true);

    AtomicBoolean running = new AtomicBoolean(true);

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
        if (firstTime.get()) {
            firstTime.set(false);
            executorService.submit(() -> {
                try {
                    player.getManagedPlayer().start(player.getSequence(pattern));
                } catch (InvalidMidiDataException | MidiUnavailableException e) {
                    throw new RuntimeException(e);
                }
                long nanoStart = System.nanoTime();
                boolean newStart = true;

                while(!player.getManagedPlayer().isFinished() && running.get()) {
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
                firstTime.set(true);
            });
        } else {
            pause.set(!pause.get());
        }
    }
    // Diz qual tempo estamos na música;
    public Long getTime(){return nanoSecondsPlayed.get();}

    public void stop(){
        running.set(false);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        running.set(false);
    }
}
