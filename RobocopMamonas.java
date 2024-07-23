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

    setAdjustGunForRobotTurn(true); // O canhão não gira com o robô
    setAdjustRadarForGunTurn(true); // O radar não gira com o canhão
    gunTurnAmt = 10; // Quantidade inicial de rotação do canhão

    while (true) {
      turnRadarRight(360); // Busca inimigos girando o radar
    }
  }

  public void onScannedRobot(ScannedRobotEvent e) {
    // Ajusta o radar para focar no inimigo
    double radarTurn = getHeading() + e.getBearing() - getRadarHeading();
    setTurnRadarRight(normalRelativeAngleDegrees(radarTurn));

    // Calcula o ângulo absoluto para o inimigo
    double absoluteBearing = getHeading() + e.getBearing();
    double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());

    // Se o inimigo estiver a menos de 10 graus da linha de fogo, atira
    if (Math.abs(bearingFromGun) <= 10) {
      fire(2);

      // Atira novamente se o canhão estiver frio
      if (getGunHeat() == 0) {
        fire(Math.min(3 - Math.abs(bearingFromGun), getEnergy() - .1));
      }
    } else {
      // Gira o canhão em direção ao inimigo
      setTurnGunRight(bearingFromGun);
    }

    // Se o inimigo estiver perto, o robo sai de perto
    if (e.getDistance() < 100) {
      setBack(50);
      setTurnRight(90);
      setAhead(150);
    } else {
      // Se o inimigo estiver longe, vai pra frente e gira
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
    // Humilha o robo, dançando o reboleixon
    for (int i = 0; i < 50; i++) {
      ahead(20);
      turnRight(30);
      back(20);
      turnLeft(30);
    }
  }
}