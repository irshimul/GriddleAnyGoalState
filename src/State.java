import java.util.Objects;

public class State implements Comparable<State> {

        int ar[][];
        int arraySize;

        State Parent;
        //GoalState goalState;

        String move;

        int numberOfMovesUsed = 0;
        float heuristicValue = Float.POSITIVE_INFINITY;

        @Override
        public int hashCode() {
                return Objects.hashCode(ar);
        }


        @Override
        public int compareTo(State other) {
                return Float.compare(heuristicValue, other.heuristicValue);
        }


        void calculateHeuristic(GoalState goalState) {
                int misplacedTiles = 0, verticalDistance = 0;
                int[][] targetAr = goalState.getAr();

                for(int i = 1; i <= arraySize; ++i) {
                        for(int j = 1; j <= arraySize; ++j){
                                if(ar[i][j] != targetAr[i][j]) {
                                        /*int temp = Math.abs(ar[i][j] - i) + 1;
                                        verticalDistance += Math.min(temp, arraySize - temp + 1);*/
                                        int temp;
                                        if(goalState.orientation == true) {     //vertical
                                                int column = goalState.mapAr[ ar[i][j] ];
                                                temp = Math.abs(column - j) + 1;
                                        }
                                        else {                                  //horizontal
                                                int row = goalState.mapAr[ ar[i][j] ];
                                                temp = Math.abs(row - i) + 1;
                                        }
                                        verticalDistance += Math.min(temp, arraySize - temp + 1);
                                        //System.out.println(goalState.orientation + " " + verticalDistance + " " + heuristicValue);
                                }
                        }
                }
                heuristicValue = Math.min( heuristicValue, numberOfMovesUsed + (float) verticalDistance / (float) arraySize );
        }

        public void calculateHeuristicAll(GoalState[] goalStates) {
                for(int i = 0; i < goalStates.length; ++i)
                        calculateHeuristic(goalStates[i]);
        }


        void rotateRowLeft(int rowNumber) {
                int temp = ar[rowNumber][1]; // saving the first element
                for(int j = 1; j < arraySize; ++j)
                        ar[rowNumber][j] = ar[rowNumber][j + 1];
                ar[rowNumber][arraySize] = temp;

                move = "ROW " + rowNumber + " LEFT";
        }

        void rotateRowRight(int rowNumber) {
                int temp = ar[rowNumber][arraySize];
                for(int j = arraySize; j > 1; --j)
                        ar[rowNumber][j] = ar[rowNumber][j - 1];
                ar[rowNumber][1] = temp;

                move = "ROW " + rowNumber + " RIGHT";
        }

        void rotateColumnUp(int columnNumber) {
                int temp = ar[1][columnNumber]; // saving the first element
                for(int i = 1; i < arraySize; ++i)
                        ar[i][columnNumber] = ar[i + 1][columnNumber];
                ar[arraySize][columnNumber] = temp;

                move = "COLUMN " + columnNumber + " UP";
        }

        void rotateColumnDown(int columnNumber) {
                int temp = ar[arraySize][columnNumber];
                for(int i = arraySize; i > 1; --i)
                        ar[i][columnNumber] = ar[i - 1][columnNumber];
                ar[1][columnNumber] = temp;

                move = "COLUMN " + columnNumber + " DOWN";
        }



        public State(int arraySize) {
                this.arraySize = arraySize;
                this.ar = new int[arraySize + 1][arraySize + 1];
        }

        public State(State parent) {
                Parent = parent;
                this.arraySize = parent.arraySize;
                this.ar = new int[arraySize + 1][arraySize + 1];
                //this.goalState = Parent.goalState;

                for(int i = 1; i <= arraySize; ++i) {
                        for(int j = 1; j <= arraySize; ++j){
                                this.ar[i][j] = parent.ar[i][j];
                        }
                }

                setNumberOfMovesUsed();
        }

        public void printAr() {
                for(int i = 1; i <= arraySize; ++i) {
                        for(int j = 1; j <= arraySize; ++j) {
                                System.out.print(ar[i][j] + " ");
                        }
                        System.out.println();
                }
                System.out.println();
        }

        public void setParentNull() {
                Parent = null;
        }

        public int getNumberOfMovesUsed() {
                return numberOfMovesUsed;
        }

        public void setNumberOfMovesUsed() {
                if(Parent != null)
                        this.numberOfMovesUsed = Parent.numberOfMovesUsed + 1;
        }


        public int[][] getAr() {
                return ar;
        }

        public void setAr(int[][] ar) {
                this.ar = ar;
        }

        public State getParent() {
                return Parent;
        }

        public void setParent(State parent) {
                Parent = parent;
        }

        public String getMove() {
                return move;
        }

        public void setMove(String move) {
                this.move = move;
        }

        public void printMove() {
                System.out.println(move);
        }
}
