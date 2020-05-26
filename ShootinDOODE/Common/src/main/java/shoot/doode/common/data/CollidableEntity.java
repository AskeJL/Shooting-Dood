package shoot.doode.common.data;

public class CollidableEntity extends Entity {
    private int boundaryWidth;
    private int boundaryHeight;
    private int toughness;
    private boolean isStatic = false;
    
    public void setBoundaryWidth(int width) {
        boundaryWidth = width;
    }
    
    public void setToughness(int toughness)
    {
        this.toughness = toughness;
    }
    
    public int getToughness()
    {
        return this.toughness;
    }
    
    public int getBoundaryWidth() {
        return boundaryWidth;
    }
    
    public void setBoundaryHeight(int height) {
        boundaryHeight = height;
    }
    
    public int getBoundaryHeight() {
        return boundaryHeight;
    }
    
    public void setIsStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }
    
    public boolean getIsStatic() { 
        return isStatic;
    }
}
