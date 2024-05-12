package main;

import entity.NPC_wizard;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = 13 * gp.tileSize;
        gp.obj[0].worldY = 25 * gp.tileSize;

        gp.obj[1] = new OBJ_Key(gp);
        gp.obj[1].worldX = 13 * gp.tileSize;
        gp.obj[1].worldY = 23 * gp.tileSize;

        gp.obj[2] = new OBJ_Chest(gp);
        gp.obj[2].worldX = 20 * gp.tileSize;
        gp.obj[2].worldY = 23 * gp.tileSize;

        gp.obj[3] = new OBJ_Door(gp);
        gp.obj[3].worldX = 15 * gp.tileSize;
        gp.obj[3].worldY = 23 * gp.tileSize;

        gp.obj[4] = new OBJ_Door(gp);
        gp.obj[4].worldX = 16 * gp.tileSize;
        gp.obj[4].worldY = 25 * gp.tileSize;
    }

    public void setNPC() {
        gp.npc[0] = new NPC_wizard(gp);
        gp.npc[0].worldx = 20 * gp.tileSize ;
        gp.npc[0].worldy = 20 * gp.tileSize ;
    }
}
