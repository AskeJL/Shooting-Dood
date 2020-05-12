package shoot.doode.menu;

import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.Menu;
import shoot.doode.common.data.World;
import shoot.doode.common.services.IGamePluginService;

@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class MenuPlugin implements IGamePluginService {
    
    private Menu menu;

    @Override
    public void start(GameData gameData, World world) {
        menu = new PauseMenu();
        world.addMenu(menu);
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Menu pauseMenu : world.getMenus(PauseMenu.class)) {
            world.removeMenu(pauseMenu);
        }
        
        for (Menu startMenu : world.getMenus(StartMenu.class)) {
            world.removeMenu(startMenu);
        }
    }
    
    private <M extends Menu> Menu createMenu(Class<M> type) {
        Menu menu = null;
        
        try
        {
            menu = type.newInstance();
        } catch(Exception e)
        {
            System.out.println(e);
        }

        return (Menu) menu;
    }
}
