package com.ufrgs.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class MouseAdapterIconLabelUpdate extends MouseAdapter {
    ImageIcon icon;
    ImageIcon nextIcon;
    Runnable actionCallback;
    boolean next = false;
    JLabel label;
    public MouseAdapterIconLabelUpdate(JLabel label, String iconFile, String iconFileNext, int normalSize, Runnable actionCallback){
        this.label = label;
        label.setMinimumSize(new Dimension(normalSize, normalSize));
        Image img =  null;
        try {
            img = ImageIO.read(new File(iconFile));
        } catch (IOException e) {
            System.err.println("Error when opening icon: " +  iconFile +  " error: " + e.getMessage());
            throw new RuntimeException();
        }
        this.icon = new ImageIcon(img.getScaledInstance(normalSize,normalSize, Image.SCALE_SMOOTH));
        label.setIcon(icon);
        try {
            img = ImageIO.read(new File(iconFileNext));
        } catch (IOException e) {
            System.err.println("Error when opening icon: " +  iconFile +  " error: " + e.getMessage());
            throw new RuntimeException();
        }
        this.nextIcon = new ImageIcon(img.getScaledInstance(normalSize, normalSize, Image.SCALE_SMOOTH));
        this.actionCallback = actionCallback;
    }
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (next) {
            next = false;
            label.setIcon(nextIcon);
        } else {
            next = true;
            label.setIcon(icon);
        }
        actionCallback.run();
    }

}
