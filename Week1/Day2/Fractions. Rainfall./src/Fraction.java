public class Fraction {

//VARIABLES
    private long _numerator, _denominator;
    private long _gcd;



    //GCD
    //Step 1: Find the product of a and b.
    // Step 2: Now, find the least common multiple (LCM) of a and b.
    // Step 3: Divide the product of 'a' and 'b' by the LCM of a and b.
    // Step 4: The obtained value after division is the greatest common divisor (GCD) of (a, b).
    private long gcd(long num, long den) {
        long gcd = _numerator;
        long _remainder = _denominator;
        while (_remainder != 0) {
            long temp = _remainder;
            _remainder = gcd % _remainder;
            gcd = temp;
        }
        return (int) gcd;
    }

    //REDUCE
    //Reduce fractions by dividing the numerator and the denominator by the largest number that can divide into both numbers exactly (GCD).
    private void reduce(){
        _gcd = gcd((int) _numerator, (int) _denominator);
        _numerator = _numerator/_gcd;
        _denominator = _denominator/_gcd;
    }


    //DEFAULT CONSTRUCTOR
    public Fraction() {
        _numerator = 0;
        _denominator = 1;
        _gcd = 1;
    }

    //CONSTRUCTOR
    public Fraction( int numerator, int denominator){
        //protecting program in case someone could make the dem 0, which would crash our program
        if ((denominator == 0)) throw new AssertionError();

        _numerator = numerator;
        _denominator = denominator;

        reduce(); //gcd set in here

        //If fraction is negative, the -1 symbol will only be on the numerator
        if(_denominator < 0){
            _numerator = _numerator * -1;
            _denominator = _denominator * -1;
        }

    }





//FRACTION (+)
    //Fraction plus( Fraction rhs ) - Returns a new fraction that is the result of the right hand side (rhs) fraction added to this fraction.
public Fraction plus( Fraction rhs){
    int resultNum, resultDem;

    //Ensure that the bottom numbers (denominators) are the same.
    if(_denominator == rhs._denominator){
        resultDem = (int) _denominator;
        //add the top numbers (nominators) and place the result over the common denominator.
        resultNum = (int) (_numerator + rhs._numerator);
    }
    else {
        //If they are not, change them so that they are the same (they have a common denominator)
        resultDem = (int) (_denominator * rhs._denominator);
        //Add the top numbers (nominators) and place the result over the common denominator.
        resultNum = (int) (_numerator*(rhs._denominator) + rhs._numerator*(_denominator));
    }

    //making a new fraction object, so pass out new values into the fraction constructor to update all of our
    //properties
    return new Fraction( resultNum, resultDem);

}

    //FRACTION (-)
    //Fraction minus(Fraction rhs) - Returns a new fraction that is the result of the right hand side (rhs) fraction subtracted from this fraction.
    public Fraction minus( Fraction rhs){
        int resultNum, resultDem;

        //Ensure that the bottom numbers (denominators) are the same.
        if(_denominator == rhs._denominator){
            resultDem = (int) _denominator;
            //subtract the top numbers (nominators) and place the result over the common denominator.
            resultNum = (int) (_numerator - rhs._numerator);
        }
        else {
            //If they are not, change them so that they are the same (they have a common denominator)
            resultDem = (int) (_denominator * rhs._denominator);
            //Subtract the top numbers (nominators) and place the result over the common denominator.
            resultNum = (int) (_numerator*(rhs._denominator) - rhs._numerator*(_denominator));
        }

        //making a new fraction object, so pass out new values into the fraction constructor to update all of our
        //properties
        return new Fraction( resultNum, resultDem);

    }

    //TIMES (*)
    //Fraction times(Fraction rhs) - Returns a new fraction that is the result of this fraction multiplied by the right hand side (rhs) fraction.
    public Fraction times( Fraction rhs){
        int resultNum, resultDem;

        resultDem = (int) (_denominator * rhs._denominator);
        resultNum = (int) (_numerator * rhs._numerator);


        //making a new fraction object, so pass out new values into the fraction constructor to update all of our
        //properties
        return new Fraction( resultNum, resultDem);
    }


    //RECIPROCAL
    //Fraction reciprocal() - Returns a new fraction that is the reciprocal of this fraction.
    //Or in other words, swap the values of the numerator and the denominator
    public void reciprocal (){

        //Swap function
        int tempNum = 0;
        tempNum = (int) _numerator;
        _numerator = _denominator;
        _denominator = tempNum;

        //making a new fraction object, so pass out new values into the fraction constructor to update all of our
        //properties
        new Fraction((int) _numerator, (int) _denominator);
    }

    //DIVIDE-BY
    // Fraction dividedBy(Fraction rhs) - Returns a new fraction that is the result of this fraction divided by the right hand side (rhs) fraction.
    //To divide a fraction you find the reciprocal of the rhs and multiply rhs & lhs respective denominators and numerators
    public Fraction dividedBy( Fraction rhs){
        int resultNum, resultDem;

        //Find reciprocal of rhs
        rhs.reciprocal();

        //Multiply lhs and rhs denominators and numerators
        resultDem = (int) (_denominator * rhs._denominator);
        resultNum = (int) (_numerator * rhs._numerator);


        //making a new fraction object, so pass out new values into the fraction constructor to update all of our
        //properties
        return new Fraction( resultNum, resultDem);
    }

    // TO-DOUBLE
    //double toDouble() - Returns a (double precision) floating point number that is the value of the fraction as a real number.
    public double toDouble() {
        //recasting return to a double
        return (double) _numerator / _denominator;
    }

    // TO-STRING
    //String toString() - Returns a string representing this fraction. The string should have the format: "N/D",
    // where N is the numerator, and D is the denominator.
    public String toString() {
        //Concatenating with a string between two variables will convert the whole line to string
        return _numerator + "/" + _denominator;
    }




}

//    private void computeQRR(){
//        _realRepresentation = (int) ((_numerator * 1.0) / _denominator); //real num needs to be a float
//        _quotient = _numerator / _denominator;
//        _remainder = _numerator % _denominator;
//    }
