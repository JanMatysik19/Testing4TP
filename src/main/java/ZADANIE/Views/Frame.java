package ZADANIE.Views;

import ZADANIE.Config.AppConstants;
import ZADANIE.Config.Styles;
import ZADANIE.Utils.Stylised;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Frame extends JFrame {
    public Frame(IWindowClosingHandler windowClosingHandler) {
        setTitle(AppConstants.APP_TITLE);
        setLocationRelativeTo(null);
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                windowClosingHandler.handleWindowClosing(e);
            }
        });
    }

    public void display(Content content) {
        setContentPane(content);
        pack();
        setLocation(200, 200);
        setVisible(true);
    }

    public interface IWindowClosingHandler {
        void handleWindowClosing(WindowEvent event);
    }

    public static class Content extends JPanel {
        private final ControlPanel controlPanel;
        private final InsertPanel insertPanel;
        private final DataPanel dataPanel;

        public Content(ControlPanel controlPanel, InsertPanel insertPanel, DataPanel dataPanel) {
            this.controlPanel = controlPanel;
            this.insertPanel = insertPanel;
            this.dataPanel = dataPanel;

            // View initialisation >--------------------
            Stylised.stylised(this, Styles.FRAME_CONTENT);
            setBorder(BorderFactory.createEmptyBorder(25, 50, 50, 50));
            setLayout(new GridBagLayout());
            var c = new GridBagConstraints();
            c.anchor = GridBagConstraints.NORTHWEST;

            c.gridx = 0;
            c.gridy = 0;
            c.insets = new Insets(0, 0, 0, 25);
            add(controlPanel, c);
            c.gridx = 0;
            c.gridy = 1;
            c.weighty = 1;
            c.insets = new Insets(25, 0, 0, 25);
            add(insertPanel, c);
            c.gridx = 1;
            c.gridy = 0;
            c.gridheight = 2;
            c.weighty = 1;
            c.insets = new Insets(0, 0, 0, 0);
            add(dataPanel, c);
        }
    }
}
