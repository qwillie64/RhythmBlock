package Main;


import javax.swing.JFrame;

public class Main extends JFrame{

    public Main() {
        setTitle("Rhythm Block");
        setSize(300, 400);

        addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }

    public void startNewGame() {
        GameRootPanel game = new GameRootPanel(getWidth(), getHeight());
        add(game);

        pack();
        setLocationRelativeTo(null);
        
        game.requestFocus();
        game.Start();
    }
    
    public static void main(String[] args) {
        Main windows = new Main();
        windows.setVisible(true);
        windows.startNewGame();
    }
}