package com.ufrgs;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.io.*;

public class Mapping {

    private static final String MAP_FILE = "./src/main/mapping.txt";
    private final HashMap<String, String> mapCharsToJFugueCommand = new HashMap<>();

    public Mapping() throws Exception {
        //initializeMap();
        initializeFromFile();
        //System.out.println(mapCharsToJFugueCommand);
    }

    // Inicializa o map com valores hardcoded
    private void initializeMap() {
        // TODO
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

    public void initializeFromFile() throws Exception {
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

                sMap = map[0].split("-");
                int numChars = (int) sMap[1].charAt(0)- (int) sMap[0].charAt(0) +1;
                int ini = (int) sMap[0].charAt(0);
                char charat;

                for (int x=ini;x<numChars+ini;x++){
                    mapCharsToJFugueCommand.put(String.valueOf((char) x), map[1]+String.valueOf((char) x));
                }
            }else{
                mapCharsToJFugueCommand.put(map[0], map[1]);
            }
        }

    }

}
