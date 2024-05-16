package main;

import entity.NPC_wizard;
import monster.MOB_Eye;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        int mapNum = 0;

        gp.obj[mapNum][0] = new OBJ_Key(gp);
        gp.obj[mapNum][0].worldx = 8 * gp.tileSize;
        gp.obj[mapNum][0].worldy = 41 * gp.tileSize;

        gp.obj[mapNum][5] = new OBJ_Key(gp);
        gp.obj[mapNum][5].worldx = 9 * gp.tileSize;
        gp.obj[mapNum][5].worldy = 7 * gp.tileSize;

        gp.obj[mapNum][1] = new OBJ_Key(gp);
        gp.obj[mapNum][1].worldx = 40 * gp.tileSize;
        gp.obj[mapNum][1].worldy = 7 * gp.tileSize;

        /*gp.obj[mapNum][2] = new OBJ_Chest(gp);
        gp.obj[mapNum][2].worldx = 12 * gp.tileSize;
        gp.obj[mapNum][2].worldy = 8 * gp.tileSize;
        */

        /*gp.obj[mapNum][3] = new OBJ_Door(gp);
        gp.obj[mapNum][3].worldx = 12 * gp.tileSize;
        gp.obj[mapNum][3].worldy = 9 * gp.tileSize;

        gp.obj[mapNum][4] = new OBJ_Door(gp);
        gp.obj[mapNum][4].worldx = 22 * gp.tileSize;
        gp.obj[mapNum][4].worldy = 9 * gp.tileSize;

        gp.obj[mapNum][6] = new OBJ_Door(gp);
        gp.obj[mapNum][6].worldx = 22 * gp.tileSize;
        gp.obj[mapNum][6].worldy = 11 * gp.tileSize;
        */

    }

    public void setNPC() {

        int mapNum = 0;
        gp.npc[mapNum][0] = new NPC_wizard(gp);
        gp.npc[mapNum][0].worldx = 22 * gp.tileSize ;
        gp.npc[mapNum][0].worldy = 16 * gp.tileSize ;

    }

    public void setMonster() {

        int mapNum = 0;
        gp.monster[mapNum][0] = new MOB_Eye(gp);
        gp.monster[mapNum][0].worldx = 39 * gp.tileSize;
        gp.monster[mapNum][0].worldy = 29 * gp.tileSize;

        mapNum++;
        gp.monster[mapNum][1] = new MOB_Eye(gp);
        gp.monster[mapNum][1].worldx = 28 * gp.tileSize;
        gp.monster[mapNum][1].worldy = 20 * gp.tileSize;
    }
}
