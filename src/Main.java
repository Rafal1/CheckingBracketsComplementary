import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
//    private static;

    public static void main(String[] args) {

        //for input up to 2^31-1 characters or up to Java heap space :)
        String[] examplesToCheck = {"(([34[34]])234)", "([6)", "([([()])])", "(]"};
        for (int i = 0; i < examplesToCheck.length; i++) {
            Boolean isComplementary = checkBracketsComplementary(examplesToCheck[i]);
            System.out.println("checkBracketsComplementary() - wyrazenie " + i + " : " + isComplementary);
        }


        //for input over the length of 2^31-1 characters
        //devide and conquer - devide a really big input to pieces

        // todo write special function to get input from file
        final Integer pieceSize = 5000;
        String[] pieces = new String[2];
        pieces[0] = getStringWithLengthAndFilledWithCharacter(pieceSize, '[');
        pieces[1] = getStringWithLengthAndFilledWithCharacter(pieceSize, ']');

        // I didn't want to make a HashMap a static field in that class
        HashMap<Character, Long> bracketsMapLongInput = initHashMap();
        for (int i = 0; i < pieces.length; i++) {
            bracketsMapLongInput = checkBracketsComplementaryLongInput(pieces[i], bracketsMapLongInput);
        }
        System.out.println("checkBracketsComplementaryLongInput() - " + checkBracketsComplementaryLongInputResult(bracketsMapLongInput));


        pieces[1] = getStringWithLengthAndFilledWithCharacter(pieceSize, 'x');
        bracketsMapLongInput = initHashMap();
        for (int i = 0; i < pieces.length; i++) {
            bracketsMapLongInput = checkBracketsComplementaryLongInput(pieces[i], bracketsMapLongInput);
        }
        System.out.println("checkBracketsComplementaryLongInput() - " + checkBracketsComplementaryLongInputResult(bracketsMapLongInput));
    }

    private static String getStringWithLengthAndFilledWithCharacter(int length, char charToFill) {
        if (length > 0) {
            char[] array = new char[length];
            Arrays.fill(array, charToFill);
            return new String(array);
        }
        return "";
    }

    private static HashMap initHashMap() {
        HashMap<Character, Long> bracketsMapLongInput = new HashMap<Character, Long>();
        bracketsMapLongInput.put('(', Long.valueOf(0));
        bracketsMapLongInput.put(')', Long.valueOf(0));
        bracketsMapLongInput.put('[', Long.valueOf(0));
        bracketsMapLongInput.put(']', Long.valueOf(0));

        return bracketsMapLongInput;
    }

    private static Boolean checkBracketsComplementaryLongInputResult(HashMap bracketsMapLongInput) {
        if (bracketsMapLongInput == null) {
            return false;
        }
        if (!bracketsMapLongInput.get('(').equals(Long.valueOf(0)) || !bracketsMapLongInput.get('[').equals(Long.valueOf(0))) {
            bracketsMapLongInput.clear();
            return false;
        }
        bracketsMapLongInput.clear();
        return true;
    }

    /*
        String's length limit is 2^31-1 (Integer.MAX_VALUE), It's enough for a few MILLIONS length of an input data
    */
    public static Boolean checkBracketsComplementary(String s) {
        HashMap<Character, Integer> bracketsMap = new HashMap<Character, Integer>();
        bracketsMap.put('(', 0);
        bracketsMap.put(')', 0);
        bracketsMap.put('[', 0);
        bracketsMap.put(']', 0);

        for (int i = 0; i < s.length(); i++) {
            for (Map.Entry<Character, Integer> entry : bracketsMap.entrySet()) {
                if (s.charAt(i) == entry.getKey()) {
                    if (entry.getKey().equals(')')) {
                        Integer closeRange = bracketsMap.get('(') - 1;
                        if (closeRange < 0) {
                            return false;
                        }
                        bracketsMap.replace('(', closeRange);
                        continue;
                    }
                    if (entry.getKey().equals(']')) {
                        Integer closeRange = bracketsMap.get('[') - 1;
                        if (closeRange < 0) {
                            return false;
                        }
                        bracketsMap.replace('[', closeRange);
                        continue;
                    }
                    bracketsMap.replace(entry.getKey(), entry.getValue() + 1);
                }
            }
        }
        if (!bracketsMap.get('(').equals(0) || !bracketsMap.get('[').equals(0)) {
            return false;
        }
        return true;
    }

    public static HashMap checkBracketsComplementaryLongInput(String s, HashMap<Character, Long> bracketsMapLongInput) {
        if (bracketsMapLongInput == null) {
            return null;
        }
        for (int i = 0; i < s.length(); i++) {
            for (Map.Entry<Character, Long> entry : bracketsMapLongInput.entrySet()) {
                if (s.charAt(i) == entry.getKey()) {
                    if (entry.getKey().equals(')')) {
                        Long closeRange = bracketsMapLongInput.get('(') - Long.valueOf(1);
                        if (closeRange < 0) {
                            return null;
                        }
                        bracketsMapLongInput.replace('(', closeRange);
                        continue;
                    }
                    if (entry.getKey().equals(']')) {
                        Long closeRange = bracketsMapLongInput.get('[') - Long.valueOf(1);
                        if (closeRange < 0) {
                            return null;
                        }
                        bracketsMapLongInput.replace('[', closeRange);
                        continue;
                    }
                    bracketsMapLongInput.replace(entry.getKey(), Long.valueOf(entry.getValue() + 1));
                }
            }
        }
        return bracketsMapLongInput;
    }
}