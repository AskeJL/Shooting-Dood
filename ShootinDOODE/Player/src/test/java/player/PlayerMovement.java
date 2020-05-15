package player;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.GameKeys;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.playersystem.PlayerControlSystem;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import shoot.doode.common.data.CollidableEntity;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.LifePart;
import shoot.doode.common.data.entityparts.PlayerMovingPart;
import shoot.doode.common.data.entityparts.ShootingPart;
import shoot.doode.common.data.entityparts.SpritePart;
import shoot.doode.playersystem.Player;


public class PlayerMovement {

    @Test
    public void playerMovementTest() throws InterruptedException {
        GameData gameData = mock(GameData.class);
        when(gameData.getDisplayHeight()).thenReturn(400);
        when(gameData.getDisplayWidth()).thenReturn(500);
        
        // The Delta gotten when running the game and printing it.
        float test = (float)0.0167202;
        when(gameData.getDelta()).thenReturn(test);

        GameKeys keys = mock(GameKeys.class);
        when(gameData.getKeys()).thenReturn(keys);

        Entity player = createPlayerShip(gameData);
        
        // Save the initial height of players location
        PositionPart positionPart = player.getPart(PositionPart.class);
        
        float initialY = positionPart.getY();
        World world = new World();
        world.addEntity(player);

        //Check that the world consist of 1 entity, the player entity
        System.out.println("Testing that a player entity has been added to world");
        assertEquals(1, world.getEntities().size());
        System.out.println("Test complete\n");
        

        //Check the initial y location of the player Entity. 1e-8 is the difference alloud and is used because it is a small number
        System.out.println("Testing that inital location of player entity id 200.0");
        assertEquals(200.0, initialY, 1e-8);
        System.out.println("Test complete\n");
        
        PlayerControlSystem playerControlSystem = new PlayerControlSystem();

        // Make sure the up key is considered pressed
        when(keys.isDown(GameKeys.W)).thenReturn(Boolean.TRUE);

        // Call the process method to move the player and check the players location is higher than the initial
        playerControlSystem.process(gameData, world); 
        System.out.println("Testing player entities y location is higher and has therefore moved up");
        assertTrue(positionPart.getY() > initialY);
        System.out.println("Test complete\n");
        
        System.out.println("All tests complete. Player movement functions correct");
    }
    
     private Entity createPlayerShip(GameData gameData) {

        float maxSpeed = 3;
        float x = gameData.getDisplayWidth() / 2;
        float y = gameData.getDisplayHeight() / 2;
        float radians = 3.1415f / 2;

        float[] colour = new float[4];
        colour[0] = 1.0f;
        colour[1] = 1.0f;
        colour[2] = 1.0f;
        colour[3] = 1.0f;

        String module = "Player";
        String[] spritePaths = new String[4];
        spritePaths[0] = "Doode-still.png";
        spritePaths[1] = "Doode-left.png";
        spritePaths[2] = "Doode-right.png";
        spritePaths[3] = "Doode-back.png";
       
        CollidableEntity playerShip = new Player();
        playerShip.setRadius(8);
        playerShip.setColour(colour);
        playerShip.add(new PlayerMovingPart(maxSpeed));
        playerShip.add(new PositionPart(x, y, radians));
        playerShip.add(new LifePart(1));
        playerShip.add(new SpritePart(module, spritePaths));
        playerShip.add(new ShootingPart(playerShip.getID()));
        
        playerShip.setBoundaryWidth(50);
        playerShip.setBoundaryHeight(50);

        return playerShip;
    }
}