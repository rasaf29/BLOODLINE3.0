package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_HP extends SuperObject {

    GamePanel gp;

    public OBJ_HP(GamePanel gp) {
        this.gp = gp;
        name = "HP";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/HP_F.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/objects/HP_H.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/objects/HP_E.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            image2 = uTool.scaleImage(image2, gp.tileSize, gp.tileSize);
            image3 = uTool.scaleImage(image3, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
