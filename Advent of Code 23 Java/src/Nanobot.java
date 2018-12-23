import java.util.ArrayList;

public class Nanobot {
    int X;
    int Y;
    int Z;
    int Radius;

    ArrayList<Nanobot> nanobotsTouching = new ArrayList<>();

    Nanobot(String s) {
        //pos=<0,0,0>, r=4
        X = Integer.parseInt(s.substring(s.indexOf("<")+1, s.indexOf(",")));
        s = s.substring(s.indexOf(",")+1);
        Y = Integer.parseInt(s.substring(0, s.indexOf(",")));
        s = s.substring(s.indexOf(",")+1);
        Z = Integer.parseInt(s.substring(0, s.indexOf(">")));
        s = s.substring(s.indexOf("=")+1);
        Radius = Integer.parseInt(s);
    }

    boolean IsInRange(int x, int y, int z) {
        if (Math.abs(x - X) + Math.abs(y-Y) + Math.abs(z-Z) <= Radius) {
            return true;
        }
        return false;
    }

    boolean IsInTouchingRadius(Nanobot n) {
        final int x = Math.abs(n.X - X);
        final int y = Math.abs(n.Y - Y);
        final int z = Math.abs(n.Z - Z);
        return x + y + z <= n.Radius + Radius;
    }
}
