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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author tobia
 */
public class AssetsHelper {

    private ArrayList<String> soundQueue = new ArrayList<>();
    private ArrayList<String> spriteQueue = new ArrayList<>();
    private HashMap<String, Sprite> spriteMap = new HashMap<>();
    private HashMap<String, Sound> soundMap = new HashMap<>();
    private String jarPath = "shootindoode/modules/shoot-doode-";
    private String imagePath = ".jar!/Assets/Images/";
    private String soundPath = ".jar!/Assets/Sounds/";
    private AssetsJarFileResolver jarfile = new AssetsJarFileResolver();
    
    private static AssetsHelper single_instance = null;

    private AssetsHelper() {

    }

    //Singleton
    public static AssetsHelper getInstance() {
        if (single_instance == null) {
            single_instance = new AssetsHelper();
        }
        return single_instance;
    }

    public Sprite getSprite(String module, String assetPath) {

        String inputPath = jarPath + module + imagePath + assetPath;

        return spriteMap.get(inputPath);
    }

    public Sound getSound(String module, String assetPath) {

        String inputPath = jarPath + module + soundPath + assetPath;

        return soundMap.get(inputPath);
    }
    
    public String getImageTotal() {
        String s = spriteMap.size() + " " + spriteQueue.size();
        return s;
    }

    public String getSoundTotal() {
        String s = soundMap.size() + " " + soundQueue.size();
        return s;
    }
    
    public void loadQueue()
    {
        for(String path : spriteQueue)
        {
            loadImages(path);
        }
            
        for(String path : soundQueue)
        {
            loadSounds(path);
        }
        spriteQueue.clear();
        soundQueue.clear();
    }
    
    public void loadImages(String path) {
        FileHandle file = jarfile.resolve(path);

        Texture texture = new Texture(file);
        Sprite sprite = new Sprite(texture);

        spriteMap.put(path, sprite);
    }

    public void queueImages(String[] paths) {

        if (paths != null) {
            for (String path : paths) {

                String inputPath = getImagePath(path);

                if (!spriteMap.containsKey(inputPath) && !spriteQueue.contains(inputPath)) {
                    spriteQueue.add(inputPath);

                }
 
            }

        }
    }

    public void loadSounds(String path) {
        FileHandle file = jarfile.resolve(path);
        Sound sound = Gdx.audio.newSound(file);
        soundMap.put(path, sound);
    }

    public void queueSounds(String[] paths) {
        if (paths != null) {
            for (String path : paths) {
                String inputPath = getSoundPath(path);

                if (!soundMap.containsKey(inputPath) && !soundQueue.contains(inputPath)) {
                    soundQueue.add(inputPath);
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
