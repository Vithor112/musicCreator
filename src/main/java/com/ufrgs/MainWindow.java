package com.ufrgs;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Ao criada deve inicializar a biblioteca de interface gráfica e criar e exibir a tela do programa, também deve responder a inputs do usuário;
public class MainWindow {
    String InputText;
    JButton GenerateMusicBtn;
    JButton SaveMusicBtn;
    JButton PauseMusicBtn;
    Music music;
    JTextArea inputText;
    // TODO WidgetPlayer widgetPlayer;
    JPanel instructionTable;
    private JPanel mainPanel;
    private JButton generateMusicButton;
    private JButton saveMusicButton;
    private JLabel labelInputFIle;

    public MainWindow(){
        ImageIcon x = new ImageIcon("/home/vithor/Desktop/musicCreator/icons/document.png");
        Image img = x.getImage();
        Image newImg = img.getScaledInstance(50,50, Image.SCALE_SMOOTH);
        Image hoveredImg = img.getScaledInstance(60,60, Image.SCALE_SMOOTH);
        labelInputFIle.setIcon(new ImageIcon(newImg));
        labelInputFIle.addMouseListener(new MouseAdapter() {
                                            @Override
                                            public void mouseClicked(MouseEvent mouseEvent) {

                                            }

                                            @Override
                                            public void mouseEntered(MouseEvent mouseEvent) {
                                                labelInputFIle.setIcon(new ImageIcon(hoveredImg));
                                            }

                                            @Override
                                            public void mouseExited(MouseEvent mouseEvent) {
                                                labelInputFIle.setIcon(new ImageIcon(newImg));
                                            }
                                        }
        );
        JFrame frame = new JFrame();
        Toolkit tk=Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        frame.setSize(screenSize.width,screenSize.height);
        frame.add(mainPanel);
        frame.setTitle("Song Parser");
        frame.setVisible(true);
    }
    // Callback que ocorre quando o usuário clica no botão para gerar a música, deve pegar o texto atual e passa-lo para a musicFactory;
    void OnGenerateMusicBtn(){
        music = MusicFactory.createMusic(inputText.getText());
    }
    // Callback  que ocorre quando o usuário clica no botão para salvar a música, deve chamar o método save da classe Music;
    void OnSaveMusicBtn(){
        JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setDialogTitle("Select folder to save music");
        int result = fc.showDialog(null, "Select");
        if (result == JFileChooser.APPROVE_OPTION)
             music.saveMusic(fc.getSelectedFile().getAbsolutePath());
    }
    // Callback que ocorre quando o usuário clica no botão para pausar/start a música, deve tocar ou pausar a música dependendo do estado anterior, chamando o método correspondente da classe Music;
    void OnPauseBtn(){}
    // Seta o tempo da música na barra de progressão;
    void setWidgetPlayerTime(){}
    //  Lê arquivo contendo texto da música
    void readFromTxt(){}

}
