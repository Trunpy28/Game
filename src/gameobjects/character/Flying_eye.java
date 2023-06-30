package gameobjects.character;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import static utils.Data.EnemyData.*;

public class Flying_eye extends Enemy {

    private Flying_eyeState state = Flying_eyeState.RUN;
    private Player player;

    public Flying_eye(int x, int y, int limitX, int limitY, int xMax, int xMin, Player player) {
        super(x, y, 100,limitX, limitY, xMax, xMin, FLYING_EYE);
        this.player = player;
    }

    @Override
    public void update() {
        if (currHealth > 0) {
            if (Math.abs(player.getHitbox().x - hitbox.x) <= limitX && Math.abs(player.getHitbox().y - hitbox.y) <= limitY) {
                // # attackMode
                if (player.getHitbox().x <= hitbox.x)
                    isLeft = true;
                else
                    isLeft = false;
                if (isLeft) {
                    if (hitbox.intersects(player.getHitbox())) {
                        initAttackbox();
                        state = Flying_eyeState.ATTACK;

                    } else {
                        state = Flying_eyeState.RUN;
                        x -= velocity;
                    }
                } else {
                    if (hitbox.intersects(player.getHitbox())) {
                        initAttackbox();
                        state = Flying_eyeState.ATTACK;
                    } else {
                        state = Flying_eyeState.RUN;
                        x += velocity;
                    }
                }
            } else {
                // # basicMode
                state = Flying_eyeState.RUN;
                if (isLeft)
                    x -= velocity;
                else
                    x += velocity;

                if (x >= xMax)
                    isLeft = true;
                else if (x <= xMin)
                    isLeft = false;
            }
            if (hitbox.intersects(player.getAttackbox())) {
                state = Flying_eyeState.HURT;
                currHealth -= player.getDamage();
            }
        } else
            state = Flying_eyeState.DEATH;
        updateHitbox(xDelta, yDelta);
        if (state != state.ATTACK)
            attackbox.setBounds(0, 0, 0, 0);
    }

    @Override
    public void render(Graphics g2D) {
        g2D.setColor(Color.RED);
        g2D.drawRect(hitbox.x, hitbox.y, (int) hitbox.getWidth(), (int) hitbox.getHeight());
        g2D.drawRect(attackbox.x, attackbox.y, (int) attackbox.getWidth(), (int) attackbox.getHeight());
        g2D.setColor(Color.WHITE);
        BufferedImage sprite = state.getSpriteAtIdx(animationTick / 8);
        if (isLeft) {
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-sprite.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            sprite = op.filter(sprite, null);
        }
        g2D.drawImage(sprite, x, y, 150,150,null);
        animationTick++;
        animationTick %= state.getImgNum() * 8;
    }
}
