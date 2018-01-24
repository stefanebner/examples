import java.util.*;

public class UncleMortysLotteryNumberPicker {

    public static void main (String [] args) {	
//        if(args.length == 0) {
//            printUsage();
//        } else if(args.length == 1) {
//            if(args[0].equals("random")) startRandomNewspaperReader();
//            parseLine(args[0]);
//        }
        
          List<Integer> grid = new ArrayList<>();
          grid.add(6);
          grid.add(5);
          grid.add(5);
          grid.add(5);
          
          System.out.println(maximum_completed_tasks(4, 5, grid));
    }
    
    public static int cover_the_border(int length, List< List<Integer> > radars) {
        // Example arguments:
        // l = 100
        // radars = [ [5, 10], [3, 25], [46, 99], [39, 40], [45, 50] ]
        // The `radars` list will çonsist of lists with two elements
        Collections.sort(radars, new Comparator<List<Integer>>() {
            @Override
            public int compare(List<Integer> o1, List<Integer> o2) {
                return o1.get(0) - o2.get(0);
            }
        });
        int l = 0, r = 0, sum = 0;
        for(List<Integer> list : radars) {
            int a = list.get(0);
            int b = list.get(1);
            if(a >= length) {
                break;
            } else if(b >= length) {
                r = length; 
                break;
            } else {
                if(a <= r) {
                    r = b;
                } else {
                    sum += r - l;
                    l = a;
                    r = b > length ? length : b;
                }
            }
        }
        return sum += r - l;
    }
    
    public static int maximum_completed_tasks(int n, int t, List<Integer> task_difficulties) {
        Integer[] tasks = new Integer[task_difficulties.size()];
        tasks = task_difficulties.toArray(tasks);
        Arrays.sort(tasks);
        int sum = tasks[0];
        if(sum > t) return 0; 
        
        for(int i = 1; i < tasks.length; i++) {
            sum += 2*tasks[i] - tasks[i-1];
            if(sum > t) return i;
        }
        return 1;
    }
    
    private static int[][] con;
    private static int n;
    private static int m;
    
