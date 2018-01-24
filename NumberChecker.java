import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class NumberChecker {
	
    private final int mMaxNumbers = 7;

    public NumberChecker() {}
	
    private void findWinningNumberCombinations(String input, List<String> result, Set<String> parsed, 
			String currentSolution, int addedNumbers, int index) {
        if(addedNumbers > mMaxNumbers) return;
		
        // if the remaining missing numbers are greater than the actual remaining numbers stop execution
        if(mMaxNumbers - addedNumbers > input.length() - index) return;

        // if we are at exactly seven and have used every number - add current solution to the result
        if(addedNumbers == mMaxNumbers && currentSolution.length() == (input.length() + 6)) result.add(currentSolution);
		
        // recursively try the current number as a possible solution as well as the combination
        // of the current and next number. 
        for (int i = 1; i <= 2; i++) {
            if (index + i > input.length()) break;
			
            // see if any trailing numbers are zero since they must be used in connection with the previous number
            boolean hasZeroFollowing = index + i + 1 <= input.length() && 
                    Integer.parseInt(input.substring(index + i , index + i + 1)) == 0;
			
            String subString = input.substring(index, index + i);
			
            // if the current numbers are x0 - jump over the second execution of the if
            // otherwise jump over the zero for the next loop
            if(hasZeroFollowing ) {
                subString += "0";
                if (i == 1) i++;
                else index++;
            }
			
            if(Integer.valueOf(subString) > 59) {
                return;
            } else if (parsed.contains(subString)) {
                continue;
            } else {
                parsed.add(subString);
                StringBuilder sb = new StringBuilder();
                sb.append(currentSolution).append(subString).append(addedNumbers == 6 ? "" : " ");
                findWinningNumberCombinations(input, result, parsed, sb.toString(), addedNumbers + 1, index + i);
                parsed.remove(subString);
            }
			
            if(i == 1 && Integer.valueOf(subString) > 6) break;
        }
    }

    public String findWinningNumbers(String input) {
        if(containsInvalidZeroes(input) || containsInvalidChars(input) || isInvalidLength(input)) return "";

        List<String> foundCombinations = new ArrayList<String>();		
        findWinningNumberCombinations(input, foundCombinations, new LinkedHashSet<String>(), "", 0, 0);

        return foundCombinations.toString();
    }

    private boolean containsInvalidZeroes(String in) {
        if(in.charAt(0) == '0') return true;
        else return Pattern.compile("0(0{1,})").matcher(in).find();
    }
	
    private boolean containsInvalidChars(String in) {
        return Pattern.compile("[^0-9]+").matcher(in).find();
    }
	
    private boolean isInvalidLength(String input) {
        int len = input.length();
        int amountOfZeroes = len - input.replace("0", "").length();
        return len < 7 + amountOfZeroes || len > 14 + amountOfZeroes;
    }
}