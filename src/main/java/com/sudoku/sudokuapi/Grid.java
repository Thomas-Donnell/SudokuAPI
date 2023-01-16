package com.sudoku.sudokuapi;

import java.util.ArrayList;

public class Grid {
    ArrayList<ArrayList<Integer>> rows;
    private int[][] cols;
    private int[][] boxes;
    int[][] grid;
    int[][] copy;
    int emptySpaces;
    private int solutions;

    Grid(){
        this.rows = new ArrayList<ArrayList<Integer>>();
        this.cols = new int[9][10];
        this.boxes = new int[9][10];
        this.grid = new int[9][9];
        this.copy = new int[9][9];
        this.emptySpaces = 0;
        this.solutions = 0;
        fill();
        fillGrid();
        copy();
        createPuzzle();
    }

    private void fill(){
        for(int i = 0; i < 9; i++) {
            rows.add(random());
            for (int j = 0; j < 10; j++) {
                cols[i][j] = j;
                boxes[i][j] = j;
            }
        }
    }
    private void copy(){
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++) {
                copy[i][j] = grid[i][j];
            }
        }
    }
    private void createPuzzle() {
        ArrayList<Integer> random = new ArrayList<>();
        for(int i = 0; i < 81; i++){
            random.add(i);
        }

        while (emptySpaces < 50) {
            int randomInt = (int)Math.floor(Math.random() * random.size());
            int num = random.get(randomInt);
            int row = num / 9;
            int col = num % 9;
            int box = ((row / 3) * 3) + (col / 3);
            int temp = copy[row][col];

            copy[row][col] = 0;
            rows.get(row).set(col, temp);
            cols[col][temp] = temp;
            boxes[box][temp] = temp;
            random.remove(randomInt);
            emptySpaces++;

            solve();
            if (solutions > 1) {
                copy[row][col] = temp;
                rows.get(row).set(col, 0);
                cols[col][temp] = 0;
                boxes[box][temp] = 0;
                random.add(num);
                emptySpaces--;
            }
            solutions = 0;
        }
    }
    private boolean fillGrid(){

        int boxCnt;

        for(int i = 0; i < 9; i++){

            for(int j = 0; j < 9; j++){

                boxCnt = ((i / 3) * 3) + (j / 3);

                if(grid[i][j]==0) {
                    for (int k = 0; k < rows.get(i).size(); k++) {
                        int index = rows.get(i).get(k);
                        if (index != 0 && cols[j][index] != 0 && boxes[boxCnt][index] != 0) {
                            //insert number into the grid
                            grid[i][j] = index;
                            //remove number from reference arrays
                            cols[j][index] = 0;
                            boxes[boxCnt][index] = 0;
                            rows.get(i).set(k, 0);

                            if (fillGrid()){
                                return true;
                            }
                            rows.get(i).set(k,index);
                            cols[j][index] = index;
                            boxes[boxCnt][index] = index;
                            grid[i][j] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean solve(){

        int boxCnt;

        for(int i = 0; i < 9; i++){

            for(int j = 0; j < 9; j++){

                boxCnt = ((i / 3) * 3) + (j / 3);

                if(copy[i][j]==0) {
                    for (int k = 0; k < rows.get(i).size(); k++) {
                        int index = rows.get(i).get(k);
                        if (index != 0 && cols[j][index] != 0 && boxes[boxCnt][index] != 0) {
                            //insert number into the grid
                            copy[i][j] = index;
                            //remove number from reference arrays
                            cols[j][index] = 0;
                            boxes[boxCnt][index] = 0;
                            rows.get(i).set(k, 0);

                            if (solve()){
                                return true;
                            }
                            rows.get(i).set(k,index);
                            cols[j][index] = index;
                            boxes[boxCnt][index] = index;
                            copy[i][j] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        solutions++;
        return false;
    }

    private static ArrayList<Integer> random(){
        ArrayList<Integer> temp = new ArrayList<Integer>();
        ArrayList<Integer> temp2 = new ArrayList<Integer>();
        for(int i = 1; i < 10; i++){
            temp.add(i);
        }
        for(int i = 0; i < 9; i++){
            int randomInt = (int)Math.floor(Math.random() * temp.size());
            temp2.add(temp.get(randomInt));
            temp.remove(randomInt);
        }
        return temp2;
    }
}
