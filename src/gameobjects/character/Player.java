package gameobjects.character;

import gamepanel.GamePanel;
import keyboard.PlayerHandle;

import java.awt.*;

public class Player extends GameCharacter {
    GamePanel gp;
    PlayerHandle ph;

    public Player(GamePanel gp, PlayerHandle ph) {
        this.gp = gp;
        this.ph = ph;

        setDefaultValues();
    }

    public void setDefaultValues() {
        this.x = 200;
        this.y = 200;
        this.speed = 2;
        this.states.add(PlayerState.RUN);
        this.states.add(PlayerState.IDLE);
    }

    public void update() {
        if (ph.upPressed || ph.downPressed || ph.leftPressed || ph.rightPressed) {
            if (ph.upPressed) {
                y -= speed;
            } else if (ph.downPressed) {
                y += speed;
            } else if (ph.leftPressed) {
                x -= speed;
            } else
                x += speed;
        }
        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum < PlayerState.RUN.getLstImg().length-1) {
                spriteNum++;
            } else spriteNum = 0;
            spriteCounter = 0;
        }
        else{
            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum < PlayerState.IDLE.getLstImg().length-1) {
                    spriteNum++;
                } else spriteNum = 0;
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        if (ph.upPressed || ph.downPressed || ph.leftPressed || ph.rightPressed)
            this.imgState = states.get(states.indexOf(PlayerState.RUN)).getLstImg();
        else this.imgState = states.get(states.indexOf(PlayerState.IDLE)).getLstImg();
        g2.drawImage(imgState[spriteNum], this.x, this.y, gp.tileWidth, gp.tileHeight, null);
    }
}
