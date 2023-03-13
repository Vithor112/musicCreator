package com.ufrgs;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.io.*;

public class Mapping {

    private static final String MAP_FILE = "./src/main/mapping.txt";
    private final HashMap<String, String> mapCharsToJFugueCommand = new HashMap<>();

    private final HashMap<String, String> mapCharsToDescription = new HashMap<>();

    public Mapping() throws IOException {
        initializeFromFile();
        commandToAction();
    }

    // Retorna uma lista com as Strings que mapeaiam pra um comando no map
    public Set<String> getChars(){
        return mapCharsToJFugueCommand.keySet();
    }

    // Retorna uma lista com os comandos JFugue no map
    public Collection<String> getCommands(){
        return mapCharsToJFugueCommand.values();
    }

    // Dado uma string, retorna a string do comando JFugue correspondente ou String vazia caso não esteja presente no map
    public String getCommand(String chars){
        if (mapCharsToJFugueCommand.containsKey(chars)){
            return mapCharsToJFugueCommand.get(chars);
        }
        return "";
    }

    public void initializeFromFile() throws IOException {
        File file = new File(MAP_FILE);
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String st;
        String[] map, sMap;
        while ((st = br.readLine()) != null) {
            map = st.split("=");

            //se a parte a esquerda de "=", que representa o caracter, estiver dividida com "-", significa
            //que todos os caracteres entre (e incluso) aqueles serão mapeados com a função a direita de "="
            if (map[0].contains("-")){

                sMap = map[0].split("-");//parte da esquerda, como exemplo A-G
                int initial = (int) sMap[0].charAt(0);// número do caracter inicial em ASCII (ex. é do A)
                int end = sMap[1].charAt(0);// número do caracter final
                char charat;

                //percorre todos os caracteres até G mapeando eles com o número ASCII, para o código da função
                for (int x=initial;x<end;x++){
                    mapCharsToJFugueCommand.put(String.valueOf((char) x), map[1]+String.valueOf((char) x));
                }
            }else{
                mapCharsToJFugueCommand.put(map[0], map[1]);
            }
        }

    }

    public void commandToAction(){
        mapCharsToDescription.put("A","Nota Lá");
        mapCharsToDescription.put("B","Nota Si");
        mapCharsToDescription.put("C","Nota Dó");
        mapCharsToDescription.put("D","Nota Ré");
        mapCharsToDescription.put("E","Nota Mi");
        mapCharsToDescription.put("F","Nota Fá");
        mapCharsToDescription.put("G","Nota Sol");

        mapCharsToDescription.put("' ' (Espaço) ","Dobra o volume");
        mapCharsToDescription.put("?","Aumenta uma oitava");

        mapCharsToDescription.put("!", "Troca instrumento para Agogo");
        mapCharsToDescription.put("O/o/I/i", "Troca instrumento para Harpsichord");
        mapCharsToDescription.put("\\n", "Troca instrumento para Tubular Bells");
        mapCharsToDescription.put(";", "Troca instrumento para Pan Flute");
        mapCharsToDescription.put(",", "Troca instrumento para Church Organ");
        mapCharsToDescription.put("Dígito", "Troca instrumento para o instrumento MIDI de valor = Atual + Dígito");

        mapCharsToDescription.put("Qualquer outro caracter", "Repete nota, se o anterior era nota, senão, silêncio");




    }

}
