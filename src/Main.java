import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static HashMap<Character, Long> bracketsMapLongInput;

    public static void main(String[] args) {

        //for input up to 2^31-1 characters or up to Java heap space :)
        String[] examplesToCheck = {"(([34[34]])234)", "([6)", "([([()])])", "(]"};
        for (int i = 0; i < examplesToCheck.length; i++) {
            Boolean isComplementary = checkBracketsComplementary(examplesToCheck[i]);
            System.out.println("checkBracketsComplementary() - wyrazenie " + i + " : " + isComplementary);
        }



        //for input over the length of 2^31-1 characters
        bracketsMapLongInput = new HashMap<Character, Long>();
        bracketsMapLongInput.put('(', Long.valueOf(0));
        bracketsMapLongInput.put(')', Long.valueOf(0));
        bracketsMapLongInput.put('[', Long.valueOf(0));
        bracketsMapLongInput.put(']', Long.valueOf(0));

        //devide and conquer - devide a really big input to pieces
        // todo write special function to devide input from file
        final Integer pieceSize = 5000;
        String[] pieces = new String[2];
        pieces[0] = getStringWithLengthAndFilledWithCharacter(pieceSize, '[');
        pieces[1] = getStringWithLengthAndFilledWithCharacter(pieceSize, ']');
        for (int i = 0; i < pieces.length; i++) {
            checkBracketsComplementaryLongInput(pieces[i]);
        }
        Boolean result = true;
        if (!bracketsMapLongInput.get('(').equals(Long.valueOf(0)) || !bracketsMapLongInput.get('[').equals(Long.valueOf(0))) {
            result = false;
        }
        System.out.println("checkBracketsComplementaryLongInput() - " + result);
    }

    private static String getStringWithLengthAndFilledWithCharacter(int length, char charToFill) {
        if (length > 0) {
            char[] array = new char[length];
            Arrays.fill(array, charToFill);
            return new String(array);
        }
        return "";
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

    /*
        One HashMap for many ivokes
    */
    public static void checkBracketsComplementaryLongInput(String s) {
        if (bracketsMapLongInput == null) {
            return;
        }
        for (int i = 0; i < s.length(); i++) {
            for (Map.Entry<Character, Long> entry : bracketsMapLongInput.entrySet()) {
                if (s.charAt(i) == entry.getKey()) {
                    if (entry.getKey().equals(')')) {
                        Long closeRange = bracketsMapLongInput.get('(') - Long.valueOf(1);
                        bracketsMapLongInput.replace('(', closeRange);
                        continue;
                    }
                    if (entry.getKey().equals(']')) {
                        Long closeRange = bracketsMapLongInput.get('[') - Long.valueOf(1);
                        bracketsMapLongInput.replace('[', closeRange);
                        continue;
                    }
                    bracketsMapLongInput.replace(entry.getKey(), Long.valueOf(entry.getValue() + 1));
                }
            }
        }
    }
}