/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import shoot.doode.common.data.GameData;
import shoot.doode.core.managers.AssetsHelper;


public class Background {
	private TiledMap map; 
	private OrthogonalTiledMapRenderer renderer;
	private float scale;
        private GameData data = new GameData();
        String module = "Map";
        String[] mapPaths = new String[1];
        AssetsHelper ah = new AssetsHelper();
	
	public Background() {
            mapPaths[0] = "map.tmx";
            File file = new File("C:\\Users\\askel\\OneDrive\\Shooting-Dood\\ShootinDOODE\\application\\target\\shootindoode\\assets\\map.tmx");
            if (!file.isDirectory())
                file = file.getParentFile();
            if (file.exists()){
                System.out.println("EXITS!!!");
                }
		scale = data.getDisplayWidth() / 1205f;
		map = ah.getMap(module, mapPaths[0]);
		renderer = new OrthogonalTiledMapRenderer(map,scale);
	}

	public void update() {
	}

	public void render(OrthographicCamera camera) {
		renderer.setView(camera);
		renderer.render();
	}

	public void dispose() {
		map.dispose();
		renderer.dispose();
	}

	public TiledMap getMap() {
		return map;
	}

	public void setMap(TiledMap map) {
		this.map = map;
	}

	public OrthogonalTiledMapRenderer getRenderer() {
		return renderer;
	}

	public void setRenderer(OrthogonalTiledMapRenderer renderer) {
		this.renderer = renderer;
	}
}