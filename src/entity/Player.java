package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {


    KeyHandler keyH;
    public final int screenX;

    public final int screenY;
    public int hasKey = 0;
    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        setDefaultValues();
        getPlayerImage();
    }


    public void setDefaultValues() {
        worldx = gp.tileSize * 28;
        worldy = gp.tileSize * 20;
        speed = 4;
        direction = "down";

        maxHP = 2;
        HP = maxHP;

    }

    public void getPlayerImage() {

        up1 = setup("/player/sus_1");
        up2 = setup("/player/sus_2");
        down1 = setup("/player/jos_1");
        down2 = setup("/player/jos_2");
        left1 = setup("/player/stanga_1");
        left2 = setup("/player/stanga_2");
        right1 = setup("/player/dreapta_1");
        right2 = setup("/player/dreapta_2");
    }


    public void update() {
        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true)
        {
            if (keyH.upPressed == true) {
                direction = "up";
            } else if (keyH.downPressed == true) {
                direction = "down";
            } else if (keyH.leftPressed == true) {
                direction = "left";
            } else if (keyH.rightPressed == true) {
                direction = "right";
            }
            // verific coliziune tile-uri
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //coliziune obiecte
            int objectIndex = gp.cChecker.checkObject(this,true);
            pickUp(objectIndex);

            //coliziune NPC
            int NPCindex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(NPCindex);

            if (collisionOn == false) {
                switch (direction) {
                    case "up":
                        worldy -= speed;
                        break;
                    case "down":
                        worldy += speed;
                        break;
                    case "left":
                        worldx -= speed;
                        break;
                    case "right":
                        worldx += speed;
                        break;

                }
            }
            spriteCounter++;
            if(spriteCounter > 10) {
                if(spriteNum == 1) {
                    spriteNum = 2;
                }
                else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

    }

    public void pickUp(int i) {

        if(i != 999) {
            String objName = gp.obj[i].name;
            switch (objName) {
                case "Key":
                    hasKey++;
                    gp.ui.showMessage("Cheie obtinuta!");
                    gp.obj[i] = null;
                    break;

                case "Door":
                    if(hasKey > 0) {
                        gp.obj[i] = null;
                        hasKey--;

                    }
                    break;
                case "Chest" :
                    gp.ui.gameFinished = true;
                    break;

            }
        }

    }
    public void interactNPC(int i ) {
        if (i != 999) {
               // gp.gameState = gp.dialogueState;
               // gp.npc[i].speak();
        }
    }

    public void draw(Graphics2D g2) {


        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, null);

    }
}
