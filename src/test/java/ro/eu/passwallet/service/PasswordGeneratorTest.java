package ro.eu.passwallet.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordGeneratorTest {

    @Test
    public void testDefaultGenerate() {
        //given
        PasswordGenerator passwordGenerator = new PasswordGenerator();

        //when
        String pass = passwordGenerator.generate();

        //then
        Assertions.assertEquals(passwordGenerator.getLength(), pass.length());
        Assertions.assertTrue(charactersFound(pass, PasswordGenerator.LOWERCASE.toCharArray()));
        Assertions.assertTrue(charactersFound(pass, PasswordGenerator.UPPERCASE.toCharArray()));
        Assertions.assertTrue(charactersFound(pass, PasswordGenerator.NUMBERS.toCharArray()));
        Assertions.assertFalse(charactersFound(pass, PasswordGenerator.SYMBOLS.toCharArray()));
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
        Assertions.assertTrue(charactersFound(pass, PasswordGenerator.LOWERCASE.toCharArray()));
        Assertions.assertTrue(charactersFound(pass, PasswordGenerator.UPPERCASE.toCharArray()));
        Assertions.assertTrue(charactersFound(pass, PasswordGenerator.NUMBERS.toCharArray()));
        Assertions.assertTrue(charactersFound(pass, PasswordGenerator.SYMBOLS.toCharArray()));
    }

    @Test
    public void testGenerateLength() {
        //given
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        passwordGenerator.setLength(6);

        //when
        String pass = passwordGenerator.generate();

        //then
        Assertions.assertEquals(passwordGenerator.getLength(), pass.length());
        Assertions.assertTrue(charactersFound(pass, PasswordGenerator.LOWERCASE.toCharArray()));
        Assertions.assertTrue(charactersFound(pass, PasswordGenerator.UPPERCASE.toCharArray()));
        Assertions.assertTrue(charactersFound(pass, PasswordGenerator.NUMBERS.toCharArray()));
    }

    private boolean charactersFound(final String pass, final char[] chars) {
        boolean found = false;
        for (char character : chars) {
            found = pass.contains(String.valueOf(character));
            if (found) {
                break;
            }
        }
        return found;
    }
}
