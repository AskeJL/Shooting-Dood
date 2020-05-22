/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package player;

import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import shoot.doode.bulletsystem.BulletSpawner;
import shoot.doode.common.data.CollidableEntity;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.GameKeys;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.LifePart;
import shoot.doode.common.data.entityparts.PlayerMovingPart;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.data.entityparts.ShootingPart;
import shoot.doode.common.data.entityparts.SpritePart;
import shoot.doode.common.services.IBulletSpawner;
import shoot.doode.commonweapon.Weapon;
import shoot.doode.playersystem.Player;
import shoot.doode.playersystem.PlayerControlSystem;
import shoot.doode.weapon.WeaponControlSystem;

/**
 *
 * @author emili
 */
public class PlayerShooting {
    @Test
    public void playerShootingTest() throws InterruptedException {
        GameData gameData = mock(GameData.class);
        when(gameData.getDisplayHeight()).thenReturn(400);
        when(gameData.getDisplayWidth()).thenReturn(500);
        
        // The Delta gotten when running the game and printing it.
        float test = (float)0.0167202;
        when(gameData.getDelta()).thenReturn(test);

        GameKeys keys = mock(GameKeys.class);
        when(gameData.getKeys()).thenReturn(keys);

        // Create player entity
        Entity player = createPlayerShip(gameData);
        
        
        World world = new World();
        world.addEntity(player);

        //Check that the world consist of 1 entity, the player entity
        assertEquals(1, world.getEntities().size());
        PlayerControlSystem playerControlSystem = new PlayerControlSystem();
        WeaponControlSystem weaponControlSystem = new WeaponControlSystem();

        weaponControlSystem.process(gameData, world);
        //Check that the world consist of 4 entity, the player entity and the tree weapon types
        assertEquals(4, world.getEntities().size());
        int initialEntities = 4;
        
        ShootingPart shootingPart = player.getPart(ShootingPart.class);
        Weapon weapon = (Weapon) shootingPart.getWeapon();
        weapon.SetCurrentTime(2.0);
        
        // Make sure the arrow keys is consideret pressed
        when(keys.isDown(GameKeys.LEFT) || keys.isDown(GameKeys.RIGHT) || keys.isDown(GameKeys.UP) || keys.isDown(GameKeys.DOWN)).thenReturn(true);

        // Call the players process method to get the player to shoot
        playerControlSystem.process(gameData, world);
        
        // add BulletSpawner since it will be nessecary to add bullets to the world
        BulletSpawner bulletSpawner = new BulletSpawner();
        world.addEntity(bulletSpawner);
        
        //Call the weapons process method to get the weapon to react and shoot bullets
        weaponControlSystem.process(gameData, world);
        
        // Check that there are more entities meaning some bullets have been added
        assertTrue(initialEntities < world.getEntities().size());
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
        playerShip.add(new PlayerMovingPart(maxSpeed));
        playerShip.add(new PositionPart(x, y, radians));
        playerShip.add(new LifePart(1));
        playerShip.add(new SpritePart(module, spritePaths));
        playerShip.add(new ShootingPart(true));
        
        playerShip.setBoundaryWidth(50);
        playerShip.setBoundaryHeight(50);

        return playerShip;
    }
         
    private <E extends Weapon> Weapon createWeapon(Entity player, Class<E> type) {
        PositionPart playerPositionPart = player.getPart(PositionPart.class);
        float x = playerPositionPart.getX() + 12;
        float y = playerPositionPart.getY();
        float radians = playerPositionPart.getRotation();

        Weapon weapon = null;

        try {
            //We know the line means that this is "outdated" but we are not sure what else to do and it works
            weapon = type.newInstance();
        } catch (Exception e) {
            System.out.println(e);
        }
        //it does not need a movepart as the position is just what the enitty that holds it has. Or if it is laying on the ground it should not move.
        weapon.add(new PositionPart(x, y, radians));

        return weapon;
    }
}
