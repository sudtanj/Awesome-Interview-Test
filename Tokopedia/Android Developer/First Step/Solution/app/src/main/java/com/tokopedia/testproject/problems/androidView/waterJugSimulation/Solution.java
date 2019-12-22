package com.tokopedia.testproject.problems.androidView.waterJugSimulation;

import com.tokopedia.testproject.problems.algorithm.waterJug.Jug;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public static List<WaterJugAction> simulateWaterJug(int jug1, int jug2, int target) {
        // TODO, simulate the smallest number of action to do the water jug problem
        // below is stub, replace with your implementation!
        int counter1=0,counter2=0;
        Jug jugOne=new Jug(jug1);
        Jug jugTwo=new Jug(jug2);
        List<WaterJugAction> list1 = new ArrayList<>();
        while(jugOne.getCurrentValue()!=target && jugTwo.getCurrentValue()!=target) {
            if(jugOne.getCurrentValue()==0){
                jugOne.fillWater();
                list1.add(new WaterJugAction(WaterJugActionEnum.FILL, 1));
            }
            if(jugTwo.getCurrentValue()==jugTwo.getCapacity()){
                jugTwo.drainWater();
                list1.add(new WaterJugAction(WaterJugActionEnum.EMPTY, 2));
            }
            jugTwo.pourFrom(jugOne);
            list1.add(new WaterJugAction(WaterJugActionEnum.POUR, 2));
            counter1++;
        }
        jugOne=new Jug(jug2);
        jugTwo=new Jug(jug1);
        List<WaterJugAction> list2 = new ArrayList<>();
        while(jugOne.getCurrentValue()!=target && jugTwo.getCurrentValue()!=target) {
            if(jugOne.getCurrentValue()==0){
                jugOne.fillWater();
                list2.add(new WaterJugAction(WaterJugActionEnum.FILL, 1));
            }
            if(jugTwo.getCurrentValue()==jugTwo.getCapacity()){
                jugTwo.drainWater();
                list2.add(new WaterJugAction(WaterJugActionEnum.EMPTY, 2));
            }
            jugTwo.pourFrom(jugOne);
            list2.add(new WaterJugAction(WaterJugActionEnum.POUR, 2));
            counter2++;
        }
        if(counter1<counter2){
            return list1;
        }
        return list2;
    }
}
