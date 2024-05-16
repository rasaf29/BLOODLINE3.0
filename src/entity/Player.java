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
        solidArea.width = 28;
        solidArea.height = 28;

        attackArea.width = 28;
        attackArea.height = 28;
        setDefaultValues();
        getPlayerImage();
        getAttackImage();
    }


    public void setDefaultValues() {
        worldx = gp.tileSize * 28;
        worldy = gp.tileSize * 20;
        speed = 4;
        direction = "down";

        maxHP = 2;
        HP = maxHP;

    }

    public void setDefaultPosition() {
        gp.currentMap = 0;
        worldx = gp.tileSize * 28;
        worldy = gp.tileSize * 20;
        direction = "down";
    }

    public void restore() {
        HP = maxHP;
        invincible = false;
    }
    public void getPlayerImage() {

        up1 = setup("/player/sus_1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/sus_2", gp.tileSize, gp.tileSize);
        down1 = setup("/player/jos_1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/jos_2", gp.tileSize, gp.tileSize);
        left1 = setup("/player/stanga_1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/stanga_2", gp.tileSize, gp.tileSize);
        right1 = setup("/player/dreapta_1", gp.tileSize, gp.tileSize);
        right2 = setup("/player/dreapta_2", gp.tileSize, gp.tileSize);
    }

    public void getAttackImage() {
        atacSus1 = setup("/player/atac_sus1", gp.tileSize, gp.tileSize * 2);
        atacSus2 = setup("/player/atac_sus2", gp.tileSize, gp.tileSize * 2);
        atacJos1 = setup("/player/atac_jos1",  gp.tileSize, gp.tileSize * 2);
        atacJos2 = setup("/player/atac_jos2",  gp.tileSize, gp.tileSize * 2);
        atacStanga1 = setup("/player/atac_stanga1", gp.tileSize * 2, gp.tileSize);
        atacStanga2 = setup("/player/atac_stanga2",  gp.tileSize * 2 , gp.tileSize);
        atacDreapta1 = setup("/player/atac_dreapta1", gp.tileSize * 2, gp.tileSize);
        atacDreapta2 = setup("/player/atac_dreapta2", gp.tileSize * 2, gp.tileSize);

    }

    public void update() {
        if (attacking == true) {
            attackingMethod();
        }
        else if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true)
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

            //coliziune event-uri
            gp.eHandler.checkEvent();
            gp.keyH.enterPressed = false;

            //coliziune monstrii
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);




            if (collisionOn == false && keyH.enterPressed == false) {
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
            gp.keyH.enterPressed = false;
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

        if(invincible == true) {
            invincibleCounter++;
            if(invincibleCounter > 120) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (HP <= 0) {
            gp.gameState = gp.gameOverState;
        }

    }

    public void attackingMethod() {
        spriteCounter++;
        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            //pt coliziune in functie de tile ul pt arma

            int currentWorldX = worldx;
            int currentWorldY = worldy;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (direction) {
                case "up": worldy -= attackArea.height; break;
                case "down": worldy += attackArea.height; break;
                case "left": worldx -= attackArea.width; break;
                case "right": worldx += attackArea.width; break;
            }

            //zona de atac devine solida
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            //pt coliziune cu monstrii cu coordonatele update-uite
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);

            //dupa verificare coliziune, ma intorc la datele originale
            worldx = currentWorldX;
            worldy = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void pickUp(int i) {

        if(i != 999) {
            String objName = gp.obj[gp.currentMap][i].name;
            switch (objName) {
                case "Key":
                    hasKey++;
                    gp.ui.showMessage("Cheie obtinuta!");
                    gp.obj[gp.currentMap][i] = null;
                    break;

                case "Door":
                    if(hasKey > 0) {
                        gp.obj[gp.currentMap][i] = null;
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
        if (gp.keyH.enterPressed == true) {
            if (i != 999) {
                    gp.gameState = gp.dialogueState;
                    gp.npc[gp.currentMap][i].speak();
                //gp.keyH.enterPressed = false;
            }
            else {
                attacking = true;
            }
        }
    }

    public void contactMonster(int i) {

        if (i != 999) {

            if(invincible == false) {
                HP -= 1;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i ) {
        if (i != 999){
            if(gp.monster[gp.currentMap][i].invincible == false) {
                gp.monster[gp.currentMap][i].HP -= 1;
                gp.monster[gp.currentMap][i].invincible = true;
                if (gp.monster[gp.currentMap][i].HP <= 0) {
                    gp.monster[gp.currentMap][i] = null;
                }
            }
        }

    }

    public void draw(Graphics2D g2) {


        BufferedImage image = null;
        int tempX = screenX;
        int tempY = screenY;
        switch (direction) {
            case "up":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                }
                if (attacking == true) {
                    tempY = screenY - gp.tileSize; // in sus am +1 tile si face cu tile ul de sus (al meu este 16x32 in acest caz)
                    if (spriteNum == 1) {
                        image = atacSus1;
                    }
                    if (spriteNum == 2) {
                        image = atacSus2;
                    }
                }
                break;
            case "down":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                }
                if (attacking == true) {
                    if (spriteNum == 1) {
                        image = atacJos1;
                    }
                    if (spriteNum == 2) {
                        image = atacJos2;
                    }
                }
                break;
            case "left":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                }
                if (attacking == true) {
                    tempX = screenX - gp.tileSize;  // in stanga am +1 tile si face cu tile ul de sus (al meu este 32x16 in acest caz)
                    if (spriteNum == 1) {
                        image = atacStanga1;
                    }
                    if (spriteNum == 2) {
                        image = atacStanga2;
                    }
                }
                break;
            case "right":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                }
                if (attacking == true) {
                    if (spriteNum == 1) {
                        image = atacDreapta1;
                    }
                    if (spriteNum == 2) {
                        image = atacDreapta2;
                    }
                }
                break;
        }
        if(invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, tempX, tempY, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
