/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.core.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
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
    private HashMap<String, TiledMap> mapMap = new HashMap<>();
    private String jarPath = "shootindoode/modules/shoot-doode-";
    private String imagePath = ".jar!/Assets/Images/";
    private String soundPath = ".jar!/Assets/Sounds/";
    private String mapPath = ".jar!/Assets/Maps/";
    private AssetsJarFileResolver jarfile = new AssetsJarFileResolver();;
    private AssetManager manager;
    
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
    
    public Set<String> getSoundMapKeys()
    {
        return soundMap.keySet();
    }
    
    public Set<String> getMapMapKeys(){
        return mapMap.keySet();
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
    
     public TiledMap getMap(String module, String assetPath) {

        String inputPath = jarPath + module + mapPath + assetPath;

        return mapMap.get(inputPath);
    }
    
    public TiledMap getMap(String totalAssetPath) {

        return mapMap.get(totalAssetPath);
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
    
    public int getMapTotal() {

        return mapMap.size();
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
    
    public void loadMaps(String path) {
        FileHandle file = jarfile.resolve(path);
        boolean exists = file.exists();
        System.out.println(exists);
        manager = new AssetManager(new ExternalFileHandleResolver());
        manager.setLoader(TiledMap.class, new TmxMapLoader());
        String currentPath = System.getProperty("user.dir");
        String subPath = currentPath.substring(0, currentPath.lastIndexOf("ShootinDOODE"));
        String absolutePath = subPath + "ShootinDOODE\\Map\\src\\main\\resources\\Assets\\Maps\\map.tmx";
        manager.load(absolutePath, TiledMap.class);
        manager.finishLoading();
        TiledMap map = manager.get(absolutePath.replaceAll("\\\\", "/"), TiledMap.class);
        
        mapMap.replace(path, map);
    }
    
    public void queueMaps(String[] paths) {

        if (paths != null) {
            for (String path : paths) {

                String inputPath = getMapPath(path);

                if (!mapMap.containsKey(inputPath)) {

                    mapMap.put(inputPath, null);
                } else {
                }

            }
        }

    }
    
    public void unLoadMaps(String[] paths) {

        if (paths != null) {
            for (String path : paths) {

                String inputPath = getMapPath(path);

                if (mapMap.containsKey(inputPath)) {
                    mapMap.remove(inputPath);
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
    
    private String getMapPath(String splitAble) {

        String[] split = splitAble.split("!");
        String module = split[0];
        String assetPath = split[1];

        return jarPath + module + mapPath + assetPath;
    }

}
