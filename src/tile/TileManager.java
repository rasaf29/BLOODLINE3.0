package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;

    public int mapTileNum[][][];
    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[20];
        mapTileNum = new int [gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/world01.txt", 0);
        loadMap("/maps/map01.txt", 1);
    }

    public void getTileImage() {

            setup(0, "tile_1", true);
            setup(1, "tile_3", true);
            setup(2, "tile_2", false);
            setup(3, "prop_1", true);
            setup(4, "prop_2", true);
            setup(5, "prop_3", true);
            setup(6, "tile_4", false);
            setup(7, "usa", false);

    }

    public void setup(int index, String imagePath, boolean collision) {
        UtilityTool uTool = new UtilityTool();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void loadMap(String filePath, int map) {
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;
            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = br.readLine();
                while(col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }

            }
                br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2) {
            int worldCol = 0;
            int worldRow = 0;


            while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

                int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

                int worldX = worldCol * gp.tileSize;
                int worldY = worldRow * gp.tileSize;
                int screenX = worldX - gp.player.worldx + gp.player.screenX;
                int screenY = worldY - gp.player.worldy + gp.player.screenY;

                if(worldX + gp.tileSize > gp.player.worldx - gp.player.screenX && worldX - gp.tileSize < gp.player.worldx + gp.player.screenX && worldY  + gp.tileSize > gp.player.worldy - gp.player.screenY &&
                                worldY - gp.tileSize < gp.player.worldy + gp.player.screenY) {
                    g2.drawImage( tile[tileNum].image,screenX, screenY,null);
                }


                worldCol++;

                if (worldCol == gp.maxWorldCol) {
                    worldCol = 0;

                    worldRow++;

                }
            }
    }
}
