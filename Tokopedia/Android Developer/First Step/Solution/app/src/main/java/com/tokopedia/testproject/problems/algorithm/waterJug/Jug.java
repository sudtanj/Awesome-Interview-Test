package com.tokopedia.testproject.problems.algorithm.waterJug;

public class Jug {
    private int capacity=0;
    private int currentValue=0;
    public Jug(int capacity){
        this.currentValue=0;
        this.capacity=capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentValue() {

        return currentValue;
    }

    public void pourFrom(Jug otherJug){
        while(currentValue!=capacity){
            if(otherJug.currentValue>0) {
                this.currentValue += 1;
                otherJug.currentValue-=1;
            } else {
                break;
            }
        }
    }

    public void fillWater(){
        this.currentValue=capacity;
    }

    public void drainWater(){
        this.currentValue=0;
    }
}
