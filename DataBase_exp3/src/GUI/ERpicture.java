package GUI;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class ERpicture {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        EventQueue.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                JFrame frame = new ImageViewerFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

class ImageViewerFrame extends JFrame{
    public ImageViewerFrame(){
        setTitle("ImageViewer");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setBounds(400, 100, 1200, 900);
        label = new JLabel();
        add(label);
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);
        JMenu menu = new JMenu("File");
        menubar.add(menu);
        JMenuItem openItem = new JMenuItem("Open");
        menu.add(openItem);
        JMenuItem exitItem = new JMenuItem("Close");
        menu.add(exitItem);
        openItem.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                int result = chooser.showOpenDialog(null);
                if(result == JFileChooser.APPROVE_OPTION){
                    String name = chooser.getSelectedFile().getPath();
                    label.setIcon(new ImageIcon(name));
                }
            }
        });
        exitItem.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                setVisible(false);;
            }
        });
    }
    private JLabel label;
    private JFileChooser chooser;
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 400;
}