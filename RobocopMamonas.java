package robotsICO;
import robocode.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;
public class RobocopMamonas extends AdvancedRobot
{
public void run() {
// Initialization of the robot should be put here

// After trying out your robot, try uncommenting the import at the top,
// and the next line:

// setColors(Color.red,Color.blue,Color.green); // body,gun,radar

// Robot main loop
while(true) {
// Replace the next 4 lines with any behavior you would like
setAhead(100);
setTurnRight(30);
execute();
turnRadarRight(360);
}
}

/**
* onScannedRobot: What to do when you see another robot
*/
public void onScannedRobot(ScannedRobotEvent e) {
// Replace the next line with any behavior you would like
double absoluteBearing = getHeading() + e.getBearing();
double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());

// If it's close enough, fire!
if (Math.abs(bearingFromGun) <= 3) {
setAhead(100);
setTurnRight(30);
execute();
turnGunRight(bearingFromGun);
// We check gun heat here, because calling fire()
// uses a turn, which could cause us to lose track
// of the other robot.
if (getGunHeat() == 0) {
fire(Math.min(3 - Math.abs(bearingFromGun), getEnergy() - .1));
}
} // otherwise just set the gun to turn.
// Note:  This will have no effect until we call scan()
else {
turnGunRight(bearingFromGun);
}
// Generates another scan event if we see a robot.
// We only need to call this if the gun (and therefore radar)
// are not turning.  Otherwise, scan is called automatically.
if (bearingFromGun == 0) {
scan();
}
}

/**
* onHitByBullet: What to do when you're hit by a bullet
*/
public void onHitByBullet(HitByBulletEvent e) {
// Replace the next line with any behavior you would like
setBack(50);
setAhead(100);
setTurnRight(30);
execute();
}

/**
* onHitWall: What to do when you hit a wall
*/
public void onHitWall(HitWallEvent e) {
// Replace the next line with any behavior you would like
back(200);
turnRight(90);
}
}
