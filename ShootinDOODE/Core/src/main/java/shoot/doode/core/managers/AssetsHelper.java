/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.core.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import java.util.HashMap;

/**
 *
 * @author tobia
 */
public class AssetsHelper {

    private HashMap<String, Sprite> spriteMap = new HashMap<>();
    private HashMap<String, Sound> soundMap = new HashMap<>();
    private String jarPath = "shootindoode/modules/shoot-doode-";
    private String imagePath = ".jar!/Assets/Images/";
    private String soundPath = ".jar!/Assets/Sounds/";

    public AssetsHelper() {

    }

    public Sprite getSprite(String module, String assetPath) {

        String inputPath = jarPath + module + imagePath + assetPath;

        return spriteMap.get(inputPath);
    }

    public Sound getSound(String module, String assetPath) {

        String inputPath = jarPath + module + soundPath + assetPath;

        return soundMap.get(inputPath);
    }

    public void loadImages(String[] paths) {

        if (paths != null) {
            for (String path : paths) {

                String inputPath = getImagePath(path);

                if (!spriteMap.containsKey(inputPath)) {
                    AssetsJarFileResolver jarfile = new AssetsJarFileResolver();
                    FileHandle file = jarfile.resolve(inputPath);
                    Texture texture = new Texture(file);
                    Sprite sprite = new Sprite(texture);

                    spriteMap.put(inputPath, sprite);
                }

            }
        }

    }

    public void loadSounds(String[] paths) {
        if (paths != null) {
            for (String path : paths) {
                String inputPath = getSoundPath(path);

                if (!spriteMap.containsKey(inputPath)) {
                    AssetsJarFileResolver jarfile = new AssetsJarFileResolver();
                    FileHandle file = jarfile.resolve(inputPath);
                    Sound sound = Gdx.audio.newSound(file);
                    soundMap.put(inputPath, sound);

                }

            }
        }
    }

    public void unLoadImages(String[] paths) {

        if (paths != null) {
            for (String path : paths) {

                String inputPath = getImagePath(path);

                if (spriteMap.containsKey(inputPath)) {
                    spriteMap.remove(inputPath);
                }
            }

        }
    }

    public void unLoadSounds(String[] paths) {

        if (paths != null) {
            for (String path : paths) {
                String inputPath = getSoundPath(path);

                if (soundMap.containsKey(inputPath)) {
                    soundMap.remove(inputPath);
                }
            }

        }
    }

    private String getImagePath(String splitAble) {

        String[] split = splitAble.split("!");
        String module = split[0];
        String assetPath = split[1];

        return jarPath + module + imagePath + assetPath;
    }

    private String getSoundPath(String splitAble) {
        String[] split = splitAble.split("!");
        String module = split[0];
        String assetPath = split[1];

        return jarPath + module + soundPath + assetPath;
    }

}
