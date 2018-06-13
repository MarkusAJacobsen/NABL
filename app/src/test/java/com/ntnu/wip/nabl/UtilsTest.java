package com.ntnu.wip.nabl;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UtilsTest {

    @Test
    public void getHumanReadableDate() {
        Date date = new Date();
        date.setTime(0);

        final String expected = "01.01.1970";
        final String returnedDate = Utils.getHumanReadableDate(date);

        Assert.assertEquals(expected, returnedDate);
    }

    @Test
    public void decodeDateString() {
        final String input = "01.01.1970";
        List<Integer> expected = new ArrayList<>();

        expected.add(1);
        expected.add(1);
        expected.add(1970);

        final List<Integer> actual = Utils.decodeDateString(input);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void generateUniqueId() {
        final int expectedLength = 24;

        final String actualId = Utils.generateUniqueId(24);

        Assert.assertEquals(expectedLength, actualId.length());
    }

    @Test
    public void addMissingZeroSuccessful() {
        final String expected = "06";

        final int testInteger = 6;

        final String actual = Utils.addMissingZero(testInteger);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addMissingZeroToNothingOtherThanConversion() {
        final String expected = "111";

        final int testInteger = 111;

        final String actual = Utils.addMissingZero(testInteger);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addMissingZeroNegativeNumbersDoNothingOtherThanConversion() {
        final String expected = "-6";

        final int testInteger = -6;

        final String actual = Utils.addMissingZero(testInteger);

        Assert.assertEquals(expected, actual);
    }
}