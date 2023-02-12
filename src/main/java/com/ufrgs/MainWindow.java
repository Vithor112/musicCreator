package com.ufrgs;

import javax.swing.*;

public class MainWindow {
    String InputText;
    JButton GenerateMusicBtn;
    JButton SaveMusicBtn;
    JButton PauseMusicBtn;
    JTextArea inputText;
    // TODO WidgetPlayer widgetPlayer;
    JPanel instructionTable;
    // Callback que ocorre quando o usuário clica no botão para gerar a música, deve pegar o texto atual e passa-lo para a musicFactory;
    void OnGenerateMusicBtn(){}
    // Callback  que ocorre quando o usuário clica no botão para salvar a música, deve chamar o método save da classe Music;
    void OnSaveMusicBtn(){}
    // Callback que ocorre quando o usuário clica no botão para pausar/start a música, deve tocar ou pausar a música dependendo do estado anterior, chamando o método correspondente da classe Music;
    void OnPauseBtn(){}
    // Seta o tempo da música na barra de progressão;
    void setWidgetPlayerTime(){}
    //  Lê arquivo contendo texto da música
    void readFromTxt(){}

}
