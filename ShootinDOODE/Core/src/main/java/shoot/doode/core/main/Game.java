package shoot.doode.core.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
import shoot.doode.common.data.entityparts.SoundPart;
import shoot.doode.common.services.IAssetService;
import shoot.doode.core.managers.AssetsHelper;

public class Game implements ApplicationListener {

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
    private AssetsHelper assetesHelper = new AssetsHelper();

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
            assetesHelper.queueImages(assetService.loadImages());
            assetesHelper.queueSounds(assetService.loadSounds());
            assetServices.add(assetService);
        }

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

    private void draw() {
        for(String totalPath : assetesHelper.getSpriteMapKeys())
        {
            if(assetesHelper.getSprite(totalPath) == null)
            {
                assetesHelper.loadImages(totalPath);
                // System.out.println("Loaded Image at: " + totalPath);
            }
            /*else

            {
                System.out.println("Image was already loaded at: " + totalPath);
            }*/
        }
        
        for(String totalPath : assetesHelper.getSoundMapKeys())
        {
            if(assetesHelper.getSound(totalPath) == null)
            {
                assetesHelper.loadSounds(totalPath);
                //System.out.println("Loaded sound at: " + totalPath);
            }
            /*else
            {
                System.out.println("Sound was already loaded at: " + totalPath);
            }*/
        }
            
            
            
            
//        System.out.println("SpriteAmount: " + assetesHelper.getImageTotal());
//        System.out.println("SoundAmount: " + assetesHelper.getSoundTotal());
//        System.out.println("AssetServices " + assetServices);
//        System.out.println("Gameplugins" + gamePlugins);
//        System.out.println("Entity process" + getEntityProcessingServices());
//        System.out.println("Post process" + getPostEntityProcessingServices());
        
        for (Entity entity : world.getEntities()) {
            SpritePart spritePart = entity.getPart(SpritePart.class);
            if (spritePart != null) {
                String module = spritePart.getModule();
                String imagePath = spritePart.getSpritePath();

                Sprite sprite = assetesHelper.getSprite(module,imagePath);
                PositionPart positionPart = entity.getPart(PositionPart.class);
                sprite.setRotation(positionPart.getRotation());
                sprite.setPosition(positionPart.getX() - sprite.getWidth()/2, positionPart.getY() - sprite.getHeight()/2); 
                
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
                
                for(String soundPath : soundPart.getSoundPaths())
                {
                    Sound sound = assetesHelper.getSound(module, soundPath);
                    sound.play();
                    soundPart.setPlay(soundPath,false);
                }
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
       /*
            for(int i = 0; i < 10;i++)
            {
                System.out.println("|");
                if(i == 5)
                {
                    System.out.println("Running resultChanged");
                }        
                System.out.println("|");
            }*/
            Collection<? extends IGamePluginService> iGameUpdated = iGameResult.allInstances();
            Collection<? extends IAssetService> iAssetUpdated = iAssetResult.allInstances();

            
            for (IAssetService us : iAssetUpdated) {
                // Newly installed modules
                if (!assetServices.contains(us)) {
                    //System.out.println("New AssetLoader: " + us);
                    assetesHelper.queueImages(us.loadImages());
                    assetesHelper.queueSounds(us.loadSounds());
                    //loadImages(us.queueImages());               
                    assetServices.add(us);
                }
            }

            // Stop and remove module
            for (IAssetService gs : assetServices) {
                if (!iAssetUpdated.contains(gs)) {
                    //unLoadImages(gs.unLoadImages());
                    //System.out.println("Remove AssetLoader: " + gs);
                    assetesHelper.unLoadImages(gs.unLoadImages());
                    assetesHelper.unLoadSounds(gs.unLoadSounds());
                    assetServices.remove(gs);
                }
            }
            
            for (IGamePluginService us : iGameUpdated) {
                // Newly installed modules
                if (!gamePlugins.contains(us)) {
                    //System.out.println("New GamePlugin: " + us);
                    us.start(gameData, world);
                    gamePlugins.add(us);
                }
            }

            // Stop and remove module
            for (IGamePluginService gs : gamePlugins) {
                if (!iGameUpdated.contains(gs)) {
                    //System.out.println("Remove GamePlugin: " + gs);
                    gs.stop(gameData, world);
                    gamePlugins.remove(gs);
                }
            }
        }
    };
}
