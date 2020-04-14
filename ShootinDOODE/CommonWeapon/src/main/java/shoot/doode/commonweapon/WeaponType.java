package shoot.doode.commonweapon;


public enum WeaponType {
    
    GUN("GUN"),
    RIFLE("RIFLE"),
    KNIFE("KNIFE");

    private String type;

    private WeaponType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
