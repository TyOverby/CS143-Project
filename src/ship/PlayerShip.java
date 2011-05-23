package ship;

import graphics.Model.Model_Enum;

public abstract class PlayerShip extends Ship {
    private static final long serialVersionUID = 1L;

    
    protected abstract float getRollRate();
    protected abstract float getPitchRate();
    protected abstract float getYawRate();
    
    protected abstract float getDefaultSpeed();
    protected abstract float getAdditiveSpeed();
    protected abstract float getNegativeSpeed();
    
    protected abstract float getAngularDampening();
    protected abstract float getVelocityDampening();
    protected abstract String getLocalModelName();
    protected Energy energy;
    
    public PlayerShip(Model_Enum model){
        super(model);
        energy = new Energy();
    }
   
    public void forwardThrust() {
        velocity.plusEquals(getDirection().times(getAdditiveSpeed()));
    }
    
    public void reverseThrust() {
        velocity.minusEquals(getDirection().times(getNegativeSpeed()));
    }
    
    public void pitchUp(){
        changePitch(getPitchRate());
    }
    
    public void pitchDown(){
        changePitch(-getPitchRate());
    }
    
    public void yawLeft() {
        changeYaw(getYawRate());
    }
    
    public void yawRight() {
        changeYaw(-getYawRate());
    }
    
    public void rollLeft() {
        changeRoll(getRollRate());
    }
    
    public void rollRight() {
        changeRoll(-getRollRate());
    }
    
    public void nextWeapon() {
        // Get the next weapon in the list
        setWeapon((selectedWeapon + 1) % weapons.size());
    }
    
    public void previousWeapon() {
        setWeapon((selectedWeapon - 1) % weapons.size());
    }

    public void setWeapon(int weaponNumber){
        selectedWeapon = weaponNumber % weapons.size();
        System.out.println("Switching to "+weapons.get(weaponNumber).getWeaponName());
    }

    @Override
    public void update(){
        super.update();
        dampenAngularVelocity(getAngularDampening());
        velocity = velocity.times(getVelocityDampening());
    }
}
