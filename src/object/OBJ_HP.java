package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_HP extends Entity {



    public OBJ_HP(GamePanel gp) {
        super(gp);
        name = "HP";

        image = setup("/objects/HP_F");
        image2 = setup("/objects/HP_H");
        image3 = setup("/objects/HP_E");



    }
}
