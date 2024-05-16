package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {
    public int worldx,worldy;
    public int speed;
    GamePanel gp;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;

    public BufferedImage atacSus1, atacSus2 , atacJos1, atacJos2 , atacStanga1, atacStanga2, atacDreapta1, atacDreapta2;
    public String direction = "down";

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionCounter = 0;
    String dialogues[] = new String[20];

    int dialogueIndex = 0;
    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;
    public boolean attacking = false;

    public int type; // 0 - erou, 1 - npc, 2 - mobi
    public int maxHP;
    public int HP;

    public boolean invincible = false;

    public int invincibleCounter = 0;
    public Entity(GamePanel gp) {
        this.gp = gp;
    }
    public void setAction() {

    }
    public void speak() {

    }
    public void update() {

        setAction();
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if (this.type == 2 && contactPlayer == true) {
            if (gp.player.invincible == false) {
                gp.player.HP -= 1;
                gp.player.invincible = true;
                // imi iau 1 damage si dupa sunt invincibil pt 3 secunde
            }
        }
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
        if (invincible == true) {
            invincibleCounter++;
            if(invincibleCounter > 30) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }
    public void draw(Graphics2D g2) {
        BufferedImage  image = null;
        int screenX = worldx - gp.player.worldx + gp.player.screenX;
        int screenY = worldy - gp.player.worldy + gp.player.screenY;

        if(worldx + gp.tileSize > gp.player.worldx - gp.player.screenX && worldx - gp.tileSize < gp.player.worldx + gp.player.screenX && worldy  + gp.tileSize > gp.player.worldy - gp.player.screenY &&
                worldy - gp.tileSize < gp.player.worldy + gp.player.screenY) {
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
            if(invincible == true) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
            }
            g2.drawImage( image,screenX, screenY, gp.tileSize, gp.tileSize, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }
    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {

            image = ImageIO.read(getClass().getResourceAsStream(  imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
