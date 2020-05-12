package shoot.doode.core.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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
import shoot.doode.common.data.entityparts.SoundPart;
import shoot.doode.common.services.IAssetService;
import shoot.doode.common.services.IGameStateService;
import shoot.doode.core.managers.AssetsHelper;

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

    private State state = State.RUN;
    private boolean newPausedValue;
    private boolean oldPausedValue;

    @Override
    public void create() {
        batch = new SpriteBatch();
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        //cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.position.set(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2, 0);
        //cam.update();

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
        for (String totalPath : AssetsHelper.getInstance().getMapMapKeys()) {
            if (AssetsHelper.getInstance().getMap(totalPath) == null) {
                AssetsHelper.getInstance().loadMaps(totalPath);
                System.out.println("Loaded map at: " + totalPath);
            } else {
                System.out.println("Map was already loaded at: " + totalPath);
            }
        }
        for (Entity entity : world.getEntities()) {
            MapPart mapPart = entity.getPart(MapPart.class);
            if (mapPart != null) {
                String module = mapPart.getModule();
                String mapPath = mapPart.getMapPath();
                map = AssetsHelper.getInstance().getMap(module, mapPath);
                System.out.println(map);
                renderer = new OrthogonalTiledMapRenderer(map, batch);

            }
        }

    }

    @Override
    public void render() {
        switch (state) {
            case START:
                break;

            case RUN:
                if (gameData.getKeys().isPressed(GameKeys.ESCAPE)) {
                    System.out.println("Enter pressed");
                    pause();
                }
                // clear screen to black
                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

                gameData.setDelta(Gdx.graphics.getDeltaTime());
                gameData.getKeys().update();

                update();
                draw();
                break;

            case PAUSE:
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                for (IGameStateService menu : getGameStateServices()) {
                    System.out.println(menu);
                    menu.render(batch);
                }
                System.out.println("Paused");

                newPausedValue = gameData.getKeys().isDown(GameKeys.ESCAPE);
                if (newPausedValue != oldPausedValue && newPausedValue) {
                    setGameState(State.RUN);
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

        for (String totalPath : AssetsHelper.getInstance().getMapMapKeys()) {
            if (AssetsHelper.getInstance().getMap(totalPath) == null) {
                AssetsHelper.getInstance().loadMaps(totalPath);
                System.out.println("Loaded map at: " + totalPath);
            } else {
                System.out.println("Map was already loaded at: " + totalPath);
            }
        }
        AssetsHelper.getInstance().loadQueue();

        /*    
        System.out.println("SpriteAmount: " + AssetsHelper.getInstance().getImageTotal());
        System.out.println("SoundAmount: " + AssetsHelper.getInstance().getSoundTotal());
            
        //background.render(cam);
        System.out.println("SpriteAmount: " + assetesHelper.getImageTotal());
        System.out.println("SoundAmount: " + assetesHelper.getSoundTotal());
        System.out.println("MapAmount: " + assetesHelper.getMapTotal());
        System.out.println("AssetServices " + assetServices);
        System.out.println("Gameplugins" + gamePlugins);
        System.out.println("Entity process" + getEntityProcessingServices());
        System.out.println("Post process" + getPostEntityProcessingServices());
         */
        cam.update();
        renderer.setView(cam);
        System.out.println(renderer);
        renderer.render();
        renderer.getBatch();

        for (Entity entity : world.getEntities()) {
            PlayerMovingPart pmp = entity.getPart(PlayerMovingPart.class);
            if (pmp != null) {
                cam.position.set(pmp.getX(entity), pmp.getY(entity), 0);
            }

            SpritePart spritePart = entity.getPart(SpritePart.class);
            if (spritePart != null) {
                String module = spritePart.getModule();
                String imagePath = spritePart.getSpritePath();

                Sprite sprite = AssetsHelper.getInstance().getSprite(module, imagePath);
                //System.out.println(sprite);
                PositionPart positionPart = entity.getPart(PositionPart.class);
                sprite.setRotation(positionPart.getRotation());
                sprite.setPosition(positionPart.getX() - sprite.getWidth() / 2, positionPart.getY() - sprite.getHeight() / 2);

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
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        this.state = State.PAUSE;
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    public void setGameState(State s) {
        this.state = s;
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return lookup.lookupAll(IEntityProcessingService.class);
    }

    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return lookup.lookupAll(IPostEntityProcessingService.class);
    }

    private Collection<? extends IGameStateService> getGameStateServices() {
        return lookup.lookupAll(IGameStateService.class);
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
