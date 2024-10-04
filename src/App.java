import javax.swing.*;
public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth = 360;
        int boardHeight = 640;
        JFrame frame = new JFrame("Flappy Birds");
        //frame.setVisible(true);
        frame.setSize(boardWidth,boardHeight);
        frame.setResizable(false); //resizing of the screen false
        frame.setLocationRelativeTo(null); //so the screen stay in middle
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Flappybird flappybird = new Flappybird();
        frame.add(flappybird);
        frame.pack(); //use of this cause i want to see titlebar
        flappybird.requestFocus(); //it ensure that panel is ready to receive any keyboard mouse commad
        frame.setVisible(true);

    
    }
}
