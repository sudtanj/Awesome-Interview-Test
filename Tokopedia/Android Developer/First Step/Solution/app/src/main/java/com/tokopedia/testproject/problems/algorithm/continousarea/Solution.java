package com.tokopedia.testproject.problems.algorithm.continousarea;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hendry on 18/01/19.
 */
public class Solution {
    public static int maxContinuousArea(int[][] matrix) {
        // TODO, return the largest continuous area containing the same integer, given the 2D array with integers
        // below is stub
        return totalContainusArea(matrix);
    }

    public static int totalContainusArea(int[][] matrix){
        int highestNumber=Integer.MIN_VALUE;
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){
                int temp=howDeep(matrix,matrix[i][j],i,j,i,j);
                if(highestNumber<temp)
                    highestNumber=temp;
            }
        }
        return highestNumber;
    }

    public static int howDeep(int[][] matrix,int target,int x,int y,int prevX,int prevY){
        int counter=1;
        if(x>0){
            if(matrix[x-1][y] == target){
                if(x-1 != prevX)
                    counter=counter+howDeep(matrix,target,x-1,y,x,y);
            }
        }
        if(x<matrix.length-1){
            if(matrix[x+1][y]==target){
                if(x+1 != prevX)
                    counter=counter+howDeep(matrix,target,x+1,y,x,y);
            }
        }
        if(y>0){
            if(matrix[x][y-1]==target){
                if(y-1 != prevY)
                    counter=counter+howDeep(matrix,target,x,y-1,x,y);
            }
        }
        if(y<matrix[0].length-1){
            if(matrix[x][y+1]==target){
                if(prevY != y+1)
                    counter=counter+howDeep(matrix,target,x,y+1,x,y);
            }
        }
        /*
        if(x>0 && y>0){
            if(matrix[x-1][y-1]==target){
                if((x-1 != prevX) && (y-1 != prevY))
                return howDeep(matrix,target,x-1,y-1,x,y)+1;
            }
        }
        if(x<matrix.length-1 && y<matrix[0].length-1){
            if(matrix[x+1][y+1]==target){
                if((x+1 != prevX) && (y+1 != prevY))
                return howDeep(matrix,target,x+1,y+1,x,y)+1;
            }
        }
        */
        return counter;
    }
}
