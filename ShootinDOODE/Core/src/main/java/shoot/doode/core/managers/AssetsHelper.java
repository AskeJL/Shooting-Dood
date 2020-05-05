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
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author tobia
 */
public class AssetsHelper {

    private HashMap<String, Sprite> spriteMap = new HashMap<>();
    private HashMap<String, Sound> soundMap = new HashMap<>();
    private HashMap<String, TiledMap> mapMap = new HashMap<>();
    private String jarPath = "shootindoode/modules/shoot-doode-";
    private String imagePath = ".jar!/Assets/Images/";
    private String soundPath = ".jar!/Assets/Sounds/";
    private String mapPath = ".jar!/Assets/Maps/";
    private AssetsJarFileResolver jarfile = new AssetsJarFileResolver();;
    private AssetManager manager;

    public AssetsHelper() {

    }

    public Set<String> getSpriteMapKeys()
    {
        return spriteMap.keySet();
    }
    
    public Set<String> getSoundMapKeys()
    {
        return soundMap.keySet();
    }
    
    public Set<String> getMapMapKeys(){
        return mapMap.keySet();
    }
    
    
    public Sprite getSprite(String toalAssetPath) {

        return spriteMap.get(toalAssetPath);
    }
    
    public Sprite getSprite(String module, String assetPath) {

        String inputPath = jarPath + module + imagePath + assetPath;
        
        return spriteMap.get(inputPath);
    }

    public Sound getSound(String module, String assetPath) {

        String inputPath = jarPath + module + soundPath + assetPath;

        return soundMap.get(inputPath);
    }
    
    public Sound getSound(String totalAssetPath) {

        return soundMap.get(totalAssetPath);
    }
    
     public TiledMap getMap(String module, String assetPath) {

        String inputPath = jarPath + module + mapPath + assetPath;

        return mapMap.get(inputPath);
    }
    
    public TiledMap getMap(String totalAssetPath) {

        return mapMap.get(totalAssetPath);
    }

    public int getImageTotal() {
        return spriteMap.size();
    }

    public int getSoundTotal() {

        return soundMap.size();
    }
    
    public int getMapTotal() {

        return mapMap.size();
    }

    public void loadImages(String path) {
        FileHandle file = jarfile.resolve(path);

        Texture texture = new Texture(file);
        Sprite sprite = new Sprite(texture);

        spriteMap.replace(path, sprite);
    }

    public void queueImages(String[] paths) {

        if (paths != null) {
            for (String path : paths) {

                String inputPath = getImagePath(path);

                if (!spriteMap.containsKey(inputPath)) {

                    spriteMap.put(inputPath, null);
                } else {
                }

            }
        }

    }

    public void loadSounds(String path) {
        FileHandle file = jarfile.resolve(path);
        Sound sound = Gdx.audio.newSound(file);
        soundMap.replace(path, sound);
    }

    public void queueSounds(String[] paths) {
        if (paths != null) {
            for (String path : paths) {
                String inputPath = getSoundPath(path);

                if (!spriteMap.containsKey(inputPath)) {
                    soundMap.put(inputPath, null);
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
        manager.load("C:\\Users\\askel\\OneDrive\\Shooting-Dood\\ShootinDOODE\\Map\\src\\main\\resources\\assets\\Maps\\map.tmx", TiledMap.class);
        manager.finishLoading();
        System.out.println(manager.getLoadedAssets());
        System.out.println(manager.getAssetNames());
        TiledMap map = manager.get("C:/Users/askel/OneDrive/Shooting-Dood/ShootinDOODE/Map/src/main/resources/assets/Maps/map.tmx", TiledMap.class);
        

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
