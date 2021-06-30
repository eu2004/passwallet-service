package ro.eu.passwallet.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordGeneratorTest {

    @Test
    public void testGenerateLength() {
        //given
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        passwordGenerator.setLength(6);

        //when
        String pass = passwordGenerator.generate();

        //then
        Assertions.assertEquals(passwordGenerator.getLength(), pass.length());
    }

    @Test
    public void testGenerateSymbol() {
        //given
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        passwordGenerator.setIncludeSymbols(true);

        //when
        String pass = passwordGenerator.generate();

        //then
        Assertions.assertEquals(passwordGenerator.getLength(), pass.length());
        boolean found = false;
        for (char character : PasswordGenerator.SYMBOLS.toCharArray()) {
            found = pass.contains(String.valueOf(character));
            if (found) {
                break;
            }
        }
        Assertions.assertTrue(found);
    }
}
