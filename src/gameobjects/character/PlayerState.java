package gameobjects.character;

import utils.Loader;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public enum PlayerState {
    ATTACK_FROM_AIR("attack_from_air.png", 0,7),
    ATTACKS("Attacks.png", 0,20),
//    CLIMB("Climb.png", 0,6),
//    CROUCH_ATTACKS("crouch_attacks.png",0, 7),
//    CROUCH_IDLE("crouch_idle.png", 0,8),
    DEATH("Death.png", 0,4),
//    HANGING("Hanging.png", 0,8),
//    HEALTH("Health.png", 0,8),
    HURT("Hurt.png", 0,3),
    IDLE("Idle.png", 0,8),
    JUMP("Jump.png",0,8),
//    PRAY("Pray.png", 0,12),
//    ROLL("Roll.png",0,4),
    RUN("Run.png", 0,8),
    SLIDE("Slide.png", 0,10),

    L_ATTACK_FROM_AIR("L_attack_from_air.png", 0,7),
    L_ATTACKS("Attacks.png", 20,20),
    //L_CLIMB("Climb.png", 6),
    //L_CROUCH_ATTACKS("crouch_attacks.png", 7),
    //CROUCH_IDLE("crouch_idle.png", 8),
    L_DEATH("L_Death.png", 0,4),
    //L_HANGING("Hanging.png", 8),
    //L_HEALTH("Health.png", 8),
    L_HURT("L_Hurt.png", 0,3),
    L_IDLE("L_Idle.png", 0,8),
    L_JUMP("L_Jump.png",0 ,8),
    //L_PRAY("Pray.png", 12),
    //L_ROLL("Roll.png", 4),
    L_RUN("L_Run.png", 0,8),
    L_SLIDE("L_Slide.png", 0,10);

    private String animationPath = "/assets/images/characters/player/";

    private final String fileName;
    private BufferedImage spriteSheet;
    private List<BufferedImage> lstImg = new ArrayList<>();
    private int imgNum;

    PlayerState(String fileName, int start, int imgNum) {
        this.fileName = fileName;
        this.imgNum = imgNum;
        loadSpriteSheet();
        splitImg(start);
    }


    private void loadSpriteSheet() {
        String path = animationPath + fileName;
        spriteSheet = Loader.loadBufferedImage(path);
    }


    public void splitImg(int start) {
        int count = 0;
        int col = spriteSheet.getWidth() / 128;
        int row = spriteSheet.getHeight() / 64;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (count >= start && count < (start+imgNum))
                    lstImg.add(spriteSheet.getSubimage(j * 128, i * 64, 128, 64));
                count++;
            }
        }
    }

    public static int stateLevel(PlayerState s) {
        if (s == IDLE || s == L_IDLE || s == RUN || s == L_RUN) return 1;
        else if (s == HURT || s == L_HURT) return 2;
        else return 3;
    }

    public BufferedImage getSpriteAtIdx(int idx) {
        return lstImg.get(idx);
    }


    public int getImgNum() {
        return imgNum;
    }

    public List<BufferedImage> getLstImg() {
        return lstImg;
    }
}
