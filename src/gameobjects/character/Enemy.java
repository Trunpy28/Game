package gameobjects.character;

import static utils.Data.EnemyData.*;
import java.awt.Rectangle;

public abstract class Enemy extends GameCharacter {

    protected int aniIndex, enemyType;
    protected int aniSpeed = 20;
    protected int width = getWidth(enemyType), height = getHeight(enemyType);
    protected int velocity = getVelocity(enemyType);
    protected int limitX;
    protected int xMax;
    protected int xMin;
    protected Rectangle hitbox;
    protected Rectangle attackbox;
    protected int maxHealth;
    public int currHealth;
    protected int xDelta = getHitboxDeltaX(enemyType);
    protected int yDelta = getHitboxDeltaX(enemyType);
    protected int AttackDistance = getAttackDistance(enemyType);
    protected boolean isLeft = false;
    protected Player player;

    public Enemy(int x, int y, int health,int limitX, int xMax, int xMin, int enemyType) {
        super(x, y,health);
        this.limitX = limitX;
        this.xMax = xMax;
        this.xMin = xMin;
        this.enemyType = enemyType;
        this.maxHealth = getMaxHeath(enemyType);
        this.currHealth = maxHealth;
        initHitbox();
        initAttackbox();
    }

    protected void initHitbox() {
        hitbox = new Rectangle(x + xDelta, y + yDelta, width, height);
    }

    protected void initAttackbox() {
        if (!isLeft)
            attackbox = new Rectangle(x + xDelta, y + yDelta, width + AttackDistance, height);
        else
            attackbox = new Rectangle(x + xDelta - AttackDistance, y + yDelta, width, height);
    }

    protected void updateHitbox(int xDelta, int yDelta) {
        hitbox.x = x + xDelta;
        hitbox.y = y + yDelta;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public Rectangle GetAttackbox() {
        return attackbox;
    }

    public int getAniIndex() {
        return aniIndex;
    }

}
