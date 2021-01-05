package models;

public class SchickWolverton {
    private double accuracy;
    private Integer[] faultTimesArray;
    private int smallN;
    private int bigN;
    private double fi;
    private double et;
    private final int NEXT_FAULT_TIME = 241;

    public SchickWolverton(double accuracy, Integer[] faultTimesArray) {
        this.accuracy = accuracy;
        this.faultTimesArray = faultTimesArray;
        this.smallN = this.faultTimesArray.length;
        this.bigN = NEXT_FAULT_TIME + 1;
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
        et = Math.sqrt(Math.PI / (2*fi*(bigN - NEXT_FAULT_TIME)));

        System.out.println("final N = " + bigN);
    }

    private double countLeftSide(){
        fi = 0;

        for(int i=1; i <= smallN; i++)
            fi += 1 / (((double) bigN - (i - 1)) * sumOfFaultTimesArraySquareElements());

        fi = 2*fi;
        return 2*fi;
    }

    private double countRightSide(){
        double result = 2 * smallN;

        double denSecPart = 0;
        for (int i=0; i<smallN; i++)
            denSecPart += i * faultTimesArray[i] * faultTimesArray[i];

        return result/(sumOfFaultTimesArraySquareElements() * bigN - denSecPart);
    }

    private double sumOfFaultTimesArraySquareElements(){
        double sum = 0;

        for (Integer value : this.faultTimesArray)
            sum += value*value;

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
