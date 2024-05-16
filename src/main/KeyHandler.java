package main;

import entity.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;

    boolean check  = false;
    GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 2;
                }
            }

            if (code == KeyEvent.VK_ENTER) {
             if (gp.ui.commandNum == 0) {
                 gp.gameState = gp.playState;
             }
             if (gp.ui.commandNum == 1) {

             }
             if (gp.ui.commandNum == 2) {
                 System.exit(0);
             }
            }
        }
        else if (gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_E) {
                enterPressed = true;
            }
            if (code == KeyEvent.VK_T) {
                if (check == false) {
                    check = true;
                }
                else if (check == true) {
                    check = false;
                }
            }

        }
            if (code == KeyEvent.VK_P) {
                if (gp.gameState == gp.playState) {
                    gp.gameState = gp.pauseState;
                } else if (gp.gameState == gp.pauseState) {
                    gp.gameState = gp.playState;
                }
            }

            else if (gp.gameState == gp.dialogueState) {
                 if (code == KeyEvent.VK_E) {
                     gp.gameState = gp.playState;
                 }
                 if (code == KeyEvent.VK_ENTER) {

                 }
                }
            else if (gp.gameState == gp.gameOverState) {
                gameOverState(code);
            }

    }

    public void gameOverState(int code) {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 1) {
                gp.ui.commandNum = 1;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
                gp.retry();
            }
            else if (gp.ui.commandNum == 1) {
                gp.gameState = gp.titleState;
                gp.restart();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }

    }
}
