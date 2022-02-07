package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {

    private static final int SCREEN_WIDTH = 1280;
    private static final int SCREEN_HEIGHT = 720;
    private final int UNIT_SIZE = 25;
    private final int GAME_UNITS = (SCREEN_WIDTH* SCREEN_HEIGHT)/ UNIT_SIZE;
    private static final int DELAY = 100;
    // These arrays will hold all the co-ordinates for the body parts of our snake
    private final int[] x = new int[GAME_UNITS];
    // and it can be bigger than the game itself
    private final int[] y = new int[GAME_UNITS];
    private int bodyParts = 5;
    private int applesEaten;
    private int appleX;
    private int appleY;
    private char direction = 'R';
    private boolean running = false;

    private Timer timer;
    private final Random random;

    private final Image appleImg = new ImageIcon("apple.png").getImage();

    GamePanel(){

        // ********************** Random class's instance ********************
        random = new Random();

        // ********************** Game Panel ********************
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true); // Will allow key listeners to work
        this.addKeyListener(new MyKeyAdapter());

        // Game starts
        startGame();
    }

    private void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g){

        if (running){
            /* for (int i = 0; i <= (SCREEN_WIDTH/UNIT_SIZE); i++) {
                g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT);
                for (int j = 0; j <= SCREEN_HEIGHT/UNIT_SIZE; j++) {
                    g.drawLine(0,j*UNIT_SIZE,SCREEN_WIDTH,j*UNIT_SIZE);
                }
            }
           // ************* For grid **************
             */

            // ********* Apple *********

            g.setColor(Color.red);
            g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);
            //g.drawImage(appleImg,appleX,appleY,null);

            // ********** Snake *****************
            for (int i = 0; i < bodyParts; i++) {
                if(i == 0){
                    // ******* Head Color *********
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }
                else{
                    //******* Body Color ********
                    g.setColor(new Color(44, 180, 44));
                    // g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255))); // For Multi color snake
                    g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }
            }
            // Score
            g.setColor(Color.red);
            g.setFont(new Font("Ink free",Font.BOLD,30));

            // This will Align our text in the Centre of the screen
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score"+applesEaten))/2,g.getFont().getSize());
        }
        else {
            gameOver(g);
        }
    }
    private void move(){
        for (int i = bodyParts; i > 0; i--) {
            x[i]= x[i-1];
            y[i]= y[i-1];
        }
        switch (direction){
            case 'U'-> y[0] -= UNIT_SIZE;
            case 'D'-> y[0] += UNIT_SIZE;
            case 'L'-> x[0] -= UNIT_SIZE;
            case 'R'-> x[0] += UNIT_SIZE;
        }
    }

    private void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    private void checkApple(){
       if((x[0] == appleX) && (y[0] == appleY)){
           bodyParts++;
           applesEaten++;
           newApple();
       }

    }

    private void checkCollisions(){
        for (int i = bodyParts; i > 0; i--) {
            // Touches body
            if((x[0]== x[i])&& (y[0] == y[i])){
                running = false;
            }
            // Touches border
            if (x[0]<0){
                running = false;
            }
            if (x[0]>SCREEN_WIDTH){
                running = false;
            }
            if (y[0]<0){
                running = false;
            }
            if (y[0]>SCREEN_HEIGHT){
                running = false;
            }
            // Timer stop
            if (!running){
                timer.stop();
            }
        }
    }

    private void gameOver(Graphics g){
        timer.stop();
        // Game Over Text
        g.setColor(Color.red);
        g.setFont(new Font("Ink free",Font.BOLD,75));

        // This will Align our text in the Centre of the screen
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2,(SCREEN_HEIGHT/2));

        // Score at End screen
        g.setColor(Color.GREEN);
        g.setFont(new Font("Ink free",Font.BOLD,30));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Your score was: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Your score was: "+applesEaten))/2,SCREEN_HEIGHT/2+75);

        // Dispose
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_UP ->{
                        if(direction != 'U'){
                        direction = 'U';
                    }}
                case KeyEvent.VK_DOWN ->{
                    if(direction != 'D'){
                        direction = 'D';
                    }}
                case KeyEvent.VK_LEFT ->{
                    if(direction != 'L'){
                        direction = 'L';
                    }}
                case KeyEvent.VK_RIGHT ->{
                    if(direction != 'R'){
                        direction = 'R';
                    }}
            }
        }
    }
}
