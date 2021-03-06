package actor.ship.shield;

import java.io.Serializable;

public abstract class Shield implements Serializable{
    private static final long serialVersionUID = -464289746987986899L;
    int strength, rechargeRate, maxStrength, age, lastHit;


    public Shield(){
        strength = 500;
        rechargeRate = 1;
        maxStrength = 500;
        age = 0;
        lastHit = 0;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }


    public void update(){
        age++;
        if(age - lastHit > 600){
            if(strength<maxStrength){
                strength += rechargeRate;
            }
        }

    }
    /**
     * 
     * @param damage
     * @return The remainder of damage to take
     */
    public int takeDamage(int damage) {
        lastHit = age;
        if(damage < strength){
            strength -= damage;
            return 0;
        } else {
            int damageLeft = damage - strength;
            strength = 0;
            return damageLeft;
        }
    }

    public boolean getStatus() {
        return strength > 0;
    }

    public long getRechargeRate() {
        return rechargeRate;
    }

    public void setRechargeRate(int rechargeRate) {
        this.rechargeRate = rechargeRate;
    }

    public int getMaxStrength() {
        return maxStrength;
    }

    public void setMaxStrength(int maxStrength) {
        this.maxStrength = maxStrength;
    }
}
