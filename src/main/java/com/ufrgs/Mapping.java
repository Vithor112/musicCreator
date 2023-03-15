package com.ufrgs;

import java.io.FileNotFoundException;
import java.util.*;
import java.io.*;

public class Mapping {

    private static final String MAP_FILE = "./src/main/mapping.txt";
    private final HashMap<String, String> mapCharsToJFugueCommand = new HashMap<>();

    private final LinkedHashMap<String, String> mapCharsToDescription = new LinkedHashMap<>();

    public Mapping() throws IOException {
        initializeFromFile();
        charsToAction();
    }

    // Retorna uma lista com as Strings que mapeiam pra um comando no map
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
                for (int x=initial;x<=end;x++){
                    mapCharsToJFugueCommand.put(String.valueOf((char) x), map[1]+String.valueOf((char) x));
                }
            }else{
                mapCharsToJFugueCommand.put(map[0], map[1]);
            }
        }

    }

    public void charsToAction(){
        mapCharsToDescription.put("A","Nota Lá");
        mapCharsToDescription.put("B","Nota Si");
        mapCharsToDescription.put("C","Nota Dó");
        mapCharsToDescription.put("D","Nota Ré");
        mapCharsToDescription.put("E","Nota Mi");
        mapCharsToDescription.put("F","Nota Fá");
        mapCharsToDescription.put("G","Nota Sol");

        mapCharsToDescription.put("' ' (Espaço) ","Dobra o volume");
        mapCharsToDescription.put("?","Aumenta uma oitava");

        mapCharsToDescription.put("!", "Instrumento Agogo");
        mapCharsToDescription.put("O/o/I/i", "Instrumento Harpsichord");
        mapCharsToDescription.put("\\n", "Instrumento Tubular Bells");
        mapCharsToDescription.put(";", "Instrumento Pan Flute");
        mapCharsToDescription.put(",", "Instrumento Church Organ");
        mapCharsToDescription.put("Dígito", "Instrumento += [digito]");

        mapCharsToDescription.put("Else", "Repete nota ou pausa");




    }
    public Set<String> getDisplayChars(){ return mapCharsToDescription.keySet();
    }
    public Collection<String> getDisplayActions(){
        return mapCharsToDescription.values();
    }
}
