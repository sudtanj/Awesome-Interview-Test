package com.tokopedia.testproject.problems.algorithm.waterJug;

public class Solution {

    public static int minimalPourWaterJug(int jug1, int jug2, int target) {
        // TODO, return the smallest number of POUR action to do the water jug problem
        // below is stub, replace with your implementation!
        int counter1=0,counter2=0;
        Jug jugOne=new Jug(jug1);
        Jug jugTwo=new Jug(jug2);
        while(jugOne.getCurrentValue()!=target && jugTwo.getCurrentValue()!=target) {
            if(jugOne.getCurrentValue()==0){
                jugOne.fillWater();
            }
            if(jugTwo.getCurrentValue()==jugTwo.getCapacity()){
                jugTwo.drainWater();
            }
            jugTwo.pourFrom(jugOne);
            counter1++;
        }
        jugOne=new Jug(jug2);
        jugTwo=new Jug(jug1);
        while(jugOne.getCurrentValue()!=target && jugTwo.getCurrentValue()!=target) {
            if(jugOne.getCurrentValue()==0){
                jugOne.fillWater();
            }
            if(jugTwo.getCurrentValue()==jugTwo.getCapacity()){
                jugTwo.drainWater();
            }
            jugTwo.pourFrom(jugOne);
            counter2++;
        }
        return Math.min(counter1,counter2);
    }
}
