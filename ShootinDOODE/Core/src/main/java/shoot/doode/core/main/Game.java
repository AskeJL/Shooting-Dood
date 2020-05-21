package shoot.doode.core.main;

import shoot.doode.common.data.State;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import shoot.doode.common.data.entityparts.SpritePart;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.services.IEntityProcessingService;
import shoot.doode.common.services.IGamePluginService;
import shoot.doode.common.services.IPostEntityProcessingService;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.core.managers.GameInputProcessor;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import shoot.doode.common.data.GameKeys;
import shoot.doode.common.data.entityparts.MapPart;
import shoot.doode.common.data.entityparts.PlayerMovingPart;
import shoot.doode.common.data.entityparts.ProjectileMovingPart;
import shoot.doode.common.data.entityparts.SoundPart;
import shoot.doode.common.services.IAssetService;
import shoot.doode.common.services.IPowerUp;
import shoot.doode.commonenemy.Enemy;
import shoot.doode.core.managers.AssetsHelper;
import shoot.doode.core.managers.PowerUpManager;

public class Game extends ApplicationAdapter {

    private SpriteBatch batch;
    private static OrthographicCamera cam;
    private ShapeRenderer sr;
    private final Lookup lookup = Lookup.getDefault();
    private final GameData gameData = new GameData();
    private World world = new World();
    private List<IGamePluginService> gamePlugins = new CopyOnWriteArrayList<>();
    private Lookup.Result<IGamePluginService> iGameResult;
    private List<IAssetService> assetServices = new CopyOnWriteArrayList<>();
    private Lookup.Result<IAssetService> iAssetResult;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    BitmapFont font;
    private boolean newPausedValue;
    private boolean oldPausedValue;

    @Override
    public void create() {
        font = new BitmapFont();
        batch = new SpriteBatch();
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());
        gameData.setState(State.RUN);
        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.position.set(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2, 0);

        sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

        iGameResult = lookup.lookupResult(IGamePluginService.class);
        iGameResult.addLookupListener(lookupListener);
        iGameResult.allItems();

        iAssetResult = lookup.lookupResult(IAssetService.class);
        iAssetResult.addLookupListener(lookupListener);
        iAssetResult.allItems();

        for (IGamePluginService plugin : iGameResult.allInstances()) {
            plugin.start(gameData, world);
            gamePlugins.add(plugin);
        }

