package game;

import input.KeyboardListener;
import java.io.IOException;

import settings.Settings;
import ship.CapitalShip;
import actor.Asteroid;

public class Game {
    private static graphics.Renderer renderer;
    private static input.KeyboardListener input;
    private static boolean paused;
    private static Player player;
    private static Map map;

    //for HUD radar testing, will be removed later
    static actor.Asteroid a;

    public static void init(){
        System.out.println(Runtime.getRuntime().availableProcessors() + " available cores detected");
        try {
            Settings.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        map = Map.load("example_1");
        player = new Player();

        renderer = new graphics.Renderer();
        input = new KeyboardListener();
        graphics.Model.loadModels();

        a = new actor.Asteroid();
        a.setPosition(new math.Vector3(-20.0f,0.0f,-30.0f));
        actor.Actor.addActor(a);
        
        CapitalShip capitalShip = new ship.CapitalShip();
       // capitalShip.setSize(5f);
        capitalShip.setSize(new math.Vector3(5,4,8));
        capitalShip.setPosition(new math.Vector3(60f,0.0f,-40.0f));
        //capitalShip.setVelocity(new math.Vector3(-.1f, 0f,-.2f));
        actor.Actor.addActor(capitalShip);
        
        new GameThread().start();
    }
    //for HUD radar testing, will be removed later
    public static Asteroid getAsteroid() {
        return a;
    }

    public static void joinServer(String server) {
        player = new Player();
        map = Map.load("example_1");

        network.ClientServerThread.joinServer(server, player);

        renderer = new graphics.Renderer();
        input = new KeyboardListener();
        graphics.Model.loadModels();

        actor.Asteroid a = new actor.Asteroid();
        a.setPosition(new math.Vector3(0.0f,0.0f,-10.0f));
        actor.Actor.addActor(a);
        
        new GameThread().start();
    }

    public static void start(){
        renderer.start();
    }

    public static KeyboardListener getInputHandler(){
        return input;
    }

    public static Player getPlayer() {
        return player;
    }

    public static boolean isPaused() {
        return paused;
    }

    public static void quitToMenu() {
        // TODO Auto-generated method stub

    }

    public static void togglePause() {
        paused = !paused;
    }

    public static void exit() {
        System.exit(0);
    }

    public static Map getMap() {
        return map;
    }

    public static void setMap(Map m) {
        map = m;
    }

    public static void main (String []args) throws IOException {
        Game.init();
        Game.start();
    }

    public static graphics.Renderer getRenderer() {
        return renderer;
    }
}
