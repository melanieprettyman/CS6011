import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

class FractionTest {

    @Test
    public void runATest() {
        // Qualify the assertEquals() with "Assertions." to say that it comes from the Assertions library.
        // Assertions library, as can be seen from the import, is: org.junit.jupiter.api.Assertions.
        //Assertions.assertEquals(3,3); // Dummy assert... put real code here.
    }

    //Test (*) and toString()
    @Test
    public void testTimes() {
        Fraction f1 = new Fraction(1,2 );
        Fraction f2 = new Fraction( 1, 3 );
        Fraction f3 = f1.times( f2 );
        Assertions.assertEquals( "1/6", f3.toString() );
    }

    //Test (+) and (-)
    @Test
    public void testAddSubtract() {
        Fraction f1 = new Fraction(1,2 );
        Fraction f2 = new Fraction( 1, 3 );

        //Add f1+f2, should equal 5/6
        Fraction f3 = f1.plus( f2 );
        Assertions.assertEquals( "5/6", f3.toString() );

        //Subtract f3-f2, should equal 1/2
        Fraction f4 = f3.minus( f2 );
        Assertions.assertEquals( "1/2", f4.toString() );

    }

    //Test Divide
    @Test
    public void testDivide() {
        Fraction f1 = new Fraction(1,2);
        Fraction f2 = new Fraction( 1, 3 );

        //Divide f1/f2, should equal 3/4
        Fraction f3 = f1.dividedBy( f2 );
        Assertions.assertEquals( "3/2", f3.toString() );
        System.out.print(f3.toDouble());

    }


    //Test Negative
    @Test
    public void testNegativeFraction() {
        Fraction f1 = new Fraction(-1,2);
        Fraction f2 = new Fraction( 1, -2 );

        //Divide f1/f2, should equal 3/4
        Assertions.assertEquals( "-1/2", f1.toString() );
        Assertions.assertEquals( "-1/2", f2.toString() );


    }


}

//double toDouble() - Returns a (double precision) floating point number that is the approximate value of this fraction, printed as a real numbe