        for (IAssetService assetService : iAssetResult.allInstances()) {
            AssetsHelper.getInstance().queueMaps(assetService.loadMaps());
            AssetsHelper.getInstance().queueImages(assetService.loadImages());
            AssetsHelper.getInstance().queueSounds(assetService.loadSounds());
            assetServices.add(assetService);
        }
    }

    @Override
    public void render() {
    
    gameData.setDelta(Gdx.graphics.getDeltaTime());
    gameData.getKeys().update();
    gameData.setScore(gameData.getScore() - gameData.getDelta());
    
    switch (gameData.getState()) {
            
            case MAINMENU:
                
                break;
            
            
            case START:
                break;

            case RUN:
                
                // clear screen to black
                Gdx.gl.glClearColor(0, 0, 0, 1);
    
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

                update();
                draw();
                newPausedValue = gameData.getKeys().isDown(GameKeys.ESCAPE);
                if (newPausedValue != oldPausedValue && newPausedValue) {
                    gameData.setState(State.PAUSE);
                }
                oldPausedValue = newPausedValue;
                break;

            case PAUSE:
                newPausedValue = gameData.getKeys().isDown(GameKeys.ESCAPE);
                if (newPausedValue != oldPausedValue && newPausedValue) {
                    gameData.setState(State.RUN);
                }
                oldPausedValue = newPausedValue;
                break;

            case RESUME:
                break;

            case STOP:
                break;

        default:
            break;
        }
    } 

    private void update() {
        
        PowerUpManager.getInstance().process(gameData,world,getPowerUpService());
        
        // Update
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {

            entityProcessorService.process(gameData, world);

            
        }

        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    private void draw() {

        for (Entity entity : world.getEntities()) {
            MapPart mapPart = entity.getPart(MapPart.class);
            if (mapPart != null) {
                for (String totalPath : AssetsHelper.getInstance().getMapMapKeys()) {
                    if (AssetsHelper.getInstance().getMap(totalPath) == null) {
                        AssetsHelper.getInstance().loadMaps(totalPath);
                        System.out.println("Loaded map at: " + totalPath);
                
                        String module = mapPart.getModule();
                        String mapPath = mapPart.getMapPath();
                        map = AssetsHelper.getInstance().getMap(module, mapPath);
                        renderer = new OrthogonalTiledMapRenderer(map, batch);
                        break;
            }
        }
            }
        }
        cam.update();
        for (Entity entity : world.getEntities()) {
            MapPart mapPart = entity.getPart(MapPart.class);
            if (mapPart != null && renderer != null) {
                renderer.setView(cam);
                renderer.render();
                renderer.getBatch();
                break;
            }else{
                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
            }}
        AssetsHelper.getInstance().loadQueue();
        

        

        for (Entity entity : world.getEntities()) {
            
            PlayerMovingPart pmp = entity.getPart(PlayerMovingPart.class);
            if (pmp != null) {
                cam.position.set(pmp.getX(entity), pmp.getY(entity), 0);
            }

            SpritePart spritePart = entity.getPart(SpritePart.class);
            if (spritePart != null) {
                if(!spritePart.isInvis())
                {
                String module = spritePart.getModule();
                String imagePath = spritePart.getSpritePath();

                Sprite sprite = AssetsHelper.getInstance().getSprite(module, imagePath);
                PositionPart positionPart = entity.getPart(PositionPart.class);
                sprite.setRotation(positionPart.getRotation());
                sprite.setPosition(positionPart.getX() - sprite.getWidth() / 2, positionPart.getY() - sprite.getHeight() / 2);
                
                ProjectileMovingPart projektileMovingPart = entity.getPart(ProjectileMovingPart.class);
                if(projektileMovingPart != null)
                {
                    float rotation = positionPart.getRotation();
                    sprite.setRotation((float)Math.toDegrees(rotation));
                }
                
                batch.begin();
                sprite.draw(batch);
                batch.end();
                }
            }

            SoundPart soundPart = entity.getPart(SoundPart.class);
            if (soundPart != null) {
                String module = soundPart.getModule();

                for (String soundPath : soundPart.getSoundPaths()) {
                    Sound sound = AssetsHelper.getInstance().getSound(module, soundPath);
                    sound.play();
                    soundPart.setPlay(soundPath, false);
                }
            }
        }
        batch.begin();
        font.draw(batch, "Score: " + (int)gameData.getScore(),0 , 0);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        //this.state = State.PAUSE;
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
    
    private Collection<? extends IPowerUp> getPowerUpService() {
        return lookup.lookupAll(IPowerUp.class);
    }
    

    private final LookupListener lookupListener = new LookupListener() {
        @Override
        public void resultChanged(LookupEvent le) {

            Collection<? extends IGamePluginService> iGameUpdated = iGameResult.allInstances();
            Collection<? extends IAssetService> iAssetUpdated = iAssetResult.allInstances();

            for (IAssetService us : iAssetUpdated) {
                // Newly installed modules
                if (!assetServices.contains(us)) {
                    System.out.println("New AssetLoader: " + us);
                    AssetsHelper.getInstance().queueImages(us.loadImages());
                    AssetsHelper.getInstance().queueSounds(us.loadSounds());
                    assetServices.add(us);
                }
            }

            // Stop and remove module
            for (IAssetService gs : assetServices) {
                if (!iAssetUpdated.contains(gs)) {
                    System.out.println("Remove AssetLoader: " + gs);
                    AssetsHelper.getInstance().unLoadImages(gs.unLoadImages());
                    AssetsHelper.getInstance().unLoadSounds(gs.unLoadSounds());
                    assetServices.remove(gs);
                }
            }

            for (IGamePluginService us : iGameUpdated) {
                // Newly installed modules
                if (!gamePlugins.contains(us)) {
                    us.start(gameData, world);
                    gamePlugins.add(us);
                }
            }

            // Stop and remove module
            for (IGamePluginService gs : gamePlugins) {
                if (!iGameUpdated.contains(gs)) {
                    gs.stop(gameData, world);
                    gamePlugins.remove(gs);
                }
            }
        }
    };
}
