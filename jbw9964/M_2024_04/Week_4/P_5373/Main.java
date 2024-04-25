package jbw9964.M_2024_04.Week_4.P_5373;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

class RubiksCube    {
    public static enum Color  {
        WHITE(0), RED(1), GREEN(2),
        YELLOW(3), ORANGE(4), BLUE(5);

        private final int value;
        private Color(int value)                {this.value = value;}
        public static Color create(int index)   {
            Color color = null;
            switch (index) {
                case 0 : color = Color.WHITE;   break;
                case 1 : color = Color.RED;     break;
                case 2 : color = Color.GREEN;   break;
                case 3 : color = Color.YELLOW;  break;
                case 4 : color = Color.ORANGE;  break;
                case 5 : color = Color.BLUE;    break;
            }
            return color;
        }

        @Override
        public String toString()    {
            String str = null;
            switch (value) {
                case 0 : str = "w"; break;
                case 1 : str = "r"; break;
                case 2 : str = "g"; break;
                case 3 : str = "y"; break;
                case 4 : str = "o"; break;
                case 5 : str = "b"; break;
            }
            return str;
        }
    };
    public static enum Face   {
        UP(0), FRONT(1), LEFT(2),
        DOWN(3), BACK(4), RIGHT(5);

        private final int value;
        private Face(int value) {this.value = value;}
        public int getIndex()   {return value;}
    }
    public static enum Direction    {
        CCW, CW;
    }

    private Color[][] cube;

    public RubiksCube() {
        cube = new Color[6][9];
        initialize();
    }
    public void initialize() {
        for (Face face : Face.values()) {
            int index = face.getIndex();
            Color color = Color.create(index);
            
            for (int i = 0; i < 9; i++)
            cube[index][i] = color;
        }
    }

    public void rotateFace(Face face, Direction dir)    {
        rotateCurrentFace(face, dir);
        rotateAdjacentFace(face, dir);
    }

    private void rotateCurrentFace(Face face, Direction dir)    {
        int faceIndex = face.getIndex();
        Color[] faceColors = cube[faceIndex];

        Deque<int[]> indexDeque = new ArrayDeque<>(4);
        indexDeque.add(new int[] {0, 1});
        indexDeque.add(new int[] {2, 5});
        indexDeque.add(new int[] {8, 7});
        indexDeque.add(new int[] {6, 3});

        if (dir == Direction.CW)    {
            Deque<int[]> tempDeque = new ArrayDeque<>(3);

            while (indexDeque.size() > 1)
            tempDeque.add(indexDeque.pollLast());

            while (!tempDeque.isEmpty())
            indexDeque.add(tempDeque.pollFirst());
        }

        int[] prevIndex = indexDeque.poll();

        Color[] reserveColors = new Color[] {
            faceColors[prevIndex[0]], 
            faceColors[prevIndex[1]]
        };

        while (!indexDeque.isEmpty())   {
            int[] currentIndex = indexDeque.poll();

            for (int i = 0; i < 2; i++)
            faceColors[prevIndex[i]] = faceColors[currentIndex[i]];

            prevIndex = currentIndex;
        }

        for (int i = 0; i < 2; i++)
        faceColors[prevIndex[i]] = reserveColors[i];
    }

