package main;

import object.OBJ_Door;
import object.OBJ_HP;
import object.OBJ_Key;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font serif_40, serif_80;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    BufferedImage HP_F, HP_H, HP_E;
    int messageCounter = 0;
    public boolean gameFinished = false;

    public String currentDialogue = "";

    public int commandNum = 0;
    public UI(GamePanel gp) {
        this.gp = gp;
        serif_40 = new Font("Serif", Font.PLAIN, 40);
        serif_80 = new Font("Serif", Font.BOLD, 80);
        //OBJ_Key key = new OBJ_Key(gp);
        //keyImage = key.image;

        SuperObject heart = new OBJ_HP(gp);
        HP_F = heart.image;
        HP_H = heart.image2;
        HP_E = heart.image3;

    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        if(gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        if(gameFinished == true) {

            String text;
            int textLength;

            g2.setFont(serif_40);
            g2.setColor(Color.white);

            int x = gp.screenWidth / 2;
            int y = gp.screenHeight / 2;

            text = "Comoara gasita!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 - (gp.tileSize * 3);
            g2.drawString(text, x, y);

            g2.setFont(serif_80);
            g2.setColor(Color.red);

            text = "Nivel terminat!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 2);
            g2.drawString(text, x, y);
            gp.gameThread = null;

        }
        else {
            g2.setFont(serif_40);
            g2.setColor(Color.white);
            if (gp.gameState == gp.playState) {
                g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
                if (messageOn == true) {
                    g2.setFont(g2.getFont().deriveFont(30F));
                    g2.drawString(message, 270, 50);
                    messageCounter++;
                    if (messageCounter > 120) {
                        messageCounter = 0;
                        messageOn = false;
                    }
                }
            }
            if (gp.gameState == gp.playState) {
                     drawPlayerLife();
            }
            if (gp.gameState == gp.pauseState) {
                    drawPlayerLife();
                    drawPauseScreen();
            }
            if (gp.gameState == gp.dialogueState) {
                drawPlayerLife();
                drawDialogueScreen();
            }
        }

    }

    public void drawPlayerLife() {

        //gp.player.HP = 1;
        int x = gp.tileSize / 2 ;
        int y = gp.tileSize / 2;
        int i = 0;
        while (i < gp.player.maxHP / 2 ) {
            g2.drawImage(HP_E, x , y, null);
            i++;
            x += gp.tileSize;
        }

        //RESETEZ
      x = gp.tileSize / 2;
      y = gp.tileSize / 2;
      i = 0;

      while (i < gp.player.HP) {
          g2.drawImage(HP_H, x, y, null);
          i++;
          if (i < gp.player.HP) {
              g2.drawImage(HP_F, x, y, null);
          }
          i++;
          x += gp.tileSize;
      }

    }
    public void drawTitleScreen() {
        //TITLU
        g2.setColor(new Color(100,0,0));
        g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Bloodline";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;

        //UMBRE LA SCRIS
        g2.setColor(Color.black);
        g2.drawString(text, x + 5, y + 5);
        //CULOARE PRINCIPALA
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.HANGING_BASELINE, 48F));

        text = "START";
        x = getXforCenteredText(text);
        y += gp.tileSize * 3;
        g2.drawString(text, x, y);
        if(commandNum == 0) {
            g2.drawString("~", x - gp.tileSize * 1, y);
        }

        text = "LOAD";
        x = getXforCenteredText(text);
        y += gp.tileSize * 2;
        g2.drawString(text, x, y);
        if(commandNum == 1) {
            g2.drawString("~", x - gp.tileSize * 1, y);
        }

        text = "EXIT";
        x = getXforCenteredText(text);
        y += gp.tileSize * 2;
        g2.drawString(text, x, y);
        if(commandNum == 2) {
            g2.drawString("~", x - gp.tileSize * 1, y);
        }
    }
    public void drawDialogueScreen() {

        //fereastra

        int x, y, width, height;
        x = gp.tileSize * 2;
        y = gp.tileSize / 2 ;
        width = gp.screenWidth - (gp.tileSize * 4);
        height = gp.tileSize * 4;
        drawSubWindow(x, y ,width, height);

        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString(currentDialogue, x ,y );
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0,0,0, 150);
        g2.setColor(c);
        g2.fillRoundRect(x, y , width, height, 20 ,20);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width -10, height-10, 10, 10);
    }
    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUZA";
        int x = getXforCenteredText(text) - 20 ;
        int y = gp.screenHeight / 2 - 200;

        g2.drawString(text, x, y);
    }

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }
}
