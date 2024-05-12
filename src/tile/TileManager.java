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

    public int mapTileNum[][];
    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10];
        mapTileNum = new int [gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/world01.txt");
    }

    public void getTileImage() {


            setup(0, "tile_1", true);
            /*tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tile_1.png")); // tile
            tile[0].collision = true;*/


            setup(1, "tile_3", true);
            /*tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tile_3.png")); // tile
            tile[1].collision = true;*/

            setup(2, "tile_2", false);
            /*tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tile_2.png")); // tile pe care merg!!*/

            setup(3, "prop_1", true);
            /*tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/prop_1.png")); // prop
            tile[3].collision = true;*/

            setup(4, "prop_2", true);
            /*tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/prop_2.png")); // prop
            tile[4].collision = true;*/

            setup(5, "prop_3", true);
            /*tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/prop_3.png")); // prop
            tile[5].collision = true;*/

            setup(6, "tile_4", false);
            /*tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tile_4.png")); // tile*/


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

    public void loadMap(String filePath) {
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

                    mapTileNum[col][row] = num;
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

                int tileNum = mapTileNum[worldCol][worldRow];

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
