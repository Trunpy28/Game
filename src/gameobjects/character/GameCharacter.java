package gameobjects.character;


import gameobjects.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameCharacter extends GameObject {
  protected BufferedImage spriteSheet;
  protected int animationTick = 0;
  protected int health;
  public boolean isAttacked = false;

  public GameCharacter(int x, int y, int health) {
    super(x, y);
    this.health= health;
  }

  public void update() {
  }

  public void render(Graphics g){

  } 
}
