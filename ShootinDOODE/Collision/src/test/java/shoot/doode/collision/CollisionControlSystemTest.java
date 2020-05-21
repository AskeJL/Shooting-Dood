/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.collision;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import shoot.doode.common.data.CollidableEntity;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.LifePart;
import shoot.doode.common.data.entityparts.PositionPart;

/**
 *
 * @author tobia
 */
public class CollisionControlSystemTest {

    GameData gameData;
    World world;
    CollidableEntity e1;
    CollidableEntity e2;

    public CollisionControlSystemTest() {
    }

    @org.junit.jupiter.api.BeforeAll
    public static void setUpClass() throws Exception {

    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
        gameData = null;
        world = new World();
        
        e1 = new CollidableEntity();
        e1.setBoundaryHeight(10);
        e1.setBoundaryWidth(10);
        e1.setToughness(10);
        e1.add(new LifePart(1));
        e1.add(new PositionPart(100,100,0));
        
        e2 = new CollidableEntity();
        e2.setBoundaryHeight(10);
        e2.setBoundaryWidth(10);
        e2.setToughness(9);
        e2.add(new LifePart(1));
        e2.add(new PositionPart(100,100,0));
        
        world.addEntity(e1);
        world.addEntity(e2);
        
        
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {       
    }

    /**
     * Test of process method, of class CollisionControlSystem.
     */
    @org.junit.jupiter.api.Test
    public void testProcess1() {
        System.out.println("Collision Should remove");
        
        CollisionControlSystem instance = new CollisionControlSystem();
        System.out.println(e1);
        int expected = 2;
        assertEquals(expected, world.getEntities().size());
        instance.process(gameData, world);
        expected = 1;
        assertEquals(expected, world.getEntities().size());
    }
    
    /**
     * Test of process method, of class CollisionControlSystem.
     */
    @org.junit.jupiter.api.Test
    public void testProcess2() {
        System.out.println("No Collision Should not remove");
        System.out.println(e1);
        CollisionControlSystem instance = new CollisionControlSystem();
        PositionPart p1 = e1.getPart(PositionPart.class);
        PositionPart p2 = e2.getPart(PositionPart.class);
        
        p1.setPosition(0, 0);
        p2.setPosition(200, 200);
        int expected = 2;
        assertEquals(expected, world.getEntities().size());
        instance.process(gameData, world);
        expected = 2;
        assertEquals(expected, world.getEntities().size());
    }
    
    /**
     * Test of process method, of class CollisionControlSystem.
     */
    @org.junit.jupiter.api.Test
    public void testProcess3() {
        System.out.println("Collision but with a static objekt, should not remove");
        System.out.println(e1);
        CollisionControlSystem instance = new CollisionControlSystem();
        e1.setIsStatic(true);
           
        int expected = 2;
        assertEquals(expected, world.getEntities().size());
        instance.process(gameData, world);
        expected = 2;
        assertEquals(expected, world.getEntities().size());
    }
    
    
}
