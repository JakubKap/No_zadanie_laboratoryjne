package models;

public class JelinskiMoranda extends ReliabilityModel{

    public JelinskiMoranda(double accuracy, Integer[] faultTimesArray) {
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
        et = 1d / (fi*(bigN - NEXT_FAULT_TIME));
    }

    double countLeftSide(){
        double result = 0;

        for(int i=1; i <= smallN; i++)
            result += 1d / (bigN - (i - 1));

        return result;
    }

    double countRightSide(){
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
}
