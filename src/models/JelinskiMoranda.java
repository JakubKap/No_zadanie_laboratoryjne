package models;

public class JelinskiMoranda {
    private double accuracy;
    private Integer[] faultTimesArray;
    private int smallN;
    private int bigN;
    private double fi;
    private double et;
    private final int NEXT_FAULT_TIME = 241;

    public JelinskiMoranda(double accuracy, Integer[] faultTimesArray) {
        this.accuracy = accuracy;
        System.out.println(accuracy);
        this.faultTimesArray = faultTimesArray;
        this.smallN = this.faultTimesArray.length;
        this.bigN = 242;
        //Sebastian przyjął 242
        this.fi = 0;
        //0.001
    }

    public void estimateBigNAndFi(){
        double leftSide;
        double rightSide;

        do {
            leftSide = countLeftSide();
            rightSide = countRightSide();

            System.out.println("N = " + bigN);
            System.out.println("leftSide = " + leftSide);
            System.out.println("rightSide = " + rightSide);
            System.out.println("fault =" + Math.abs(leftSide - rightSide));
            boolean cond = Math.abs(leftSide - rightSide) > accuracy;
            System.out.println("condition = " + cond);

            bigN++;
        }

        while(Math.abs(leftSide - rightSide) > accuracy);

        bigN = bigN - 1;
        et = 1 / (fi*(bigN - NEXT_FAULT_TIME));

        System.out.println("final N = " + bigN);
    }

    private double countLeftSide(){
        double result = 0;

        for(int i=1; i <= smallN; i++)
            result += 1 / ((double) bigN - (i - 1));

        return result;
    }

    private double countRightSide(){
        fi = smallN;

        double denSecPart = 0;
        for(int i=0; i<smallN; i++)
            denSecPart += i * faultTimesArray[i];

        fi = fi / (bigN * sumOfFaultTimesArrayElements() - denSecPart);

        return fi * sumOfFaultTimesArrayElements();
    }

    private double sumOfFaultTimesArrayElements(){
        double sum = 0;

        for (Integer value : this.faultTimesArray)
            sum += value;

        return sum;
    }

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
