package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class NPC_wizard extends Entity{


    public NPC_wizard(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
    }

    public void getImage() {

        up1 = setup("/npc/wizard_sus1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/wizard_sus2", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/wizard_jos1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/wizard_jos_2", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/wizard_1s", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/wizard_2s", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/wizard_1d", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/wizard_2d", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {

        dialogues[0] = "Dorian, esti in viata!\n";
        dialogues[1] = "Nu ma asteptam sa te mai vad..\n";
        dialogues[2] = "Lorian se afla la castel, trebuie sa\n il infrangi!";
        dialogues[3] = "Pentru a trece mai departe,\n ai nevoie de ochiul lui Astaroth";
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
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
    }
}
