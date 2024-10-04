import java.awt.*;
import java.awt.event.*;
import java.nio.channels.Pipe;
import java.util.ArrayList; //USE for storing pipe in game
import java.util.Random;   //use for placing the pipe at random
import javax.swing.*;

public class Flappybird extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 360;
    int boardHeight = 640;
    //image
    Image backgroundimg;
    Image birdimg;
    Image toppipeimg;
    Image bottompipeimg;
    //Bird
    int birdX = boardWidth/8;
    int birdY = boardHeight/2;
    int birdWidth = 34;
    int birdHeight = 24;
    
    class Bird{
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img){
          this.img = img;
        }
    }
    //pipes
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    class Pipe{
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;
        Pipe(Image img){
            this.img = img;
        }
    }





    //game logic
    Bird bird;
    int velocityX = -4; //move pipe to the left
    int velocityY = 0; // move bird upwards
    int gravity = 1;
    Timer gameloop;
    Timer placepipetime;
    boolean gameOver = false;
    double score = 0;

    ArrayList<Pipe> pipes;
    Random random =  new Random();


    Flappybird(){
     setFocusable(true); //this make sure that class will take key events
     addKeyListener(this);//it make sure to check keylistener if key is pressed

        setPreferredSize(new Dimension(boardWidth,boardHeight));
        setBackground(Color.blue);
    //load image
    backgroundimg = new ImageIcon(getClass().getResource("flappybirdbg.png")).getImage();
    birdimg = new ImageIcon(getClass().getResource("flappybird.png")).getImage();
    toppipeimg = new ImageIcon(getClass().getResource("toppipe.png")).getImage();
    bottompipeimg = new ImageIcon(getClass().getResource("bottompipe.png")).getImage();

    //bird 
    bird = new Bird(birdimg);
    pipes = new ArrayList<Pipe>();

    //placepipetimer
    placepipetime = new Timer(1500, new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e) {
        placepipe();
      }
    });
    placepipetime.start();


    //game timer
    
    gameloop = new Timer(1000/60,this); // 1000/60 =16.6MS
    gameloop.start();
    }


public void placepipe(){
    int openingSpace = pipeHeight/4;
    int randompipeY = (int)(pipeY - pipeHeight/4 - Math.random()*pipeHeight/2);
    Pipe toppipe = new Pipe(toppipeimg);
    toppipe.y = randompipeY;
    pipes.add(toppipe);

    Pipe bottompipe = new Pipe(bottompipeimg);
    bottompipe.y = toppipe.y + openingSpace + pipeHeight;
    pipes.add(bottompipe); 
}










public void paintComponent(Graphics g){
    super.paintComponent(g); // super class
    draw(g);

}
public void draw(Graphics g){
    //background
   //for checking my frames are working i simply sout here
  // System.out.println("draw");
    g.drawImage(backgroundimg, 0, 0, boardWidth ,boardHeight,null);// we put x=0 & y=0 cause we want to start from the top

//bird

g.drawImage(birdimg, bird.x, bird.y, birdWidth, birdHeight, null);


for(int i = 0; i<pipes.size() ;i++){
    Pipe pipe = pipes.get(i);
    g.drawImage(pipe.img, pipe.x, pipe.y, pipeWidth, pipeHeight, null);

}
    //score
    g.setColor(Color.white);

    g.setFont(new Font("Arial", Font.PLAIN, 32));
    if (gameOver) {
        g.drawString("Game Over: " + String.valueOf((int) score), 10, 35);
    }
    else {
        g.drawString(String.valueOf((int) score), 10, 35);
    }
    


}
public void move() {
    //bird
    velocityY += gravity;
    bird.y += velocityY;
    bird.y = Math.max(bird.y , 0);
    //pipe
    for(int i = 0; i<pipes.size() ;i++){
        Pipe pipe = pipes.get(i);
       pipe.x += velocityX;

      if(collision(bird, pipe)){
        gameOver = true;

      } 
      if (!pipe.passed && bird.x > pipe.x + pipe.width) {
        score += 0.5; //0.5 because there are 2 pipes! so 0.5*2 = 1, 1 for each set of pipes
        pipe.passed = true;
    }

       if(bird.y > boardHeight){
        gameOver = true;
       }
    
    }
    
}
boolean collision(Bird a, Pipe b) {
    return a.x < b.x + b.width &&   //a's top left corner doesn't reach b's top right corner
           a.x + a.width > b.x &&   //a's top right corner passes b's top left corner
           a.y < b.y + b.height &&  //a's top left corner doesn't reach b's bottom left corner
           a.y + a.height > b.y;    //a's bottom left corner passes b's top left corner
}
@Override
public void actionPerformed(ActionEvent e) {
     move();
     repaint();
     if(gameOver){
        placepipetime.stop();
        gameloop.stop();
     }
    
}



@Override
public void keyPressed(KeyEvent e) {
   if(e.getKeyCode() == KeyEvent.VK_SPACE){
   velocityY = -9;
   if (gameOver) {
    //restart game by resetting conditions
    bird.y = birdY;
    velocityY = 0;
    pipes.clear();
    gameOver = false;
    score = 0;
    gameloop.start();
    placepipetime.start();
}
}
}
@Override
public void keyTyped(KeyEvent e) {
  
}

@Override
public void keyReleased(KeyEvent e) {
   
}   
}
