package classes;

import model.Point;

public class AreaChecker {

    public AreaChecker(){    }

    public boolean check(Point p){
        double x = p.getX();
        double y = p.getY();
        double r = p.getR();
        return check(x, y, r);
    }

    public boolean check(double x, double y, double r){
        return (
                (x >= 0 && x <= r/2 && y <= 0 && y >= -r)
                        ||(x <= 0 && y <= 0 && y >= -x - r/2)
                        ||(x >= 0 && y >= 0 && Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r/2, 2) )
        );
    }
}
