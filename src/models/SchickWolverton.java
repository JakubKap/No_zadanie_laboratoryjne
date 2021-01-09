package models;

public class SchickWolverton extends ReliabilityModel{

    public SchickWolverton(double accuracy, Integer[] faultTimesArray) {
        super(accuracy, faultTimesArray);
    }

    public void estimateBigNAndFi(){
        double leftSide;
        double rightSide;

        do {
            leftSide = countLeftSide();
            rightSide = countRightSide();

            bigN++;
        }

        while(Math.abs(leftSide - rightSide) > accuracy);

        bigN = bigN - 1;
        et = Math.sqrt(Math.PI / (2*fi*(bigN - NEXT_FAULT_TIME)));
    }

    double countLeftSide(){
        fi = 0;

        for(int i=1; i <= smallN; i++)
            fi += 1 / (((double) bigN - (i - 1)) * sumOfFaultTimesArraySquareElements());

        fi = 2*fi;
        return 2*fi;
    }

    double countRightSide(){
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
}
