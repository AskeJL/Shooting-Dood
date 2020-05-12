package shoot.doode.weapon;

import java.util.Random;
import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.data.entityparts.ShootingPart;
import shoot.doode.common.data.entityparts.SoundPart;
import shoot.doode.common.data.entityparts.SpritePart;
import shoot.doode.common.services.IEntityProcessingService;
import shoot.doode.commonweapon.Weapon;

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class WeaponControlSystem implements IEntityProcessingService {

    int numPoints = 4;
    Random rnd = new Random(10);
    float angle = 90;

    @Override
    public void process(GameData gameData, World world) {
        Entity player;
        Weapon weapon;
        
        for (Entity entity : world.getEntities()) {
            if (entity.getPart(ShootingPart.class) != null) {
                player = entity;
                ShootingPart playerShootingPart = player.getPart(ShootingPart.class);

                if (playerShootingPart.getWeapon() != null) {
                    PositionPart playerPosition = player.getPart(PositionPart.class);
                    weapon = (Weapon) playerShootingPart.getWeapon();
                    PositionPart weaponPosition = weapon.getPart(PositionPart.class);
                    weaponPosition.setPosition(playerPosition.getX()+12, playerPosition.getY());
                    weaponPosition.setRotation(playerPosition.getRotation());
                    
                    weaponPosition.process(gameData, weapon);
                                    
                    if (playerShootingPart.isShooting()) {
                        if(weapon.getCurrentTime() >= weapon.getRealoadTime())
                        {
                            weapon.shoot(gameData, world,playerShootingPart.getDamageModifier());
                            weapon.SetCurrentTime(0);
                        }
                    }
                    weapon.SetCurrentTime(weapon.getCurrentTime()+gameData.getDelta()*playerShootingPart.getReloadModifier());
                    if(playerShootingPart.isSwitchWeapon())
                    {
                        if(playerShootingPart.getWeapon() instanceof Gun)
                        {
                            world.removeEntity(playerShootingPart.getWeapon());
                            Weapon newWeapon = createWeapon(player,Shotgun.class);
                            world.addEntity(newWeapon);
                            playerShootingPart.setWeapon(newWeapon);
                        }
                        else
                        {
                            world.removeEntity(playerShootingPart.getWeapon());
                            Weapon newWeapon = createWeapon(player,Gun.class);
                            world.addEntity(newWeapon);
                            playerShootingPart.setWeapon(newWeapon);
                        }
                    }
                    
                    
                } else {
                    weapon = createWeapon(player,Gun.class);
                    world.addEntity(weapon);
                    playerShootingPart.setWeapon(weapon);
                }
                
                updateShape(weapon);

            }
        }
    }

    private void updateShape(Weapon weapon) {
        PositionPart weaponPositionPart = weapon.getPart(PositionPart.class);

        float[] shapex = new float[4];
        float[] shapey = new float[4];
        float x = weaponPositionPart.getX();
        float y = weaponPositionPart.getY();
        float rotation = weaponPositionPart.getRotation();

        shapex[0] = (float) (x + Math.cos(rotation) * 5);
        shapey[0] = (float) (y + Math.sin(rotation) * 15);

        shapex[1] = (float) (x + Math.cos(rotation - 4 * 3.1415f / 5) * weapon.getRadius());
        shapey[1] = (float) (y + Math.sin(rotation - 4 * 3.1145f / 5) * weapon.getRadius());

        shapex[2] = (float) (x + Math.cos(rotation + 3.1415f) * weapon.getRadius());
        shapey[2] = (float) (y + Math.sin(rotation + 3.1415f) * weapon.getRadius());

        shapex[3] = (float) (x + Math.cos(rotation + 4 * 3.1415f / 5) * weapon.getRadius());
        shapey[3] = (float) (y + Math.sin(rotation + 4 * 3.1415f / 5) * weapon.getRadius());

        weapon.setShapeX(shapex);
        weapon.setShapeY(shapey);
    }
    //public <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes) {
    private <E extends Weapon> Weapon createWeapon(Entity player, Class<E> type) {
        PositionPart playerPositionPart = player.getPart(PositionPart.class);
        float x = playerPositionPart.getX() + 12;
        float y = playerPositionPart.getY();
        float radians = playerPositionPart.getRotation();
        UUID id = UUID.randomUUID();

        float[] colour = new float[4];
        colour[0] = 1.0f;
        colour[1] = 1.0f;
        colour[2] = 1.0f;
        colour[3] = 1.0f;
        Entity weapon = null;
        
        try
        {
            weapon = type.newInstance();
        } catch(Exception e)
        {
            System.out.println(e);
        }
            
        weapon.setID(id.toString());
        weapon.add(new PositionPart(x, y, radians));

        weapon.setColour(colour);
        weapon.setRadius(5);

        return (Weapon) weapon;
    }
}
