package gameobjects.character;

import utils.PlayerHandle;

import java.awt.*;

public class Player extends GameCharacter {

    private final PlayerHandle ph = new PlayerHandle();
    private int velocity = 4;

    private PlayerState state = PlayerState.IDLE;
    private boolean rightTendency = true;

    public Player(int x, int y, int health) {

        super(x, y, health);
        initHitbox();
        initAttackbox();
    }

    protected void initAttackbox() {
        if (!isLeft)
            attackbox = new Rectangle(x + 55, y + 16,   50, 50);
        else
            attackbox = new Rectangle(x + 30, y + 16, 50, 50);
    }

    public void initHitbox() {
        hitbox = new Rectangle(x + 55,y + 16, 25, 50 );
    }
    public void updateHitbox() {
        if(state==PlayerState.SLIDE || state ==PlayerState.L_SLIDE)
            hitbox.setBounds(x+55,y+40,45,25);
        else hitbox.setBounds(x + 55,y + 16, 25, 50);
    }
    public Rectangle getHitbox() {
        return hitbox;
    }

    public void update() {

        if (health == 0) {
            if (state != PlayerState.DEATH && state != PlayerState.L_DEATH) {
                if (rightTendency) changeState(PlayerState.DEATH);
                else changeState(PlayerState.L_DEATH);
            }
        } else {
            if (isAttacked) {
                if (rightTendency) changeState(PlayerState.HURT);
                else changeState(PlayerState.L_HURT);
                isAttacked = false;
            }

            if (ph.attack) {
                if (rightTendency && ph.upPressed) changeState(PlayerState.ATTACK_FROM_AIR);
                else if (!rightTendency && ph.upPressed) changeState(PlayerState.L_ATTACK_FROM_AIR);
                else if (rightTendency) changeState(PlayerState.ATTACKS);
                else changeState(PlayerState.L_ATTACKS);
            } else if (ph.rightPressed && !ph.upPressed && !ph.downPressed) {
                rightTendency = true;
                changeState(PlayerState.RUN);
                x += velocity;
            } else if (ph.leftPressed && !ph.upPressed && !ph.downPressed) {
                rightTendency = false;
                changeState(PlayerState.L_RUN);
                x-=velocity;
            } else if (ph.upPressed && !ph.downPressed) {
                if (rightTendency) {
                    changeState(PlayerState.JUMP);
                } else {
                    changeState(PlayerState.L_JUMP);
                }
            } else if (ph.downPressed && !ph.upPressed) {
                if (rightTendency) {
                    changeState(PlayerState.SLIDE);
                } else {
                    changeState(PlayerState.L_SLIDE);
                }
            } else {
                if (rightTendency) changeState(PlayerState.IDLE);
                else changeState(PlayerState.L_IDLE);
            }

            if (state == PlayerState.JUMP || state == PlayerState.L_JUMP) {
                if (animationTick >= 8 && animationTick < 32) y -= 3;
                else if (animationTick >= 40 && animationTick < 64) y += 3;
            }

            if (state != state.ATTACKS && state != state.L_ATTACKS)
                attackbox.setBounds(0, 0, 0, 0);
            else initAttackbox();
            updateHitbox();
        }
    }

    public void render(Graphics2D g2D) {
        if (health > 0) {
            animationTick++;
        } else {
            if (animationTick < 31) animationTick++;
        }

        g2D.setColor(Color.RED);
        g2D.drawRect(hitbox.x, hitbox.y, (int) hitbox.getWidth(), (int) hitbox.getHeight());
        g2D.drawRect(attackbox.x, attackbox.y, (int) attackbox.getWidth(), (int) attackbox.getHeight());
        g2D.drawImage(state.getSpriteAtIdx(animationTick / 8), x, y, null);
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
