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

        for(int i=0; i < smallN; i++)
            fi += 1d / ((bigN - i) * sumOfFaultTimesArraySquareElements());

        fi = 2*fi;
        return fi;
    }

    double countRightSide(){
        double numerator = 2 * smallN;

        double denSecPart = 0;
        for (int i=0; i<smallN; i++)
            denSecPart += i * Math.pow(faultTimesArray[i],2);

        double denFirstPart = sumOfFaultTimesArraySquareElements() * bigN;
        double denominator = denFirstPart - denSecPart;

        return numerator/denominator;
    }

    private double sumOfFaultTimesArraySquareElements(){
        double sum = 0;

        for (Integer value : this.faultTimesArray)
            sum += value*value;

        return sum;
    }
}
