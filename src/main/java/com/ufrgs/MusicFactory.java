package com.ufrgs;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import java.util.List;

// Classe que cuida do processo da geração da música através de um texto;
public class MusicFactory {
    private static int instrument=0; //MIDI instrument https://fmslogo.sourceforge.io/manual/midi-instrument.html
    private static  int volume =20; //volume from 0 to 127
    private static int octave = 5; //default octave

    public MusicFactory(){
    }

    // Gera lista de padrões Musicais correspondente ao texto passado
    public static Pattern createPattern(String text, Mapping map){
        String command;
        char lastRead='a';
        char lastType='X';

        Pattern pattern = new Pattern();
        pattern.add(":CON(7"+String.valueOf(volume)+")"); //https://fmslogo.sourceforge.io/manual/midi-table.html

        for(char c : text.toCharArray()) {
            command = map.getCommand(String.valueOf(c));
            String sel[];
            if (!command.equals("")){//se for caracter definido no arquivo
                sel = command.split(":");
                lastType =sel[0].charAt(0);
                if (sel[0].equals("N")){
                    //adiciona nota com a oitava especificada
                    pattern.add(sel[1]+octave);
                }else if(sel[0].equals("I")){
                    //muda instrumento em todas as notas, sempre prevalece o último instrumento adicionado
                    pattern.setInstrument(Integer.valueOf(sel[1]));
                    instrument = Integer.valueOf(sel[1]);
                }else if(sel[0].equals("IM")){
                    pattern.setInstrument(instrument+Integer.valueOf(sel[1]));
                }else if(sel[0].equals("O")){
                    //todas notas depois estão em uma oitava diferente
                    setOctave(octave+1);
                }
                else{
                    // todas notas depois estão em volume diferente
                    setVolume(volume*2);
                    pattern.add(":CON(7"+String.valueOf(volume)+")");
                }
            }
            else{//se for qualquer caracter não definido pelo arquivo ou a-g
                if(lastType == 'N'){//se ultimo caracter foi nota, coloca ele de novo
                    command = map.getCommand(String.valueOf(lastRead));
                    sel = command.split(":");
                    pattern.add(sel[1]+octave);
                }else{
                    pattern.add("Rw");
                }
                lastType ='X';
            }
            lastRead=c;
        }

        return pattern;
    }

    // Gera a Musica correspondente através do texto passado;
    public static Music createMusic(String text, Mapping map){
        return new Music(createPattern(text, map));
    }

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
