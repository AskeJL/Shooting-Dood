package shoot.doode.common.data;

import java.io.Serializable;
import java.util.UUID;

public class Menu implements Serializable {
    
    private String ID = UUID.randomUUID().toString();
    private float height = 200;
    private float width = 200;

    public String getID() {
        return ID;
    }
      
    public void setID(String id) {
        this.ID = id;
    }
    
    public void setSize(float h, float w) {
        height = h;
        width = w;
    }

    public float getHeight() {
        return height;
    }
    
    public float getWidth() {
        return width;
    }
}


