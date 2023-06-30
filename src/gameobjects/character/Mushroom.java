package gameobjects.character;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import static utils.Data.EnemyData.*;

public class Mushroom extends Enemy {

    private MushroomState state = MushroomState.RUN;
    private Player player;

    public Mushroom(int x, int y, int limitX, int xMax, int xMin, Player player) {
        super(x, y, 100, limitX, xMax, xMin, MUSHROOM);
        this.player = player;
    }

    @Override
    public void update() {
        if (currHealth > 0) {
            if (Math.abs(player.getX() + 66 - x) <= limitX) {
                // # attackMode
                if (player.getX() + 66 <= x)
                    isLeft = true;
                else
                    isLeft = false;
                if (isLeft) {
                    if (x - player.getX() - 66 <= 30) {
                        initAttackbox();
                        state = MushroomState.ATTACK;

                    } else {
                        state = MushroomState.RUN;
                        x -= velocity;
                    }
                } else {
                    if (player.getX() + 66 - x <= getWidth(enemyType) + getAttackDistance(enemyType)) {
                        initAttackbox();
                        state = MushroomState.ATTACK;
                    } else {
                        state = MushroomState.RUN;
                        x += velocity;
                    }
                }
            } else if (Math.abs(player.getX() + 66 - x) > limitX) {
                // # basicMode
                state = MushroomState.RUN;
                if (isLeft)
                    x -= velocity;
                else
                    x += velocity;

                if (x >= xMax)
                    isLeft = true;
                else if (x <= xMin)
                    isLeft = false;
            }
//            if (hitbox.intersects(player.getAttackbox())) {
//                state = MushroomState.HURT;
//                currHealth -= player.getDamage();
//            }
        } else
            state = MushroomState.DEATH;
        updateHitbox(xDelta, yDelta);
        if (state != MushroomState.ATTACK) attackbox.setBounds(0, 0, 0, 0);
    }

    @Override
    public void render(Graphics g2D) {
        g2D.setColor(Color.RED);
        g2D.drawRect(attackbox.x, attackbox.y, (int) attackbox.getWidth(), (int) attackbox.getHeight());
        g2D.setColor(Color.WHITE);
        BufferedImage sprite = state.getSpriteAtIdx(animationTick / 8);
        if (isLeft) {
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-sprite.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            sprite = op.filter(sprite, null);
        }
        g2D.drawImage(sprite, x, y, 150, 150, null);
        animationTick++;
        animationTick %= state.getImgNum() * 8;
    }
}
