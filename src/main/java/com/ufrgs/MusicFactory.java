package com.ufrgs;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import java.util.List;

// Classe que cuida do processo da geração da música através de um texto;
public class MusicFactory {
    private static  final Mapping map;
    private static int instrument=0; //MIDI instrument https://fmslogo.sourceforge.io/manual/midi-instrument.html
    private static  int volume =40; //volume from 0 to 127
    private static int octave = 5; //default octave

    static {
        try {
            map = new Mapping();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MusicFactory(){
    }

    // Gera a Musica correspondente através do texto passado;
    public static Music createMusic(String textMusic){
        String command;
        char lastRead='a';

        Pattern pattern = new Pattern();
        pattern.add(":CON(7"+String.valueOf(volume)+")"); //https://fmslogo.sourceforge.io/manual/midi-table.html

        for(char c : textMusic.toCharArray()) {
            command = map.getCommand(String.valueOf(c));
            String sel[];
            if (!command.equals("")){
                sel = command.split(":");
                if (sel[0].equals("N")){
                    pattern.add(sel[1]);
                }else if(sel[0].equals("I")){
                    pattern.setInstrument(Integer.valueOf(sel[1]));
                    instrument = Integer.valueOf(sel[1]);
                }else if(sel[0].equals("IM")){
                    pattern.setInstrument(instrument+Integer.valueOf(sel[1]));
                }else if(sel[0].equals("O")){//só funciona nas notas que vem antes e só uma vez CONSERTAR
                    setOctave(octave+1);
                    pattern.addToEachNoteToken(String.valueOf(octave));
                }
                else{
                    setVolume(volume*2);
                    pattern.add(":CON(7"+String.valueOf(volume)+")");
                }
            } //se for caracter definido
            else{//se for qualquer caracter não definido ou a-g
                if((int)lastRead>=65 || (int)lastRead<=71){//se ultimo caracter foi nota, coloca ele de novo
                    command = map.getCommand(String.valueOf(lastRead));
                    sel = command.split(":");
                    pattern.add(sel[1]);
                }
            }
            lastRead=c;
        }

        /*System.out.println(pattern.getPattern());
        Player player = new Player();
        player.play(pattern);*/ //DEBUG
        return null;}
    // Gera lista de padrões Musicais correspondente ao texto passado


    public static void setVolume(int volume) {
        if(volume>127){
            volume = 40;
        }

        MusicFactory.volume = volume;
    }

    public static void setOctave(int octave) {
        if (octave>10 || octave<1){
            octave = 5;
        }
        MusicFactory.octave = octave;
    }
}
