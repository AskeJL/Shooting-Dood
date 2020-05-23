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
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;

/**
 *
 * @author tobia
 */
public class AssetsHelper {

    private PriorityQueue<String> soundQueue = new PriorityQueue<>();
    private PriorityQueue<String> spriteQueue = new PriorityQueue<>();
    private HashMap<String, Sprite> spriteMap = new HashMap<>();
    private HashMap<String, Sound> soundMap = new HashMap<>();
    private HashMap<String, TiledMap> mapMap = new HashMap<>();
    private String jarPath = "shootindoode/modules/shoot-doode-";
    private String spritePath = ".jar!/Assets/Images/";
    private String soundPath = ".jar!/Assets/Sounds/";
    private String mapPath = ".jar!/Assets/Maps/";
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

        String inputPath = jarPath + module + spritePath + assetPath;

        return spriteMap.get(inputPath);
    }

    public Sound getSound(String module, String assetPath) {

        String inputPath = jarPath + module + soundPath + assetPath;

        return soundMap.get(inputPath);
    }
    
     public TiledMap getMap(String module, String assetPath) {

        String inputPath = jarPath + module + mapPath + assetPath;

        return mapMap.get(inputPath);
    }
    
    public TiledMap getMap(String totalAssetPath) {

        return mapMap.get(totalAssetPath);
    }
    
    
    public void loadQueue()
    {
        while(!spriteQueue.isEmpty())
        {
            loadSprites(spriteQueue.poll());
        }
        
        while(!soundQueue.isEmpty())
        {
            loadSounds(soundQueue.poll());
        }
    }
    
    public int getMapTotal() {

        return mapMap.size();
    }

    public void loadSprites(String path) {
        FileHandle file = new JarFileHandleStream(path);
        Texture texture = new Texture(file);
        Sprite sprite = new Sprite(texture);

        spriteMap.put(path, sprite);
    }

    public void queueSprites(String[] paths) {

        if (paths != null) {
            for (String path : paths) {

                String inputPath = getSpritePath(path);

                if (!spriteMap.containsKey(inputPath) && !spriteQueue.contains(inputPath)) {
                    spriteQueue.add(inputPath);

                }
 
            }

        }
    }

    public void loadSounds(String path) {
        FileHandle file = new JarFileHandleStream(path);
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

    public void unLoadSprites(String[] paths) {

        if (paths != null) {
            for (String path : paths) {

                String inputPath = getSpritePath(path);

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

    private String getSpritePath(String splitAble) {

        String[] split = splitAble.split("!");
        String module = split[0];
        String assetPath = split[1];

        return jarPath + module + spritePath + assetPath;
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
