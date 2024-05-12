package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class NPC_wizard extends Entity{

    GamePanel gp;
    public NPC_wizard(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
    }

    public void getImage() {

        up1 = setup("/npc/wizard_sus1");
        up2 = setup("/npc/wizard_sus2");
        down1 = setup("/npc/wizard_jos1");
        down2 = setup("/npc/wizard_jos_2");
        left1 = setup("/npc/wizard_1s");
        left2 = setup("/npc/wizard_2s");
        right1 = setup("/npc/wizard_1d");
        right2 = setup("/npc/wizard_2d");
    }

    public void setDialogue() {

        dialogues[0] = "Dorian";
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

    public void speak() {
        gp.ui.currentDialogue = dialogues[0];
    }
}
