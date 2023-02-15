package com.ufrgs.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class MouseAdapterIconLabel extends MouseAdapter {
    Image originalImage;
    Image hoveredImg;
    Runnable actionCallback;
    JLabel label;
    public MouseAdapterIconLabel(JLabel label, String iconFile, int normalSize, int incrementFactor, Runnable actionCallback){
        this.label = label;
        label.setMinimumSize(new Dimension(normalSize + 5, normalSize + 5));
        Image img =  null;
        try {
            img = ImageIO.read(new File(iconFile));
        } catch (IOException e) {
            System.err.println("Error when opening icon: " +  iconFile +  " error: " + e.getMessage());
            throw new RuntimeException();
        }
        this.originalImage = img.getScaledInstance(normalSize,normalSize, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(originalImage));
        this.hoveredImg = originalImage.getScaledInstance(normalSize + 5, normalSize + 5, Image.SCALE_SMOOTH);
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
