package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

    public final int maxMap = 3;
    public int currentMap = 0;

    Thread gameThread;
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int titleState = 0;
    public final int gameOverState = 4;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public KeyHandler keyH = new KeyHandler(this);
    TileManager tileM = new TileManager(this);
    public Player player = new Player(this, keyH);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public Entity obj[][]= new Entity[maxMap][10];
    public Entity npc[][] = new Entity[maxMap][10];

    public Entity monster[][] = new Entity[maxMap][10];

    public EventHandler eHandler = new EventHandler(this );

    ArrayList<Entity> entityList = new ArrayList<>();

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
        assetSetter.setMonster();
        gameState = titleState;
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;
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

    public void retry() {
        player.setDefaultPosition();
        player.restore();
        assetSetter.setNPC();
        assetSetter.setMonster();
    }

    public void restart() {
        player.setDefaultValues();
        player.restore();
       assetSetter.setObject();
       assetSetter.setNPC();
       assetSetter.setMonster();
    }
    public void update() {

        if (gameState == playState) {
            player.update();
            for(int i = 0; i < npc[1].length; ++i) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }
            for (int i = 0; i < monster[1].length; ++i) {
                if (monster[currentMap][i] != null) {
                    monster[currentMap][i].update();
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
            entityList.add(player);
            for (int i = 0; i < npc[1].length; ++i) {
                if (npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }
            for (int i = 0; i < obj[1].length; i++) {
                if(obj[currentMap][i] != null) {
                    entityList.add(obj[currentMap][i]);
                }
            }
            for (int i = 0; i < monster[1].length; i++) {
                if(monster[currentMap][i] != null) {
                    entityList.add(monster[currentMap][i]);
                }
            }
            //player.draw(g2);

            //SORTARE
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldy, e2.worldy);
                    return result;
                }
            });
            for(int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            //GOLIRE LISTA
            /*for(int i = 0; i < entityList.size(); i++) {
                entityList.remove(i);
            }*/

            entityList.clear();

            if (keyH.check == true) {
                g2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                g2.setColor(Color.white);
                int x = 10;
                int y = 400;
                int lineHeight = 20;

                g2.drawString("WorldX:" + player.worldx, x, y); y += lineHeight;
                g2.drawString("WorldY:" + player.worldy, x, y); y += lineHeight;
                g2.drawString("Col:" + (player.worldx + player.solidArea.x) / tileSize, x, y); y += lineHeight;
                g2.drawString("Row:" + (player.worldy + player.solidArea.y) / tileSize, x, y); y += lineHeight;
            }

            ui.draw(g2);
        }


        long drawEnd = System.nanoTime();
        long passed = drawEnd -drawStart;
        //g2.drawString("Draw Time: " + passed, 10 , 400);
        g2.dispose();
    }

}
