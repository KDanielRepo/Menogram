import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class PathFinder {
    int[][] mapa = new int[96][64];
    Node[][] map = new Node[96][64];
    ArrayList closed = new ArrayList();
    ArrayList open = new ArrayList();
    List<Integer> movesX = new ArrayList<>();
    List<Integer> movesY = new ArrayList<>();
    private int finishX;
    private int finishY;
    private int startX = -1;
    private int startY = -1;
    private int checks = 0;
    private int lenght = 0;
    boolean bulean = true;

    public PathFinder(int sx, int sy, int fx, int fy) throws IOException {
        clear();
        finishX = fx;
        finishY = fy;
        startX = sx;
        startY = sy;
        Node current = map[sx][sy];
        current.setCellType(0);
        current.setHops(0);
        map[fx][fy].setCellType(1);
        AStar();
        //System.out.println(map[sx][sy].getCellType());
        //System.out.println(sx+" "+sy);
        //System.out.println(map[fx][fy].getCellType());
        //System.out.println(fx+" "+fy);

        /*for (int i = 0; i<64;i++){
            for(int j = 0; j < 96;j++){
                System.out.println(map[j][i]);
            }
        }*/
    }

    public PathFinder() {

    }

    public void clear() throws IOException {
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 96; j++) {
                map[j][i] = new Node(3, j, i);
            }
        }
        getCurrentMap();
    }

    public void getCurrentMap() throws IOException {
        FileReader fileReader = new FileReader(new File("C:\\Menogram\\src\\main\\java\\testowanie.txt"));
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 96; j++) {
                Node current;
                current = map[j][i];
                if (bufferedReader.read()==48) {
                    current.setCellType(2);
                } else{
                    current.setCellType(3);
                }
            }
            bufferedReader.skip(2);
        }
    }
    public void AStar() {
        ArrayList<Node> priority = new ArrayList<Node>();
        priority.add(map[startX][startY]);
        while (bulean) {
            if (priority.size() <= 0) {
                bulean = false;
                break;
            }
            //System.out.println(priority.size());
            int hops = priority.get(0).getHops() + 1;
            //System.out.println("hops: "+hops);
            ArrayList<Node> explored = exploreNeighbors(priority.get(0), hops);
            //System.out.println("size: "+explored.size());
            if (explored.size() > 0) {
                priority.remove(0);
                priority.addAll(explored);
                //System.out.println(priority.size());
            } else {
                priority.remove(0);
            }
            sortQue(priority);
        }
        if (!bulean) {
            System.out.println(priority.size());
            for (int i = 0; i < 64; i++) {
                for (int j = 0; j < 96; j++) {
                    if (map[j][i].getCellType() == 5) {
                        System.out.println(j + " " + i);
                        movesX.add(j);
                        movesY.add(i);
                    }
                }
            }
        }
    }

    public ArrayList<Node> sortQue(ArrayList<Node> sort) {
        int c = 0;
        while (c < sort.size()) {
            int sm = c;
            for (int i = c + 1; i < sort.size(); i++) {
                if (sort.get(i).getEuclidDist() + sort.get(i).getHops() < sort.get(sm).getEuclidDist() + sort.get(sm).getHops())
                sm = i;
            }
            if (c != sm) {
                Node temp = sort.get(c);
                sort.set(c, sort.get(sm));
                sort.set(sm, temp);
            }
            c++;
        }
        return sort;
    }

    public void explore(Node current, int lastx, int lasty, int hops) {
        if (current.getCellType() != 0 && current.getCellType() != 1)
            current.setCellType(4);
        current.setLastNode(lastx, lasty);
        current.setHops(hops);
        checks++;
        if (current.getCellType() == 1) {
            backtrack(current.getLastX(), current.getLastY(), hops);
        }

    }

    public ArrayList<Node> exploreNeighbors(Node current, int hops) {
        ArrayList<Node> explored = new ArrayList<Node>();
        for (int a = -1; a <= 1; a++) {
            for (int b = -1; b <= 1; b++) {
                int boundX = current.getX() + a;
                int boundY = current.getY() + b;
                if ((boundX > -1 && boundX < 96) && (boundY > -1 && boundY < 64)) {
                    Node neighbor = map[boundX][boundY];
                    if ((neighbor.getHops() == -1 || neighbor.getHops() > hops) && neighbor.getCellType() != 2) {
                        explore(neighbor, current.getX(), current.getY(), hops);
                        explored.add(neighbor);
                    }
                }
            }
        }
        return explored;
    }

    public void backtrack(int lx, int ly, int hops) {
        lenght = hops;
        while (hops > 1) {
            Node current = map[lx][ly];
            current.setCellType(5);
            lx = current.getLastX();
            ly = current.getLastY();
            hops--;
        }
        bulean = false;
    }

    @Getter
    @Setter
    class Node {
        private int cellType = 0;
        private int hops;
        private int x;
        private int y;
        private int lastX, lastY;
        private double dToEnd = 0;

        public Node(int type, int x, int y) {
            cellType = type;
            this.x = x;
            this.y = y;
            hops = -1;
        }

        public double getEuclidDist() {
            int xdif = Math.abs(x - finishX);
            int ydif = Math.abs(y - finishY);
            dToEnd = Math.sqrt((xdif * xdif) + (ydif * ydif));
            return dToEnd;
        }

        public void setLastNode(int x, int y) {
            lastX = x;
            lastY = y;
        }
    }
}
