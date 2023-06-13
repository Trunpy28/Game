package gameobjects.character;


import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class GameCharacter {
    public int x, y;
    public int speed;
    BufferedImage[] imgState = null;

    public int spriteCounter = 0;
    public int spriteNum = 0;

    public LinkedList<PlayerState> states = new LinkedList<>();
}
