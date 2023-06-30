package gameobjects.character;

import static utils.Data.EnemyData.*;

import java.awt.Rectangle;

public abstract class Enemy extends GameCharacter {

    protected int aniIndex, enemyType;
    protected int aniSpeed = 20;
    protected int width, height;
    protected int velocity;
    protected int limitX;
    protected int xMax;
    protected int xMin;
    protected Rectangle hitbox;
    protected Rectangle attackbox;
    protected int maxHealth;
    public int currHealth;
    protected int xDelta;
    protected int yDelta;
    protected int AttackDistance;
    protected boolean isLeft = false;
    protected Player player;

    public Enemy(int x, int y, int health, int limitX, int xMax, int xMin, int enemyType) {
        super(x, y, health);
        this.limitX = limitX;
        this.xMax = xMax;
        this.xMin = xMin;
        this.enemyType = enemyType;
        this.maxHealth = getMaxHeath(enemyType);
        this.currHealth = maxHealth;
        width = getWidth(enemyType);
        height = getHeight(enemyType);
        velocity = getVelocity(enemyType);
        xDelta = getHitboxDeltaX(enemyType);
        yDelta = getHitboxDeltaX(enemyType);
        AttackDistance = getAttackDistance(enemyType);
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
            attackbox = new Rectangle(x + xDelta - AttackDistance, y + yDelta, width + AttackDistance, height);
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
