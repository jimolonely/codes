package com.jimo.cmd;

import java.awt.*;
import java.awt.event.*;

/**
 * @author jimo
 * @date 19-4-9 下午1:39
 */
public class SplashDemo extends Frame implements ActionListener {


    public static void main(String[] args) {
        new SplashDemo();
    }

    private static WindowListener closeWindow = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            e.getWindow().dispose();
        }
    };

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }

    private SplashDemo() throws HeadlessException {
        super("Splash Demo by jimo");
        setSize(300, 200);
        setLayout(new BorderLayout());
        Menu m1 = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        m1.add(exitItem);
        m1.addActionListener(this);
        this.addWindowListener(closeWindow);

        MenuBar mb = new MenuBar();
        setMenuBar(mb);
        mb.add(m1);

        SplashScreen splashScreen = SplashScreen.getSplashScreen();
        if (splashScreen == null) {
            System.out.println("splash is null");
            return;
        }
        Graphics2D g = splashScreen.createGraphics();
        if (g == null) {
            System.out.println("g is null");
            return;
        }
        for (int i = 0; i < 100; i++) {
            renderSplashFrame(g, i);
            splashScreen.update();
            try {
                Thread.sleep(90);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        splashScreen.close();
        setVisible(true);
        toFront();
    }

    private static void renderSplashFrame(Graphics2D g, int frame) {
        final String[] comps = {"jimo", "hehe", "haha"};
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(120, 140, 200, 40);
        g.setPaintMode();
        g.setColor(Color.BLACK);
        g.drawString("Loading " + comps[(frame / 5) % 3] + "...", 120, 150);
    }
}
