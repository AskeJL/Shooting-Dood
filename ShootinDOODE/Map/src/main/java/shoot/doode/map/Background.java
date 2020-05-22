package shoot.doode.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import shoot.doode.common.data.Entity;


public class Background extends Entity{
	private TiledMap map; 
	private OrthogonalTiledMapRenderer renderer;
        String module = "Map";
        String[] mapPaths = new String[1];
	
	public Background() {
            mapPaths[0] = "map.tmx";
            		
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