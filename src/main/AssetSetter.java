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
        gp.obj[0].worldx = 8 * gp.tileSize;
        gp.obj[0].worldy = 41 * gp.tileSize;

        gp.obj[5] = new OBJ_Key(gp);
        gp.obj[5].worldx = 9 * gp.tileSize;
        gp.obj[5].worldy = 7 * gp.tileSize;

        gp.obj[1] = new OBJ_Key(gp);
        gp.obj[1].worldx = 40 * gp.tileSize;
        gp.obj[1].worldy = 7 * gp.tileSize;

        gp.obj[2] = new OBJ_Chest(gp);
        gp.obj[2].worldx = 12 * gp.tileSize;
        gp.obj[2].worldy = 7 * gp.tileSize;

        gp.obj[3] = new OBJ_Door(gp);
        gp.obj[3].worldx = 12 * gp.tileSize;
        gp.obj[3].worldy = 9 * gp.tileSize;

        gp.obj[4] = new OBJ_Door(gp);
        gp.obj[4].worldx = 22 * gp.tileSize;
        gp.obj[4].worldy = 9 * gp.tileSize;

        gp.obj[6] = new OBJ_Door(gp);
        gp.obj[6].worldx = 22 * gp.tileSize;
        gp.obj[6].worldy = 11 * gp.tileSize;
    }

    public void setNPC() {
        gp.npc[0] = new NPC_wizard(gp);
        gp.npc[0].worldx = 22 * gp.tileSize ;
        gp.npc[0].worldy = 16 * gp.tileSize ;


    }
}
