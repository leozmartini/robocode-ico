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

  int count = 0;
  double gunTurnAmt;
  String trackName;

  public void run() {
    setColors(Color.white, Color.white, Color.blue);

    trackName = null;
    setAdjustGunForRobotTurn(true);
    gunTurnAmt = 10;

    while (true) {
      turnGunRight(gunTurnAmt);
      count++;
      if (count > 2) {
        gunTurnAmt = -10;
      }
      if (count > 5) {
        gunTurnAmt = 10;
      }
      if (count > 11) {
        trackName = null;
      }
    }
  }

  public void onScannedRobot(ScannedRobotEvent e) {
    setAhead(100);
    setTurnRight(30);
    execute();
    double absoluteBearing = getHeading() + e.getBearing();
    double bearingFromGun = normalRelativeAngleDegrees(
      absoluteBearing - getGunHeading()
    );

    if (Math.abs(bearingFromGun) <= 10) {
      fire(2);

      if (getGunHeat() == 0) {
        fire(Math.min(3 - Math.abs(bearingFromGun), getEnergy() - .1));
      }
    } else {
      turnGunRight(bearingFromGun);
    }

    if (bearingFromGun == 0) {
      scan();
    }
  }

  public void onHitByBullet(HitByBulletEvent e) {
    setBack(50);
    setAhead(100);
    setTurnRight(30);
    execute();
  }

  public void onHitWall(HitWallEvent e) {
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
