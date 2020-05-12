package shoot.doode.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import shoot.doode.common.data.Menu;
import shoot.doode.common.services.IGameStateService;

@ServiceProviders(value = {
    @ServiceProvider(service = IGameStateService.class),})
public class PauseMenu extends Menu implements IGameStateService{

    private Texture background = new Texture("Background.png");
    private Texture continueBtn = new Texture("Continue.png");
    
    public PauseMenu() {
    }
    
    @Override
    public void render(SpriteBatch sb) {
        System.out.println("In here?");
        
        sb.begin();
        sb.draw(background, 0, 0, getWidth(), getHeight());
        sb.draw(continueBtn, getWidth() - (continueBtn.getWidth()/2), getHeight());
        sb.end();
    }

    @Override
    public float getHeight() {
        return super.getHeight();
    }
    
    @Override
    public float getWidth() {
        return super.getWidth();
    }

    @Override
    public void setSize(float h, float w) {
        super.setSize(h, w);
    }
}