    public static int count_the_paths(List<String> grid) {
        // Write your solution here
        n = grid.get(0).length();
        m = grid.size();
        con = new int[n][m];
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                con[j][i] = -Character.getNumericValue(grid.get(m-i-1).charAt(j));
            }
        }
        con[n-1][m-1] = 1;
        return traverse(0,0);
    }
    
    private static int traverse(int x, int y) {
        if(x+1 < n) {
            if(con[x+1][y] == 0) {
                con[x][y] += traverse(x+1, y);
            } else if(con[x+1][y] > 0) {
                con[x][y] += con[x+1][y];
            }
        }
        
        if(y+1 < m) {
            if(con[x][y+1] == 0) {
                con[x][y] += traverse(x, y+1);
            } else if(con[x][y+1] > 0) {
                con[x][y] += con[x][y+1];
            }
        }
        
        return con[x][y];
    }
    
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> triplets = new ArrayList<>();
        Set<TreeSet<Integer>> checked = new HashSet<TreeSet<Integer>>();
        
        for(int i = 0; i < nums.length-2; i++) {
            for(int j = 1; j < nums.length-1; j++) {                
                TreeSet<Integer> firstTwo = new TreeSet<Integer>();
                firstTwo.add(nums[i]);
                firstTwo.add(nums[j]);
                
                if(checked.contains(firstTwo)) continue;
                
                int lookingFor = -(nums[i] + nums[j]);
                
                for(int k = 2; k < nums.length; k++) {
                    if(nums[k] != lookingFor) continue;
                    checked.add(firstTwo);
                    List<Integer> finalThree = new ArrayList<Integer>();
                    finalThree.add(nums[i]);
                    finalThree.add(nums[j]);
                    finalThree.add(nums[k]);
                    triplets.add(new ArrayList<Integer>(finalThree));
                    break;
                }
            }
        }
        return triplets;
    }
    
    
    public static List<Integer> longest_increasing_subsequence(List<Integer> sequence) {
        if(sequence.size() <= 1) return sequence;
        
        List<Integer> temp = new ArrayList<>();
        List<Integer> longest = new ArrayList<>();
        longest.add(sequence.get(0));
        for(int i = 1; i < sequence.size(); i++) {
            int current = sequence.get(i);
            int last = longest.get(longest.size()-1);
            if(temp.isEmpty()) {
                if(last < current) longest.add(current);
                else if(last > current) temp.add(current);
            } else {
                int tempLast = temp.get(temp.size()-1);
                if(last < current) {
                    longest.add(current);
                    temp.clear();
                } else {
                    if(tempLast < current) temp.add(current);
                    else if(tempLast > current) {
                        temp.clear();
                        temp.add(current);
                    }
                }
            }
            if(!temp.isEmpty() && temp.size() > longest.size()) 
                longest = new ArrayList<>(temp);
        }
        return longest;
    }
    
    
    public static class ListNode {
             int val;
             ListNode next;
             ListNode(int x) { val = x; }
    }
    
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        if(head == null || (head.next == null && n >= 1)) return null;
        
        ListNode slow = head, fast = head, prev = head;
        int count = 0, length = 0;
        while(fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
            count ++;
        }
        
        if(fast == null) length = count * 2;
        else length = count * 2 + 1;
        
        if(length - n == 0) {
            return head.next;
        }
        
        int stepsToGo = 0;
        if(length - n > count) {
            stepsToGo = length - count - n;
        } else if(length - n < count) {
            slow = head;
            stepsToGo = length - n;
        }
        
        while(stepsToGo > 0) {
            prev = slow;
            slow = slow.next;
            stepsToGo --;
        }
        
        prev.next = slow.next;
        return head;
    }
	
    private static void printUsage() {
        StringBuilder sb = new StringBuilder();
        sb.append("Uncle Morty's Lucky Lottery Number Picker\n");
        sb.append("----------------------------------------------------------------------\n\n");
        sb.append("To simulate a newspaper just use the parameter 'random'\n");
        sb.append("Otherwise just add string in the following format to the command line:\n");
        sb.append("[\"NUMBER\", \"NUMBER\",\"NUMBER\",\"NUMBER\", \"NUMBER\", ...]");
        System.out.print(sb.toString());
    }
	
    private static void startRandomNewspaperReader() {
        NumberChecker checker = new NumberChecker();
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for(int i = 0; i < 1000000; i++) {
            for(int j = 0; j < getWeightedRandom(); j++) sb.append(rand.nextInt(10));
            if (!checkAndPrintOutput(sb.toString(), checker.findWinningNumbers(sb.toString()))) {
                sb.append(" is no winner");
                System.out.println(sb.toString());
            }
            sb.setLength(0);
        }
    }

    private static int getWeightedRandom() {
        double r = new Random().nextDouble();
        r = Math.pow(r, 0.5);
        return (int) (5 + 14 * r);
    }

    private static String parseLine(String input) {
        input = input.replaceAll("\\[|\\]|\\s|", "");
        List<String> parsedList = Arrays.asList(input.split(","));
        NumberChecker checker = new NumberChecker();
        StringBuilder sb = new StringBuilder();
        for (String num : parsedList) {
            checkAndPrintOutput(num, checker.findWinningNumbers(num));
            sb.setLength(0);
        }
        return "";
    }

    private static boolean checkAndPrintOutput(String input, String result) {
        // Java collections surounds the toString() output with[] - if we have a winning number
        if(result.contains("[")) result = result.substring(1, result.length()-1).trim();
        if(result.length() == 0) return false;
        StringBuilder sb = new StringBuilder();
        List<String> output = Arrays.asList(result.replaceAll(",\\s", ",").split(","));
        Iterator<String> it = output.iterator();
        while(it.hasNext()) {
            String next = it.next();
            //doBasicValidation(input, next);
            sb.append(input).append(" -> ").append(next);
            if(it.hasNext()) sb.append("\n");
        }
        System.out.println(sb.toString());
        return true;
    }

    private static void doBasicValidation(String input, String output) {
        if(input.charAt(0) != output.charAt(0) || input.charAt(input.length()-1) != output.charAt(output.length()-1)) {
            System.out.println("ERROR start and end of input and output are different");
            System.exit(0);
        }
        
        if(input.length() != output.replaceAll(" ", "").length()) {
            System.out.println("ERROR mismatched length");
            System.exit(0);
        }
        
        List<String> vals = Arrays.asList(output.split(" "));
        if(vals.size() != 7) {
            System.out.println("ERROR output has the wrong amount of numbers");
            System.exit(0);
        }
        
        for (String string : vals) {
            if(string.contains("00") || string.startsWith("0")) {
                System.out.println("ERROR starting with or too many zeroes");
                System.exit(0);
            }
            if(Integer.parseInt(string) > 59) {
                System.out.println("ERROR number in output too big");
                System.exit(0);
            }
        }
    }
}