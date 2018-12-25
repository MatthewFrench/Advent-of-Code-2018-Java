public class Point {
    int X;
    int Y;
    int Z;
    int W;

    Point(int x, int y, int z, int w) {
        X = x;
        Y = y;
        Z = z;
        W = w;
    }

    public int DistanceToPoint(Point p) {
        return Math.abs(p.X - X) + Math.abs(p.Y - Y) + Math.abs(p.Z - Z) + Math.abs(p.W - W);
    }
}
