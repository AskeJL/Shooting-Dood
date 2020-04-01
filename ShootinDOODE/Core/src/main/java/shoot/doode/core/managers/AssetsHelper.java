/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.core.managers;

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
    private String jarPath = "shootindoode/modules/shoot-doode-";
    private String imagePath = ".jar!/Assets/Images/";
    private String soundPath = ".jar!/Assets/Sounds/";
    
    public AssetsHelper() {

    }

    public Sprite getSprite(String module,String assetPath) {
        
        String inputPath = jarPath + module + imagePath + assetPath;
        
        return spriteMap.get(inputPath);
    }

    public void loadImages(String[] paths) {

        if (paths != null) {
            for (String path : paths) {
                String[] split = path.split("!");
                String module = split[0];
                String assetPath = split[1];

                String inputPath = jarPath + module + imagePath + assetPath;

                if (!spriteMap.containsKey(inputPath)) {
                    AssetsJarFileResolver jarfile = new AssetsJarFileResolver();
                    FileHandle file = jarfile.resolve(inputPath);
                    System.out.println(file.exists());
                    Texture texture = new Texture(file);
                    Sprite sprite = new Sprite(texture);
                    System.out.println("Touched the files");

                    spriteMap.put(inputPath, sprite);
                }

            }
        }

    }

    public void unLoadImages(String[] paths) {
        if (paths != null) {
            for (String path : paths) {
                String[] split = path.split("!");
                String module = split[0];
                String assetPath = split[1];

                String inputPath = jarPath + module + imagePath + assetPath;

                if (spriteMap.containsKey(inputPath)) {
                    spriteMap.remove(inputPath);
                }
            }

        }
    }

}
