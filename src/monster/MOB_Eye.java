package monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class MOB_Eye extends Entity {
    GamePanel gp;
    public MOB_Eye(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Demon Eye";
        type = 2;
        speed = 1;
        maxHP = 3;
        HP = maxHP;


        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 42;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {

        up1 = setup("/monster/demon_eye1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/demon_eye2", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/demon_eye1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/demon_eye2", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/demon_eye1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/demon_eye2", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/demon_eye1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/demon_eye2", gp.tileSize, gp.tileSize);


    }

    public void setAction() {
        actionCounter++;
        if (actionCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // intre [1,100]

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionCounter = 0;
        }
    }
}
