package com.ufrgs.GUI;

import com.ufrgs.Music;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Player {

    private JPanel mainPanel;
    private JProgressBar progressBar;
    private JLabel startLabel;
    private JLabel timeLabel;

    private Music music;

    private AtomicReference<String> duration = new AtomicReference<>();

    AtomicBoolean running = new AtomicBoolean(false);

    MouseAdapterIconLabelUpdate labelListener;

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public Player() {
        labelListener = new MouseAdapterIconLabelUpdate(startLabel, "./icons/play.png", "./icons/pause.png", 30, this::playMusic);
        startLabel.addMouseListener(labelListener);
        timeLabel.setText(" 0:00/0:00");
        mainPanel.setMaximumSize(new Dimension(900, 70));
        mainPanel.setSize(900, 70);
    }

    void playMusic() {
        if (music != null) {
            music.Pause();
        }
        // TODO throw warning
    }

    public JPanel getPlayerPanel() {
        return mainPanel;
    }

    public void setMusic(Music music) {
        if (this.music != null) {
            stop();
            this.music.stop();
        }
        this.music = music;
        labelListener.changeToDefault();
        double durationSecs = music.calcDuration();
        int minutes = 0;
        while (durationSecs >= 60) {
            durationSecs /= 60;
            minutes++;
        }
        duration.set("/" + String.format("%02d", minutes) + ":" + String.format("%02d", (int) durationSecs));
        synchronized (timeLabel) {
            timeLabel.setText(" 0:00" + duration.get());
        }
        executorService.submit(() -> {
            running.set(true);
            while (running.get()) {
                if (music.hasMusicEnded()) {
                    synchronized (labelListener) {
                        labelListener.changeToDefault();
                    }
                }
                long seconds = music.getTime() / 1000000000;
                int minutesLambda = 0;
                while (seconds >= 60) {
                    seconds /= 60;
                    minutesLambda++;
                }
                String currentTime = String.format("%02d", minutesLambda) + ":" + String.format("%02d", (int) seconds);
                synchronized (timeLabel) {
                    timeLabel.setText(currentTime + duration.get());
                }
            }
        });
    }

    private void stop() {
        running.set(false);
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(2, 2), new Dimension(2, 2), new Dimension(2, 2), 0, false));
        startLabel = new JLabel();
        startLabel.setText("");
        mainPanel.add(startLabel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        timeLabel = new JLabel();
        timeLabel.setText("Label");
        mainPanel.add(timeLabel, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        progressBar = new JProgressBar();
        mainPanel.add(progressBar, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
