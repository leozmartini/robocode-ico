package robotsICO;

import static robocode.util.Utils.normalRelativeAngleDegrees;

import java.awt.*;
import java.awt.Color;
import robocode.*;
import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

public class RobocopMamonas extends AdvancedRobot {

  int count = 0; // Keeps track of how long we've
  // been searching for our target
  double gunTurnAmt; // How much to turn our gun when searching
  String trackName; // Name of the robot we're currently tracking

  public void run() {
    // Initialization of the robot should be put here

    // After trying out your robot, try uncommenting the import at the top,
    // and the next line:

    // setColors(Color.red,Color.blue,Color.green); // body,gun,radar
    setColors(Color.white, Color.white, Color.blue);

    // Prepare gun
    trackName = null; // Initialize to not tracking anyone
    setAdjustGunForRobotTurn(true); // Keep the gun still when we turn
    gunTurnAmt = 10; // Initialize gunTurn to 10

    // Robot main loop
    while (true) {
      // Replace the next 4 lines with any behavior you would like
      // turn the Gun (looks for enemy)
      turnGunRight(gunTurnAmt);
      // Keep track of how long we've been looking
      count++;
      // If we've haven't seen our target for 2 turns, look left
      if (count > 2) {
        gunTurnAmt = -10;
      }
      // If we still haven't seen our target for 5 turns, look right
      if (count > 5) {
        gunTurnAmt = 10;
      }
      // If we *still* haven't seen our target after 10 turns, find another target
      if (count > 11) {
        trackName = null;
      }
    }
  }

  /**
   * onScannedRobot: What to do when you see another robot
   */
  public void onScannedRobot(ScannedRobotEvent e) {
    setAhead(100);
    setTurnRight(30);
    execute();
    // Replace the next line with any behavior you would like
    double absoluteBearing = getHeading() + e.getBearing();
    double bearingFromGun = normalRelativeAngleDegrees(
      absoluteBearing - getGunHeading()
    );

    // If it's close enough, fire!
    if (Math.abs(bearingFromGun) <= 10) {
      fire(2);
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

  public void onWin(WinEvent e) {
    for (int i = 0; i < 50; i++) {
      turnRight(30);
      turnLeft(30);
    }
  }
}
