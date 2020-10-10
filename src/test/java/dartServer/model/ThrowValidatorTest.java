package dartServer.model;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ThrowValidatorTest {

    @Test
    void isValidThrow1() {
        assertFalse(ThrowValidator.isValidThrow(null, 501));

        assertFalse(ThrowValidator.isValidThrow(new Throw(-1,0,3,0), 501));
        assertFalse(ThrowValidator.isValidThrow(new Throw(181,0,3,0), 501));
        assertFalse(ThrowValidator.isValidThrow(new Throw(0,0,2,0), 501));
        assertFalse(ThrowValidator.isValidThrow(new Throw(0,0,1,0), 501));
        assertFalse(ThrowValidator.isValidThrow(new Throw(180,0,3,0), 100));
        assertFalse(ThrowValidator.isValidThrow(new Throw(180,0,3,0), 100));
    }

    @TestFactory
    Stream<DynamicTest> isValidThrow2() {
        return List.of(163, 166, 169, 172, 173, 175, 176, 178, 179).stream()
                .map(points -> DynamicTest.dynamicTest("Test that a score of " + points + " is not valid", () -> {
                    assertFalse(ThrowValidator.isValidThrow(new Throw(points,0,3,0), 501));
                }));
    }

    @Test
    void isValidThrow3() {
        assertFalse(ThrowValidator.isValidThrow(new Throw(180,0, 0,0), 501));
        assertFalse(ThrowValidator.isValidThrow(new Throw(180,0, 4,0), 501));

        assertFalse(ThrowValidator.isValidThrow(new Throw(0,-1, 3,0), 40));
        assertFalse(ThrowValidator.isValidThrow(new Throw(0,4, 3,0), 40));
        assertFalse(ThrowValidator.isValidThrow(new Throw(0,3, 1,0), 40));
        assertFalse(ThrowValidator.isValidThrow(new Throw(100,1, 3,0), 190));
        assertFalse(ThrowValidator.isValidThrow(new Throw(170,2, 3,0), 170));
        assertFalse(ThrowValidator.isValidThrow(new Throw(170,3, 3,0), 170));
        assertFalse(ThrowValidator.isValidThrow(new Throw(110,3, 3,0), 110));
        assertFalse(ThrowValidator.isValidThrow(new Throw(2,2, 3,0), 2));
    }

    @Test
    void isValidThrowCustomCases() {

        // VALID Throws

        // standard throw
        assertTrue(ThrowValidator.isValidThrow(new Throw(180,0,3,0),501));
        assertTrue(ThrowValidator.isValidThrow(new Throw(90,0,3,0),321));
        assertTrue(ThrowValidator.isValidThrow(new Throw(90,0,3,0),231));

        // checkouts
        // 3-dart-finish
        assertTrue(ThrowValidator.isValidThrow(new Throw(0,3,3,0), 2));
        assertTrue(ThrowValidator.isValidThrow(new Throw(141,1,3,0),141));
        assertTrue(ThrowValidator.isValidThrow(new Throw(100,1,3,0),100));
        assertTrue(ThrowValidator.isValidThrow(new Throw(100,2,3,0),100));
        assertTrue(ThrowValidator.isValidThrow(new Throw(50,3,3,0),50));
        assertTrue(ThrowValidator.isValidThrow(new Throw(40,2,3,0),40));
        assertTrue(ThrowValidator.isValidThrow(new Throw(50,1,3,0),50));
        assertTrue(ThrowValidator.isValidThrow(new Throw(2,3,3,0),2));

        // 2-dart-finish
        assertTrue(ThrowValidator.isValidThrow(new Throw(100,1,2,0),100));
        assertTrue(ThrowValidator.isValidThrow(new Throw(100,2,2,0),100));
        assertTrue(ThrowValidator.isValidThrow(new Throw(50,2,2,0),50));
        assertTrue(ThrowValidator.isValidThrow(new Throw(40,1,2,0),40));
        assertTrue(ThrowValidator.isValidThrow(new Throw(50,1,2,0),50));
        assertTrue(ThrowValidator.isValidThrow(new Throw(3,1,2,0),3));
        assertTrue(ThrowValidator.isValidThrow(new Throw(2,2,2,0),2));

        // 1-dart-finish
        assertTrue(ThrowValidator.isValidThrow(new Throw(50,1,1,0),50));
        assertTrue(ThrowValidator.isValidThrow(new Throw(40,1,1,0),40));
        assertTrue(ThrowValidator.isValidThrow(new Throw(40,1,1,0),40));
        assertTrue(ThrowValidator.isValidThrow(new Throw(2,1,1,0),2));


        // INVALID THROWS
        assertFalse(ThrowValidator.isValidThrow(new Throw(180,3,0,0), 501));
        assertFalse(ThrowValidator.isValidThrow(new Throw(0,2,2,0), 2));
        assertFalse(ThrowValidator.isValidThrow(new Throw(0,2,2,0), 100));
        assertFalse(ThrowValidator.isValidThrow(new Throw(0,1,1,0), 2));
        assertFalse(ThrowValidator.isValidThrow(new Throw(0,1,1,0), 100));
        assertFalse(ThrowValidator.isValidThrow(new Throw(2,1,2,0), 2));
        assertFalse(ThrowValidator.isValidThrow(new Throw(180,3,0,0), 178));
        assertFalse(ThrowValidator.isValidThrow(new Throw(163,0,3,0), 501));
        assertFalse(ThrowValidator.isValidThrow(new Throw(179,0,3,0), 501));
        assertFalse(ThrowValidator.isValidThrow(new Throw(180,3,3,0), 501));
        assertFalse(ThrowValidator.isValidThrow(new Throw(60,3,3,0), 501));
        assertFalse(ThrowValidator.isValidThrow(new Throw(170,3,3,0), 501));
        assertFalse(ThrowValidator.isValidThrow(new Throw(170,2,3,0), 501));
        assertFalse(ThrowValidator.isValidThrow(new Throw(180,1,3,0), 501));
        assertFalse(ThrowValidator.isValidThrow(new Throw(180,-10,3,0), 501));
        assertFalse(ThrowValidator.isValidThrow(new Throw(180,10,3,0), 501));
        assertFalse(ThrowValidator.isValidThrow(new Throw(180,1,-10,0), 501));
        assertFalse(ThrowValidator.isValidThrow(new Throw(180,1,10,0), 501));
        assertFalse(ThrowValidator.isValidThrow(null, 501));
    }

    @TestFactory
    Stream<DynamicTest> isThreeDartFinish() {
        return IntStream.rangeClosed(2,170).filter(num -> !(List.of(159, 162, 163, 165, 166, 168, 169).contains(num))).boxed()
                .map(points -> DynamicTest.dynamicTest("Test that " + points + " is 3-dart finish", () -> {
                    assertTrue(ThrowValidator.isThreeDartFinish(points));
                }));
    }

    @TestFactory
    Stream<DynamicTest>  isTwoDartFinish() {
        return IntStream.rangeClosed(2,110).filter(num -> !(List.of(99, 102, 103, 105, 106, 108, 109).contains(num))).boxed()
                .map(points -> DynamicTest.dynamicTest("Test that " + points + " is 2-dart finish", () -> {
                    assertTrue(ThrowValidator.isThreeDartFinish(points));
                }));
    }

    @TestFactory
    Stream<DynamicTest> isOneDartFinish() {
        return IntStream.concat(IntStream.rangeClosed(2,40).filter(num -> num%2 == 0), IntStream.of(50)).boxed()
                .map(points -> DynamicTest.dynamicTest("Test that " + points + " is 1-dart finish", () -> {
                    assertTrue(ThrowValidator.isOneDartFinish(points));
                }));
    }

}