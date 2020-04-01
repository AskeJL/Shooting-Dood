package shoot.doode.core.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import shoot.doode.common.data.entityparts.AssetPart;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.services.IEntityProcessingService;
import shoot.doode.common.services.IGamePluginService;
import shoot.doode.common.services.IPostEntityProcessingService;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.core.managers.GameInputProcessor;
import java.util.HashMap;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import shoot.doode.core.managers.AssetsJarFileResolver;

public class Game implements ApplicationListener {

    private HashMap<String, Sprite> spriteMap = new HashMap<>();
    private SpriteBatch batch;
    private static OrthographicCamera cam;
    private ShapeRenderer sr;
    private final Lookup lookup = Lookup.getDefault();
    private final GameData gameData = new GameData();
    private World world = new World();
    private List<IGamePluginService> gamePlugins = new CopyOnWriteArrayList<>();
    private Lookup.Result<IGamePluginService> result;

    @Override
    public void create() {
        batch = new SpriteBatch();
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

        result = lookup.lookupResult(IGamePluginService.class);
        result.addLookupListener(lookupListener);
        result.allItems();

        for (IGamePluginService plugin : result.allInstances()) {
            plugin.start(gameData, world);
            gamePlugins.add(plugin);
        }
        updateAssets();
    }

    @Override
    public void render() {
        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        gameData.setDelta(Gdx.graphics.getDeltaTime());
        gameData.getKeys().update();

        update();
        draw();
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }

        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    private void updateAssets() {
        spriteMap.clear();
        for (Entity entity : world.getEntities()) {
            AssetPart assets = entity.getPart(AssetPart.class);
            if (assets != null) {
                String jarPath = "shootindoode/modules/shoot-doode-";
                String module = assets.getModule();
                String imagePath = assets.getAssetPath();

                String inputPath = jarPath + module + ".jar!/Assets/" + imagePath;

                if (!spriteMap.containsKey(inputPath)) {
                    AssetsJarFileResolver jarfile = new AssetsJarFileResolver();
                    FileHandle file = jarfile.resolve(inputPath);
                    Texture texture = new Texture(file);
                    Sprite sprite = new Sprite(texture);
                    System.out.println("Touched the files");

                    spriteMap.put(inputPath, sprite);
                }

            }
        }
    }

    private void draw() {

        System.out.println("HashMap has: " + spriteMap.size());
        System.out.println("Gameplugins" + gamePlugins);
        System.out.println("Entity process" + getEntityProcessingServices());
        System.out.println("Post process" + getPostEntityProcessingServices());
        
        for (Entity entity : world.getEntities()) {
            AssetPart assets = entity.getPart(AssetPart.class);
            System.out.println(entity);
            if (assets != null) {

                String jarPath = "shootindoode/modules/shoot-doode-";
                String module = assets.getModule();
                String imagePath = assets.getAssetPath();

                String inputPath = jarPath + module + ".jar!/Assets/" + imagePath;

                Sprite sprite;
                if (spriteMap.containsKey(inputPath)) {
                    sprite = spriteMap.get(inputPath);
                } else {
                    updateAssets();
                    sprite = spriteMap.get(inputPath);
                }
                PositionPart positionPart = entity.getPart(PositionPart.class);

                sprite.setRotation(positionPart.getRotation());
                sprite.setPosition(positionPart.getX(), positionPart.getY());

                batch.begin();
                sprite.draw(batch);
                batch.end();

            } else {
                sr.setColor(1, 1, 1, 1);

                sr.begin(ShapeRenderer.ShapeType.Line);

                float[] shapex = entity.getShapeX();
                float[] shapey = entity.getShapeY();

                for (int i = 0, j = shapex.length - 1;
                        i < shapex.length;
                        j = i++) {

                    sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
                }

                sr.end();
            }

        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return lookup.lookupAll(IEntityProcessingService.class);
    }

    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return lookup.lookupAll(IPostEntityProcessingService.class);
    }

    private final LookupListener lookupListener = new LookupListener() {
        @Override
        public void resultChanged(LookupEvent le) {
       
            Collection<? extends IGamePluginService> updated = result.allInstances();
            

            for (IGamePluginService us : updated) {
                // Newly installed modules
                if (!gamePlugins.contains(us)) {
                    System.out.println("IGAMESTART");
                    us.start(gameData, world);
                    gamePlugins.add(us);
                }
            }

            // Stop and remove module
            for (IGamePluginService gs : gamePlugins) {
                if (!updated.contains(gs)) {
                    System.out.println("IGAMESTOP");
                    gs.stop(gameData, world);
                    gamePlugins.remove(gs);
                }
            }
        }

    };
}
