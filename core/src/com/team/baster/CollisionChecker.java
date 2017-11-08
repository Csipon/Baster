package com.team.baster;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Pasha on 11/8/2017.
 */

public class CollisionChecker {
    public static boolean intersect(Rectangle rect, Circle circle) {
        float halfHeight = rect.height / 2;
        float halfWidth = rect.width / 2;
        float cx = Math.abs(circle.x - rect.x - halfWidth);
        float xDist = halfWidth + circle.radius;
        if (cx > xDist)
            return false;
        float cy = Math.abs(circle.y - rect.y - halfHeight);
        float yDist = halfHeight + circle.radius;
        if (cy > yDist)
            return false;
        if (cx <= halfWidth || cy <= halfHeight)
            return true;
        float xCornerDist = cx - halfWidth;
        float yCornerDist = cy - halfHeight;
        float xCornerDistSq = xCornerDist * xCornerDist;
        float yCornerDistSq = yCornerDist * yCornerDist;
        float maxCornerDistSq = circle.radius * circle.radius;
        return xCornerDistSq + yCornerDistSq <= maxCornerDistSq;
    }

    public static boolean hasCollision(Circle c1, Circle c2){
        double xDiff = c1.x - c2.x;
        double yDiff = c1.y - c2.y;

        double distance = Math.sqrt((Math.pow(xDiff, 2) + Math.pow(yDiff, 2)));

        return distance < (c1.radius + c2.radius);
    }
}
