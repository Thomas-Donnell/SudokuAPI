package com.sudoku.sudokuapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class SudokuAPIController {

    @GetMapping("/")
    public ArrayList<int[][]> sudoku() {
        Grid grid = new Grid();
        ArrayList<int[][]> arr = new ArrayList<>();
        arr.add(grid.grid);
        arr.add(grid.copy);
        return arr;
    }
}
