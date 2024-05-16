package main;

import java.awt.*;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][][];
    int eventRectDefaultX, eventRectDefaultY;

    boolean canTouchEvent = true;

    int previousEventX, previousEventY;
    public EventHandler(GamePanel gp) {

        this.gp = gp;

        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;
        while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {

            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;

                if (row == gp.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }

    }

    public void checkEvent() {

            //Verific daca sunt cu 1 tile inafara tile-ului de event
            int xDistance = Math.abs(gp.player.worldx - previousEventX);
            int yDistance = Math.abs(gp.player.worldy - previousEventY);
            int distance = Math.max(xDistance, yDistance);
            if (distance > gp.tileSize) {
                canTouchEvent = true;
            }

            if (canTouchEvent == true) {
                if (hit(0, 26, 12, "any") == true) {
                    teleport(gp.playState);
                } else if (hit(0, 12, 7, "any") == true) {
                    teleportToMap(1, 37, 12);
                } else if (hit(1, 37, 12, "any") == true) {
                    teleportToMap(0, 12, 7);
                }
            }

    }

    //Verificare colizune cu event-urile din joc(de ex tile de teleport folosit si la tranzitia dintre mape)
    public boolean hit(int map, int col, int row, String regDirection) {

        boolean hit = false;
        if(map == gp.currentMap) {

            gp.player.solidArea.x = gp.player.worldx + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldy + gp.player.solidArea.y;
            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

            if (gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {
                if (gp.player.direction.contentEquals(regDirection) || regDirection.contentEquals("any")) {
                    hit = true;
                    previousEventX = gp.player.worldx;
                    previousEventY = gp.player.worldy;
                }
            }

            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }

        return hit;
    }

   public void teleport(int gameState) {
        gp.gameState = gameState;
        gp.player.worldx = gp.tileSize * 10;
        gp.player.worldy = gp.tileSize * 7;
        canTouchEvent = false;
   }

   public void teleportToMap(int map, int col, int row) {

        gp.currentMap = map;
        gp.player.worldx = gp.tileSize * col;
        gp.player.worldy = gp.tileSize * row;
        previousEventX = gp.player.worldx;
        previousEventY = gp.player.worldy;
        canTouchEvent = false;
   }
}
