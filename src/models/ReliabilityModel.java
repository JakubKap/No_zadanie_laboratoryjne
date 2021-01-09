package models;

public abstract class ReliabilityModel {
    double accuracy;
    Integer[] faultTimesArray;
    int smallN;
    int bigN;
    double fi;
    double et;
    final int NEXT_FAULT_TIME = 241;

    public ReliabilityModel(double accuracy, Integer[] faultTimesArray) {
        this.accuracy = accuracy;
        this.faultTimesArray = faultTimesArray;
        this.smallN = this.faultTimesArray.length;
        this.bigN = NEXT_FAULT_TIME;
        this.fi = 0;
    }

    public abstract void estimateBigNAndFi();
    abstract double countLeftSide();
    abstract double countRightSide();

    public int getSmallN() {
        return smallN;
    }

    public int getBigN() {
        return bigN;
    }

    public double getFi() {
        return fi;
    }

    public double getEt() {
        return et;
    }

    public int getNEXT_FAULT_TIME() {
        return NEXT_FAULT_TIME;
    }
}
