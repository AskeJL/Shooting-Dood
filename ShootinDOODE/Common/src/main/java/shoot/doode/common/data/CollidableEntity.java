package shoot.doode.common.data;

/**
 *
 * @author sande
 */
public class CollidableEntity extends Entity {
    private int boundaryWidth;
    private int boundaryHeight;
    private boolean isStatic = false;
    
    public void setBoundaryWidth(int width) {
        boundaryWidth = width;
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
