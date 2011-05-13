package graphics;

import javax.media.opengl.GL2;

import actor.Actor;
import math.Quaternion;
import math.Vector3;

/*
 * A camera is kind of like an actor in that it has a position and rotation
 * but it's position and rotation are opposite of the actors.
 */
public class Camera implements actor.Movable{
    Vector3 position, velocity;
    Quaternion rotation;
    
    public Camera() {
        position = new Vector3();
        velocity = new Vector3();
        rotation = new Quaternion();
    }

    public void updateFromActor(Actor actor) {
        velocity = actor.getVelocity();
        position = actor.getPosition().times(-1.0f);
        rotation = actor.getRotation().inverse();
    }
    
    public void setPerspective(GL2 gl) {
        float[] matrix;       
        gl.glLoadIdentity();
        // Combine the pitch and heading rotations and store the results in q
        matrix = rotation.toGlMatrix();
        // Let OpenGL set our new perspective on the world!
        gl.glMultMatrixf(matrix,0);
        // Translate to our new position.
        gl.glTranslatef(position.x, position.y, position.z);
    }

    @Override
    public Vector3 getPosition() {
        return position;
    }

    @Override
    public Camera setPosition(Vector3 newPosition) {
        position = newPosition;
        return this;
    }

    @Override
    public Vector3 getVelocity() {
        return velocity;
    }

    @Override
    public Camera setVelocity(Vector3 vel) {
        velocity = vel;
        return this;
    }

    @Override
    public Quaternion getRotation() {
        return rotation;
    }

    @Override
    public Camera setRotation(Quaternion rot) {
        rotation = rot;
        return this;
    }
}