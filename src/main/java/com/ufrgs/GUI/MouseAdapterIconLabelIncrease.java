package com.ufrgs.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

// Classe criada para tornar a classe JLabel do Swing, em botões com um efeito de hover. A biblioteca Swing não suporta botões feito apenas por imagens.
public class MouseAdapterIconLabelIncrease extends MouseAdapter {
    Image originalImage;
    Image hoveredImg;
    Runnable actionCallback;
    JLabel label;
    public MouseAdapterIconLabelIncrease(JLabel label, String iconFile, int normalSize, int incrementFactor, Runnable actionCallback){
        this.label = label;
        label.setMinimumSize(new Dimension(normalSize + incrementFactor, normalSize + incrementFactor));
        Image img =  null;
        try {
            img = ImageIO.read(new File(iconFile));
        } catch (IOException e) {
            System.err.println("Error when opening icon: " +  iconFile +  " error: " + e.getMessage());
            throw new RuntimeException();
        }
        this.originalImage = img.getScaledInstance(normalSize,normalSize, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(originalImage));
        this.hoveredImg = originalImage.getScaledInstance(normalSize + incrementFactor, normalSize + incrementFactor, Image.SCALE_SMOOTH);
        this.actionCallback = actionCallback;
    }
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        actionCallback.run();
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        label.setIcon(new ImageIcon(hoveredImg));
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        label.setIcon(new ImageIcon(originalImage));
    }
}