    @SuppressWarnings("unchecked")
    private void rotateAdjacentFace(Face face, Direction dir)   {
        Deque<?>[] queueArray = getAdjacentFaceAndIndex(face, dir);

        Deque<Face> adjacentFaceQueue = (Deque<Face>) queueArray[0];
        Deque<int[]> colorSwapIndexQueue = (Deque<int[]>) queueArray[1];

        Face prevFace = adjacentFaceQueue.poll();
        int[] prevIndex = colorSwapIndexQueue.poll();

        Color[] reserveColors = new Color[] {
            cube[prevFace.getIndex()][prevIndex[0]],
            cube[prevFace.getIndex()][prevIndex[1]],
            cube[prevFace.getIndex()][prevIndex[2]],
        };

        while (!adjacentFaceQueue.isEmpty())    {
            Face currentFace = adjacentFaceQueue.poll();
            int[] currentIndex = colorSwapIndexQueue.poll();

            for (int i = 0; i < 3; i++)
            cube[prevFace.getIndex()][prevIndex[i]] = cube[currentFace.getIndex()][currentIndex[i]];

            prevFace = currentFace;
            prevIndex = currentIndex;
        }

        for (int i = 0; i < 3; i++)
        cube[prevFace.getIndex()][prevIndex[i]] = reserveColors[i];

    }
    private Deque<?>[] getAdjacentFaceAndIndex(Face face, Direction dir)  {
        Deque<Face> adjacentFaceQueue = new ArrayDeque<>(4);
        Deque<int[]> colorSwapIndexQueue = new ArrayDeque<>(4);
        
        switch (face) {     // store indexes on clock-wise  --> works on counter clock-wise
            case UP :
                adjacentFaceQueue.add(Face.FRONT);
                colorSwapIndexQueue.add(new int[] {2, 1, 0});
                
                adjacentFaceQueue.add(Face.LEFT);
                colorSwapIndexQueue.add(new int[] {2, 1, 0});
                
                adjacentFaceQueue.add(Face.BACK);
                colorSwapIndexQueue.add(new int[] {2, 1, 0});

                adjacentFaceQueue.add(Face.RIGHT);
                colorSwapIndexQueue.add(new int[] {2, 1, 0});
                break;
        
            case FRONT :
                adjacentFaceQueue.add(Face.UP);
                colorSwapIndexQueue.add(new int[] {6, 7, 8});
                
                adjacentFaceQueue.add(Face.RIGHT);
                colorSwapIndexQueue.add(new int[] {0, 3, 6});

                adjacentFaceQueue.add(Face.DOWN);
                colorSwapIndexQueue.add(new int[] {2, 1, 0});

                adjacentFaceQueue.add(Face.LEFT);
                colorSwapIndexQueue.add(new int[] {8, 5, 2});
                break;

            case LEFT :
                adjacentFaceQueue.add(Face.UP);
                colorSwapIndexQueue.add(new int[] {0, 3, 6});
                
                adjacentFaceQueue.add(Face.FRONT);
                colorSwapIndexQueue.add(new int[] {0, 3, 6});

                adjacentFaceQueue.add(Face.DOWN);
                colorSwapIndexQueue.add(new int[] {0, 3, 6});

                adjacentFaceQueue.add(Face.BACK);
                colorSwapIndexQueue.add(new int[] {8, 5, 2});
                break;

            case DOWN :
                adjacentFaceQueue.add(Face.FRONT);
                colorSwapIndexQueue.add(new int[] {6, 7, 8});
                
                adjacentFaceQueue.add(Face.RIGHT);
                colorSwapIndexQueue.add(new int[] {6, 7, 8});

                adjacentFaceQueue.add(Face.BACK);
                colorSwapIndexQueue.add(new int[] {6, 7, 8});

                adjacentFaceQueue.add(Face.LEFT);
                colorSwapIndexQueue.add(new int[] {6, 7, 8});
                break;
            
            case BACK :
                adjacentFaceQueue.add(Face.UP);
                colorSwapIndexQueue.add(new int[] {2, 1, 0});
                
                adjacentFaceQueue.add(Face.LEFT);
                colorSwapIndexQueue.add(new int[] {0, 3, 6});

                adjacentFaceQueue.add(Face.DOWN);
                colorSwapIndexQueue.add(new int[] {6, 7, 8});

                adjacentFaceQueue.add(Face.RIGHT);
                colorSwapIndexQueue.add(new int[] {8, 5, 2});
                break;

            case RIGHT :
                adjacentFaceQueue.add(Face.UP);
                colorSwapIndexQueue.add(new int[] {8, 5, 2});
                
                adjacentFaceQueue.add(Face.BACK);
                colorSwapIndexQueue.add(new int[] {0, 3, 6});

                adjacentFaceQueue.add(Face.DOWN);
                colorSwapIndexQueue.add(new int[] {8, 5, 2});

                adjacentFaceQueue.add(Face.FRONT);
                colorSwapIndexQueue.add(new int[] {8, 5, 2});
                break;
        }

        if (dir == Direction.CW)    {
            Deque<Object> tempDeque = new ArrayDeque<>(3);
            while (adjacentFaceQueue.size() > 1)
            tempDeque.add((Object) adjacentFaceQueue.pollLast());

            while (!tempDeque.isEmpty())
            adjacentFaceQueue.add((Face) tempDeque.pollFirst());

            while (colorSwapIndexQueue.size() > 1)
            tempDeque.add((Object) colorSwapIndexQueue.pollLast());

            while (!tempDeque.isEmpty())
            colorSwapIndexQueue.add((int[]) tempDeque.pollFirst());
        }

        return new Deque[] {adjacentFaceQueue, colorSwapIndexQueue};
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int faceIndex = Face.UP.getIndex();

        for (int i = 0; i < 9; i++) {
            sb.append(cube[faceIndex][i]);
            if (i % 3 == 2) sb.append("\n");
        }

        return sb.toString();
    }
}

public class Main {
    private static final BufferedReader br = new BufferedReader(
        new InputStreamReader(System.in)
    );

    private static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        int T = Integer.parseInt(br.readLine());

        RubiksCube cube = new RubiksCube();

        while (T-- > 0) {
            br.readLine();
            String[] input = br.readLine().split(" ");

            RubiksCube.Face face = null;
            RubiksCube.Direction dir = null;

            for (String str : input)    {
                char character = str.charAt(1);

                if (character == '-')       dir = RubiksCube.Direction.CCW;
                else if (character == '+')  dir = RubiksCube.Direction.CW;

                character = str.charAt(0);

                switch (character) {
                    case 'U' : face = RubiksCube.Face.UP;       break;
                    case 'D' : face = RubiksCube.Face.DOWN;     break;
                    case 'F' : face = RubiksCube.Face.FRONT;    break;
                    case 'B' : face = RubiksCube.Face.BACK;     break;
                    case 'L' : face = RubiksCube.Face.LEFT;     break;
                    case 'R' : face = RubiksCube.Face.RIGHT;    break;
                }

                cube.rotateFace(face, dir);
            }

            sb.append(cube);
            cube.initialize();
        }

        System.out.println(sb.toString());
    }
}
