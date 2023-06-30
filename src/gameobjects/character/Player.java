package gameobjects.character;

import utils.PlayerHandle;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Player extends GameCharacter {

    private final PlayerHandle ph = new PlayerHandle();
    private int velocity = 2;

    private PlayerState state = PlayerState.IDLE;
    private boolean facingRight = true;

    public Player(int x, int y, int health) {

        super(x, y, health);
        initHitbox();
        initAttackbox();
    }

    protected void initAttackbox() {
        if (!facingRight)
            attackbox = new Rectangle(x + 55, y + 16,   50, 50);
        else
            attackbox = new Rectangle(x + 30, y + 16, 50, 50);
    }

    public void initHitbox() {
        hitbox = new Rectangle(x + 55,y + 16, 25, 50 );
    }
    public void updateHitbox() {
        if(state==PlayerState.SLIDE)
            hitbox.setBounds(x+55,y+40,45,25);
        else hitbox.setBounds(x + 55,y + 16, 25, 50);
    }

    public void update() {

        if (health == 0) {
            if (state != PlayerState.DEATH) {
                changeState(PlayerState.DEATH);
            }
        } else {
            if (isAttacked) {
                changeState(PlayerState.HURT);
                isAttacked = false;
            }

            if (ph.attack) {
                changeState(PlayerState.ATTACKS);
            } else if (ph.rightPressed && !ph.upPressed && !ph.downPressed) {
                facingRight = true;
                changeState(PlayerState.RUN);
                x += velocity;
            } else if (ph.leftPressed && !ph.upPressed && !ph.downPressed) {
                facingRight = false;
                changeState(PlayerState.RUN);
                x-=velocity;
            } else if (ph.upPressed && !ph.downPressed) {
                    changeState(PlayerState.JUMP);
            } else if (ph.downPressed && !ph.upPressed) {
                    changeState(PlayerState.SLIDE);
            } else {
                changeState(PlayerState.IDLE);
            }

            if (state == PlayerState.JUMP) {
                if (animationTick >= 8 && animationTick < 32) y -= 3;
                else if (animationTick >= 40 && animationTick < 64) y += 3;
            }

            if (state != PlayerState.ATTACKS)
                attackbox.setBounds(0, 0, 0, 0);
            else initAttackbox();
            updateHitbox();
        }
    }

    public void render(Graphics2D g2D) {
        g2D.setColor(Color.RED);
        g2D.drawRect(hitbox.x, hitbox.y, (int) hitbox.getWidth(), (int) hitbox.getHeight());
        g2D.drawRect(attackbox.x, attackbox.y, (int) attackbox.getWidth(), (int) attackbox.getHeight());
        BufferedImage sprite = state.getSpriteAtIdx(animationTick / 8);
        if (!facingRight) {
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-sprite.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            sprite = op.filter(sprite, null);
        }
        g2D.drawImage(sprite, x, y, 128, 64, null);
        animationTick++;
        animationTick %= state.getImgNum() * 8;
    }

    public void changeState(PlayerState st) {
        if ((PlayerState.stateLevel(st) <= PlayerState.stateLevel(this.state))
                && (PlayerState.stateLevel(this.state) > 1) && (animationTick == state.getImgNum() * 8 - 1)) {
            animationTick = 0;
            state = st;
        } else if (PlayerState.stateLevel(this.state) == 1
                || (PlayerState.stateLevel(st) > PlayerState.stateLevel(this.state))) {
            if (state != st) {
                animationTick = 0;
                state = st;
            } else if (animationTick == state.getImgNum() * 8 - 1) {
                animationTick = 0;
            }
        }
    }

    public PlayerHandle getPh() {
        return ph;
    }
}
