import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

public class Main {
    public static void main(String[] args) {
        part1();
        part2();
    }

    public static void part1() {
        String originalInput = Input.get();
        System.out.println("Part 1 Starting");

        String[] lines = originalInput.split("\n");
        ArrayList<ArrayList<GridLocation>> grid = new ArrayList<>();
        ArrayList<Cart> carts = new ArrayList<>();
        int x = 0;
        int y = 0;
        for (String line : lines) {
            ArrayList<GridLocation> row = new ArrayList<>();
            grid.add(row);
            for (char c : line.toCharArray()) {
                GridLocation location = new GridLocation();
                row.add(location);
                location.X = x;
                location.Y = y;
                location.Character = c;
                if (c == '>' || c == '<') {
                    Cart cart = new Cart();
                    cart.X = x;
                    cart.Y = y;
                    cart.Direction = c;
                    carts.add(cart);
                    location.Character = '-';
                    location.HasCart = cart;
                } else if (c == '^' || c == 'v') {
                    Cart cart = new Cart();
                    cart.X = x;
                    cart.Y = y;
                    cart.Direction = c;
                    carts.add(cart);
                    location.Character = '|';
                    location.HasCart = cart;
                }
                x += 1;
            }
            y += 1;
            x = 0;
        }

        boolean noCrash = true;
        crashWhile:
        while (true) {
            if (carts.size() == 1) {
                System.out.println("Last cart at " + carts.get(0).X + ", " + carts.get(0).Y);
                break crashWhile;
            }
            for (y = 0; y < grid.size(); y++) {
                ArrayList<GridLocation> row = grid.get(y);
                for (x = 0; x < row.size(); x++) {
                    GridLocation location = row.get(x);
                    //Move cart
                    if (location.HasCart != null && !location.HasCart.DidMove) {
                        Cart c = location.HasCart;
                        c.DidMove = true;
                        location.HasCart = null;
                        int newX = c.X;
                        int newY = c.Y;
                        if (c.Direction == '>') {
                            newX += 1;
                        } else if (c.Direction == '<') {
                            newX -= 1;
                        } else if (c.Direction == '^') {
                            newY -= 1;
                        } else if (c.Direction == 'v') {
                            newY += 1;
                        }
                        c.X = newX;
                        c.Y = newY;
                        GridLocation newLocation = grid.get(c.Y).get(c.X);
                        if (newLocation.HasCart != null) {
                            carts.remove(newLocation.HasCart);
                            carts.remove(c);
                            newLocation.HasCart = null;
                            location.HasCart = null;
                            System.out.println("Carts left: " + carts.size());
                        } else {
                            newLocation.HasCart = c;
                        }
                        //Update cart direction
                        if (newLocation.Character == '+') {
                            if (c.Step == 0) {
                                if (c.Direction == '<') {
                                    c.Direction = 'v';
                                } else if (c.Direction == 'v') {
                                    c.Direction = '>';
                                } else if (c.Direction == '>') {
                                    c.Direction = '^';
                                } else if (c.Direction == '^') {
                                    c.Direction = '<';
                                }
                            } else if (c.Step == 2) {
                                if (c.Direction == '<') {
                                    c.Direction = '^';
                                } else if (c.Direction == 'v') {
                                    c.Direction = '<';
                                } else if (c.Direction == '>') {
                                    c.Direction = 'v';
                                } else if (c.Direction == '^') {
                                    c.Direction = '>';
                                }
                            }
                            c.Step += 1;
                            if (c.Step > 2) {
                                c.Step = 0;
                            }
                        } else if (newLocation.Character == '\\') {
                            if (c.Direction == '>') {
                                c.Direction = 'v';
                            } else if (c.Direction == '^') {
                                c.Direction = '<';
                            } else if (c.Direction == '<') {
                                c.Direction = '^';
                            } else if (c.Direction == 'v') {
                                c.Direction = '>';
                            }
                        } else if (newLocation.Character == '/') {
                            if (c.Direction == '>') {
                                c.Direction = '^';
                            } else if (c.Direction == 'v') {
                                c.Direction = '<';
                            } else if (c.Direction == '<') {
                                c.Direction = 'v';
                            } else if (c.Direction == '^') {
                                c.Direction = '>';
                            }
                        }
                    }
                }
            }
            //Reset carts
            for (Cart c : carts) {
                c.DidMove = false;
            }
            //Draw grid
/*
            for (y = 0; y < grid.size(); y++) {
                String rowString = "";
                ArrayList<GridLocation> row = grid.get(y);
                for (x = 0; x < row.size(); x++) {
                    GridLocation location = row.get(x);
                    if (location.HasCart == null) {
                        rowString += location.Character;
                    } else {
                        rowString += location.HasCart.Direction;
                    }
                }
                System.out.println(rowString);
            }
            System.out.println("-------------");
*/
        }


        System.out.println("Part 1 Answer: max X: ");
    }

    public static void part2() {
        /*
        String originalInput = Input.get();
        System.out.println("Part 2 Starting");

        Marble n = new Marble();
        n.Generate(
                Arrays.stream(originalInput.split(" ")).map(Integer::parseInt).collect(Collectors.toList()).toArray(new Integer[0])
                ,0);

        System.out.println("Part 2 Answer: Sum of metadata: " + n.SumOfMetadataByChildNodes());
        */
    }
}
