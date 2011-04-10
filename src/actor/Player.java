package actor;

import graphics.Camera;

public class Player extends Actor {
    private static final String DEFAULT_NAME = new String("Pilot");
    private static final float TURN_SPEED = 1.0f;
    private static final long serialVersionUID = 260627862699350716L;
    protected boolean alive;
    protected String name;


    public Player(){
        super();
        alive = true;
        name = DEFAULT_NAME;
    }
    @Override
    public void handleCollision(Actor other) {
        // TODO Auto-generated method stub

    }

    public boolean isAlive() {
        return alive;
    }

    public void respawn() {
        // Don't respawn the player if they are alive
        if(alive == true)
            return;

        // TODO Auto-generated method stub

    }

    public void shoot() {
        // TODO Auto-generated method stub

    }

    public void forwardThrust() {
        position.plusEquals(getDirection().times(0.1f));
    }

    public void reverseThrust() {
        position.minusEquals(getDirection().times(0.1f));
    }

    public void turnUp(){
        changePitch(TURN_SPEED);

    }

    public void turnDown(){
        changePitch(-TURN_SPEED);
    }


    public void turnLeft() {
        changeHeading(TURN_SPEED);
    }

    public void turnRight() {
        changeHeading(-TURN_SPEED);
    }

}
