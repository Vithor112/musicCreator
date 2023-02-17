package com.ufrgs;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class Mapping {
    private final HashMap<String, String> mapCharsToJFugueCommand = new HashMap<>();

    public Mapping() {
        initializeMap();
    }

    // Inicializa o map com valores hardcoded
    private void initializeMap(){
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
}
