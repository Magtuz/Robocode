package voidious.predator;

import robocode.*;
import robocode.util.Utils;
import java.awt.geom.*;
import java.util.LinkedList;
import java.awt.Color;
import robocode.Rules;


public class Predator extends AdvancedRobot {
    private static double _surfStats[][][] = new double[4][5][47];
    public static Point2D.Double _myLocation;
    public static Point2D.Double _enemyLocation;
    public static LinkedList _enemyWaves;
    private static double _oppEnergy;

    private static Wave _surfWave;
    private static Wave _nextSurfWave;
    private static double _lastDistance;
    
    private static double _goAngle;
    private static int _ramCounter;
    
    private static java.awt.geom.Rectangle2D.Double _fieldRect
        = new java.awt.geom.Rectangle2D.Double(18, 18, 764, 564);
    static final double A_LITTLE_LESS_THAN_HALF_PI = 1.25;
    static final double WALL_STICK = 140;

    static final int GF_ZERO = 23;
    static final int GF_ONE = 46;
    private static double _enemyAbsoluteBearing;
    private static int _lastGunOrientation;
    

    static double lastVChangeTime;
    static int enemyVelocity;
    static double[][][][][][] _gunStats = new double[6][4][4][2][3][GF_ONE+1];
    static final double LOG_BASE_E_TO_2_CONVERSION_CONSTANT = 1.4427;

    public void run() {
        setColors(Color.black, Color.black, Color.white);

        _enemyWaves = new LinkedList();
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);

        do {
            turnRadarRightRadians(1);
        } while (true);

    }

    public void onScannedRobot(ScannedRobotEvent e) {
        Wave w;
        int direction;

        double bulletPower;
        if ((bulletPower = _oppEnergy - e.getEnergy()) <= 3
            && bulletPower > 0) {
        	(w = _nextSurfWave).bulletSpeed = Rules.getBulletSpeed(bulletPower);
            addCustomEvent(w);
            _enemyWaves.addLast(w);
        }
        (_nextSurfWave = w = new Wave()).directAngle = _lastAbsBearingRadians;
        w.waveGuessFactors = _surfStats[(int)(Math.min((_lastDistance+50)/200, 3))][(int)((Math.abs(_lastLatVel)+1)/2)];
        w.orientation = direction = sign(_lastLatVel);
        double enemyAbsoluteBearing;
        w.sourceLocation = _enemyLocation = 
        	project((_myLocation = new Point2D.Double(getX(), getY())),
        			_enemyAbsoluteBearing = enemyAbsoluteBearing = getHeadingRadians() + e.getBearingRadians(), _lastDistance = e.getDistance());
    
        try {
            _goAngle = (_surfWave = (Wave)(_enemyWaves.getFirst()))
            	.absoluteBearing(_myLocation) +
            	A_LITTLE_LESS_THAN_HALF_PI * 
                	(direction = (sign(checkDanger(-1) - checkDanger(1))));
        } catch (Exception ex) { }

       
    public void onHitByBullet(HitByBulletEvent e) {
        Wave surfWave = Predator._surfWave;
        _oppEnergy += e.getBullet().getPower() * 3;
    	if (surfWave.distanceToPoint(_myLocation) - surfWave.distance < 100) {
            logHit(surfWave, _myLocation, 0.7);
        }
    }

    }
}
