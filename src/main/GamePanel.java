package main;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import org.w3c.dom.ls.LSOutput;
import tile.TileManager;

import javax.swing.*;
import javax.swing.plaf.TableUI;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.awt.*;

public class GamePanel extends JPanel implements  Runnable{

    int FPS = 60;
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    Thread gameThread;
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int titleState = 0;

    public CollisionChecker cChecker = new CollisionChecker(this);
    KeyHandler keyH = new KeyHandler(this);
    TileManager tileM = new TileManager(this);
    public Player player = new Player(this, keyH);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public SuperObject obj[] = new SuperObject[10];
    public Entity npc[] = new Entity[10];

    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        assetSetter.setObject();
        assetSetter.setNPC();
        gameState = titleState;
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000) {
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }

    }
    public void update() {

        if (gameState == playState) {
            player.update();
            for(int i = 0; i < npc.length; ++i) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }
        }
        if(gameState == pauseState) {

        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        long drawStart = 0;
        drawStart = System.nanoTime();
        if (gameState == titleState) {
            ui.draw(g2);

        }
        else {
            tileM.draw(g2);

            for(int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    obj[i].draw(g2, this);
                }
            }
            for (int i = 0; i < npc.length; i++) {
                if(npc[i] != null) {
                    npc[i].draw(g2);
                }
            }


            player.draw(g2);
            ui.draw(g2);
        }


        long drawEnd = System.nanoTime();
        long passed = drawEnd -drawStart;
        //g2.drawString("Draw Time: " + passed, 10 , 400);
        g2.dispose();
    }

}
