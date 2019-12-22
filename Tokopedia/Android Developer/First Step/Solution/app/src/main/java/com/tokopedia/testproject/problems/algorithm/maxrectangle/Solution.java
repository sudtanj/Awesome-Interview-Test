package com.tokopedia.testproject.problems.algorithm.maxrectangle;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Solution {
    public static int maxRect(int[][] matrix) {
        // TODO, return the largest area containing 1's, given the 2D array of 0s and 1s
        // below is stub
        List<Integer> result=new ArrayList<>();
        for(int i=0;i<matrix.length-1;i++){
            for(int j=0;j<matrix[0].length-1;j++){
                if(matrix[i][j]==1){
                    int col=j,row=0;
                    for(int l=j+1;l<matrix[0].length;l++){
                        if(matrix[i+row][l]!=1){
                            break;
                        }
                        col++;
                    }
                    while(matrix[i+row][j]==1){
                        for(int l=j+1;l<col;l++){
                            if(matrix[i+row][l]!=1){
                                col=l;
                                break;
                            }
                        }
                        row++;
                    }
                    result.add((((col-j)+1)*((row-i)+1)));
                }
            }
        }
        int maximum=Integer.MIN_VALUE;
        for(Integer temp:result){
            if(maximum<temp){
                maximum=temp;
            }
        }
        System.out.println(maximum);
        return 0;
    }

}
