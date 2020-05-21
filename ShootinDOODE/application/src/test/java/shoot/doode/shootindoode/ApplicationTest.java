package shoot.doode.shootindoode;

import java.io.IOException;
import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import junit.framework.Test;
import static junit.framework.TestCase.assertEquals;
import org.netbeans.junit.NbModuleSuite;
import org.netbeans.junit.NbTestCase;
import org.openide.util.Lookup;
import shoot.doode.common.services.IEntityProcessingService;
import shoot.doode.common.services.IGamePluginService;

public class ApplicationTest extends NbTestCase {
    
    private static final String currentPath = System.getProperty("user.dir");
    private static final String subPath = currentPath.substring(0, currentPath.lastIndexOf("ShootinDOODE"));
        
    private static final String ADD_PLAYER_UPDATES_FILE = subPath + "ShootinDOODE\\addPlayerUpdate.txt";
    private static final String REMOVE_PLAYER_UPDATES_FILE = subPath + "ShootinDOODE\\removePlaterUpdate.txt";  
    private static final String UPDATES_FILE = subPath + "ShootinDOODE\\netbeans_site\\update.txt";
    
    public static Test suite() {
        return NbModuleSuite.createConfiguration(ApplicationTest.class).
                gui(false).
                failOnMessage(Level.WARNING).
                failOnException(Level.INFO).
                enableClasspathModules(false).
                clusters(".*").
                suite();
                }
    
    public ApplicationTest(String name) {
        super(name);
    }
    
    public void testLoadUnload() throws InterruptedException, IOException {
        List<IEntityProcessingService> processors = new CopyOnWriteArrayList<>();
        List<IGamePluginService> plugins = new CopyOnWriteArrayList<>();
        waitForUpdate(processors, plugins);

        assertEquals("No processors", 0, processors.size());
        assertEquals("No plugins", 0, plugins.size());
        
        copy(get(ADD_PLAYER_UPDATES_FILE), get(UPDATES_FILE), REPLACE_EXISTING);
        waitForUpdate(processors, plugins);
        
        assertEquals("One plugins", 1, plugins.size());
        assertEquals("One prociessors", 1, processors.size());
        
        copy(get(REMOVE_PLAYER_UPDATES_FILE), get(UPDATES_FILE), REPLACE_EXISTING);
        waitForUpdate(processors, plugins);
        
        assertEquals("No processors", 0, processors.size());
        assertEquals("No plugins", 0, plugins.size());
    }
    
    private void waitForUpdate(List<IEntityProcessingService> processors, List<IGamePluginService> plugins) throws InterruptedException {
        Thread.sleep(10000);
        processors.clear();
        processors.addAll(Lookup.getDefault().lookupAll(IEntityProcessingService.class));
        
        plugins.clear();
        plugins.addAll(Lookup.getDefault().lookupAll(IGamePluginService.class));
    }
    
    
}
