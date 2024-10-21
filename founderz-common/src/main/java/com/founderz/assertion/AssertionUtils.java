package com.founderz.assertion;

import com.founderz.exception.BadRequestException;

import java.util.regex.Pattern;

public class AssertionUtils {
    public static void assertRegularExpression(final String aString, final String regex, final String aMessage) {
        if (!Pattern.matches(regex, aString)) {
            throw new BadRequestException(aMessage);
        }
    }
}