package gameobjects.character;


import gameobjects.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameCharacter extends GameObject {
  protected BufferedImage spriteSheet;
  protected int animationTick = 0;
  protected int health;
  protected int damage;
  protected Rectangle hitbox;
  protected Rectangle attackbox;
  protected boolean isLeft = false;
  public boolean isAttacked = false;

  public GameCharacter(int x, int y, int health) {
    super(x, y);
    this.health= health;
  }

  public void update() {
  }

  public void render(Graphics g){

  }

  public int getHealth() {
    return health;
  }

  public int getDamage() {
    return damage;
  }

  public Rectangle getHitbox() {
    return hitbox;
  }

  public Rectangle getAttackbox() {
    return attackbox;
  }
}
