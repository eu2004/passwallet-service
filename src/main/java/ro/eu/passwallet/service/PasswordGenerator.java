package ro.eu.passwallet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PasswordGenerator {
    private int length = 16;
    private boolean includeSymbols = false;
    private boolean includeNumbers = true;
    private boolean includeLowerCase = true;
    private boolean includeUpperCase = true;

    static final String SYMBOLS = "!@#$%^&*()_";
    static final String NUMBERS = "0123456789";
    static final String LOWERCASE = "abcdefghijklmnopqrstuvxyz";
    static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVXYZ";

    private static final Random randomSymbol = new Random();
    private static final Random randomUpperCase = new Random();
    private static final Random randomLowerCase = new Random();
    private static final Random randomNumber = new Random();
    private static final Random shuffledRandom = new Random();

    enum Operations {
        INCLUDE_SYMBOLS,
        INCLUDE_NUMBERS,
        INCLUDE_LOWER_CASE,
        INCLUDE_UPPER_CASE
    }

    public String generate() {
        StringBuilder generatedPassword = new StringBuilder();
        int ratioOfGroupOfCharsLength = getRatioOfGroupOfCharsLength();
        List<Operations> appliedOperations = getAppliedOperations();
        List<Operations> shuffledAppliedOperations = getShuffledOperations(appliedOperations);

        while (generatedPassword.length() < length) {
            for (Operations shuffledAppliedOperation : shuffledAppliedOperations) {
                switch (shuffledAppliedOperation) {
                    case INCLUDE_NUMBERS: {
                        for (int i = 0; i < ratioOfGroupOfCharsLength; i++) {
                            generatedPassword.append(getRandomNumber());
                        }
                        break;
                    }
                    case INCLUDE_LOWER_CASE: {
                        for (int i = 0; i < ratioOfGroupOfCharsLength; i++) {
                            generatedPassword.append(getRandomLowerCase());
                        }
                        break;
                    }
                    case INCLUDE_SYMBOLS: {
                        for (int i = 0; i < ratioOfGroupOfCharsLength; i++) {
                            generatedPassword.append(getRandomSymbol());
                        }
                        break;
                    }
                    case INCLUDE_UPPER_CASE: {
                        for (int i = 0; i < ratioOfGroupOfCharsLength; i++) {
                            generatedPassword.append(getRandomUpperCase());
                        }
                        break;
                    }
                }
            }
        }

        if (generatedPassword.length() > length) {
            generatedPassword.delete(length, generatedPassword.length());
        }
        return generatedPassword.toString();
    }

    private List<Operations> getShuffledOperations(List<Operations> appliedOperations) {
        List<Operations> shuffledOperations = new ArrayList<>();
        int[] shuffledIndexes = new int[appliedOperations.size()];
        int index = 0;
        while (index < shuffledIndexes.length) {
            int lastShuffledIndex = shuffledRandom.nextInt(shuffledIndexes.length);
            if (index != 0) {
                boolean lastShuffledIndexAlreadyGenerated = true;
                while (lastShuffledIndexAlreadyGenerated) {
                    lastShuffledIndexAlreadyGenerated = false;
                    for (int i = 0; i < index; i++) {
                        if (lastShuffledIndex == shuffledIndexes[i]) {
                            lastShuffledIndexAlreadyGenerated = true;
                            lastShuffledIndex = shuffledRandom.nextInt(shuffledIndexes.length);
                            break;
                        }
                    }
                }
            }
            shuffledIndexes[index] = lastShuffledIndex;
            index++;
        }

        for (int shuffledIndex : shuffledIndexes) {
            shuffledOperations.add(appliedOperations.get(shuffledIndex));
        }
        return shuffledOperations;
    }

    private List<Operations> getAppliedOperations() {
        List<Operations> appliedOperations = new ArrayList<>();
        if (includeNumbers) {
            appliedOperations.add(Operations.INCLUDE_NUMBERS);
        }
        if (includeLowerCase) {
            appliedOperations.add(Operations.INCLUDE_LOWER_CASE);
        }
        if (includeUpperCase) {
            appliedOperations.add(Operations.INCLUDE_UPPER_CASE);
        }
        if (includeSymbols) {
            appliedOperations.add(Operations.INCLUDE_SYMBOLS);
        }
        return appliedOperations;
    }

    private int getRatioOfGroupOfCharsLength() {
        int groupOfCharactersCount = 0;
        if (includeNumbers) {
            groupOfCharactersCount++;
        }
        if (includeSymbols) {
            groupOfCharactersCount++;
        }
        if (includeLowerCase) {
            groupOfCharactersCount++;
        }
        if (includeUpperCase) {
            groupOfCharactersCount++;
        }

        return length / groupOfCharactersCount;
    }

    private char getRandomSymbol() {
        return SYMBOLS.charAt(randomSymbol.nextInt(SYMBOLS.length()));
    }

    private char getRandomUpperCase() {
        return UPPERCASE.charAt(randomUpperCase.nextInt(UPPERCASE.length()));
    }

    private char getRandomLowerCase() {
        return LOWERCASE.charAt(randomLowerCase.nextInt(LOWERCASE.length()));
    }

    private int getRandomNumber() {
        final int randomIndex = randomNumber.nextInt(NUMBERS.length());
        return Integer.parseInt(String.valueOf(NUMBERS.charAt(randomIndex)));
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isIncludeSymbols() {
        return includeSymbols;
    }

    public void setIncludeSymbols(boolean includeSymbols) {
        this.includeSymbols = includeSymbols;
    }

    public boolean isIncludeNumbers() {
        return includeNumbers;
    }

    public void setIncludeNumbers(boolean includeNumbers) {
        this.includeNumbers = includeNumbers;
    }

    public boolean isIncludeLowerCase() {
        return includeLowerCase;
    }

    public void setIncludeLowerCase(boolean includeLowerCase) {
        this.includeLowerCase = includeLowerCase;
    }

    public boolean isIncludeUpperCase() {
        return includeUpperCase;
    }

    public void setIncludeUpperCase(boolean includeUpperCase) {
        this.includeUpperCase = includeUpperCase;
    }
}
