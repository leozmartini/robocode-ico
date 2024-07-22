package robotsICO;

import static robocode.util.Utils.normalRelativeAngleDegrees;

import java.awt.*;
import robocode.*;

public class RobocopMamonas extends AdvancedRobot {

  int count = 0;
  double gunTurnAmt;
  String trackName;

  public void run() {
    setColors(Color.pink, Color.pink, Color.pink);

    trackName = null;
    setAdjustGunForRobotTurn(true);
    setAdjustRadarForGunTurn(true);
    gunTurnAmt = 10;

    while (true) {
      turnRadarRight(360);
    }
  }

  public void onScannedRobot(ScannedRobotEvent e) {
    double radarTurn = getHeading() + e.getBearing() - getRadarHeading();
    setTurnRadarRight(normalRelativeAngleDegrees(radarTurn));

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
      setTurnGunRight(bearingFromGun);
    }

    if (e.getDistance() < 100) {
      setBack(50);
      setTurnRight(90);
      setAhead(150);
    } else {
      setAhead(100);
      setTurnRight(30);
    }

    execute();
  }

  public void onHitByBullet(HitByBulletEvent e) {
    setBack(50);
    setTurnRight(90);
    setAhead(100);
    execute();
  }

  public void onHitWall(HitWallEvent e) {
    back(200);
    turnRight(90);
  }

  public void onWin(WinEvent e) {
    for (int i = 0; i < 50; i++) {
      ahead(20);
      turnRight(30);
      back(20);
      turnLeft(30);
    }
  }
}
