package Bridge;

import java.awt.Color;

public class GoldenBullet extends BulletTypeAbstraction{


	public GoldenBullet(int x, int y, double angle, BulletModeInplementor mode, int damage) {
		if (x < 0) {
			throw new IllegalArgumentException("Coordinate x is negative in GoldenBullet(int x, int y, double angle, BulletModeInplementor mode)");
		}
		if (y < 0) {
			throw new IllegalArgumentException("Coordinate y is negative in GoldenBullet(int x, int y, double angle, BulletModeInplementor mode)");
		}
		if (angle < 0) {
			throw new IllegalArgumentException("Angle is negative in GoldenBullet(int x, int y, double angle, BulletModeInplementor mode)");
		}
		if (mode == null) {
			throw new IllegalArgumentException("Mode is null in GoldenBullet(int x, int y, double angle, BulletModeInplementor mode)");
		}
		
		this.x = x;
		this.y = y;
		this.r = 8;
		this.color = Color.YELLOW;
		
		this.damage = damage;
		rad = Math.toRadians(angle);
		dx = Math.cos(rad);
		dy = Math.sin(rad);
		speed = 10;
		
		this.mode = mode;
	}
}
