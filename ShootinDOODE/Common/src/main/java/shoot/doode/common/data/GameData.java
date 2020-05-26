package shoot.doode.common.data;

public class GameData {

    private float delta;
    private int displayWidth;
    private int displayHeight;
    private final GameKeys keys = new GameKeys();
    private State state;
    private float score;
    private int wave;

    public void setScore(float score)
    {
        this.score = score;
    }
    
    public void setWave(int wave)
    {
        this.wave = wave;
    }
    
    public float getScore()
    {
        return score;
    }
    
    public int getWave()
    {
        return wave;
    }
    
    public State getState() {
        return state;
    }
    
    public void setState(State state)
    {
        this.state = state;
    }

    public GameKeys getKeys() {
        return keys;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    public float getDelta() {
        return delta;
    }

    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

}
