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
        for (Entity entity : world.getEntities()) {
            if (entity.getPart(ShootingPart.class) != null) {
                ShootingPart shootingPart = entity.getPart(ShootingPart.class);

                //If the entity has a shooting part and should have weapons but don't, give them weapons
                if (shootingPart.getWeaponAmount() == 0 && shootingPart.isUsesWeapons()) {
                    Weapon gun = createWeapon(entity, Gun.class);
                    world.addEntity(gun);
                    shootingPart.addWeapon(gun);

                    Weapon shotgun = createWeapon(entity, Shotgun.class);
                    world.addEntity(shotgun);
                    shootingPart.addWeapon(shotgun);

                    Weapon mingun = createWeapon(entity, MiniGun.class);
                    world.addEntity(mingun);
                    shootingPart.addWeapon(mingun);
                }

                PositionPart playerPosition = entity.getPart(PositionPart.class);
                Weapon weapon = (Weapon) shootingPart.getWeapon();
                PositionPart weaponPosition = weapon.getPart(PositionPart.class);
                //Set the rotation and position of the weapon = to the player
               weaponPosition.setPosition(playerPosition.getX() + 12, playerPosition.getY());
                weaponPosition.setRotation(playerPosition.getRotation());

                
                weaponPosition.process(gameData, weapon);
                //If the shooting part is shooting and the enityt uses weapons than use the shoot method form the weapon
                if (shootingPart.isShooting()&& shootingPart.isUsesWeapons()) {
                    if (weapon.getCurrentTime() >= weapon.getRealoadTime()) {
                        weapon.shoot(gameData, world, entity);
                        weapon.SetCurrentTime(0);
                    }
                }
                //process the shooting part (which changes hwat weapon you have equiped if the boolean is true)
                shootingPart.process(gameData, entity);
                weapon.SetCurrentTime(weapon.getCurrentTime() + gameData.getDelta() * shootingPart.getReloadModifier());


            }
        }
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

        weapon.setRadius(5);

        return weapon;
    }
}
