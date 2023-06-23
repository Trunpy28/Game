package gameobjects.character;

import utils.PlayerHandle;

import java.awt.*;

public class Player extends GameCharacter {

    private final PlayerHandle ph = new PlayerHandle();
    private int velocity = 20;
    private PlayerState state = PlayerState.IDLE;
    private boolean rightTendency = true;

    public Player(int x, int y, int health) {
        super(x, y, health);
    }

    public void update() {
        if (ph.low) {
            if (health != 0) {
                health--;
                isAttacked = true;
            }
        }
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
            if (ph.rightPressed && !ph.upPressed && !ph.downPressed) {
                rightTendency = true;
                changeState(PlayerState.RUN);
                x += velocity;
            } else if (ph.leftPressed && !ph.upPressed && !ph.downPressed) {
                rightTendency = false;
                changeState(PlayerState.L_RUN);
                x -= velocity;
            } else if (ph.upPressed && !ph.downPressed) {
                if (rightTendency) {
                    changeState(PlayerState.JUMP);
                    if (ph.rightPressed) x += (velocity / 3);
                } else {
                    changeState(PlayerState.L_JUMP);
                    if (ph.leftPressed) x -= (velocity / 3);
                }
            } else if (ph.downPressed && !ph.upPressed) {
                if (rightTendency) {
                    changeState(PlayerState.SLIDE);
                    if (ph.rightPressed) x += (velocity / 3);
                } else {
                    changeState(PlayerState.L_SLIDE);
                    if (ph.leftPressed) x -= (velocity / 3);
                }
            } else if (((state == PlayerState.HURT || state == PlayerState.L_HURT) && animationTick == 2)
                    || (state != PlayerState.HURT && state != PlayerState.L_HURT)) {
                if (rightTendency) changeState(PlayerState.IDLE);
                else changeState(PlayerState.L_IDLE);
            }

            if (state == PlayerState.JUMP || state == PlayerState.L_JUMP) {
                if (animationTick > 0 && animationTick < 4) y -= 20;
                else if (animationTick > 3 && animationTick < 7) y += 20;
            }
        }
    }

    public void render(Graphics2D g2D) {
        if (health > 0) {
            animationTick++;
        } else {
            if (animationTick < 3) animationTick++;
        }

        g2D.drawImage(state.getSpriteAtIdx(animationTick), x, y, 128, 64, null);
    }

    public boolean checkSpecialState(PlayerState s) {
        return s != PlayerState.IDLE && s != PlayerState.L_IDLE
                && s != PlayerState.RUN && s != PlayerState.L_RUN
                && s != PlayerState.HURT && s != PlayerState.L_HURT;
    }

    public void changeState(PlayerState st) {
        if (checkSpecialState(state) && (animationTick == state.getImgNum() - 1)) {
            animationTick = 0;
            state = st;
        } else if (!checkSpecialState(state)) {
            if (state != st) {
                animationTick = 0;
                state = st;
            } else if (animationTick == state.getImgNum() - 1) {
                animationTick = 0;
            }
        }
    }

    public PlayerHandle getPh() {
        return ph;
    }
}
