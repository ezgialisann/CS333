import java.util.Scanner;

public class Arithmetic {

    public static int maxOutput(int[][] maxNumbers, int[][] minNumbers, char[] operators, int length){
        for (int x = 0; x < length; x++) {
            for (int i = 0; i < length-x-1; i++) {
                int minTmp = Integer.MAX_VALUE;
                int maxTmp = Integer.MIN_VALUE;
                for (int j = i; j < i+x+1; j++) {

                    //in the first step it computes all the diagonals with their operations between them,
                    //other steps it checks for all the combinations (max-max), (min, min), (max, min),(min, max) of the operations and
                    //assigns the minimum to -> minvaluearray | assigns the maximum to -> maxvaluearray

                    int comb[] = new int[4];
                    if (operators[j] == '+') {
                        comb[0] = minNumbers[i][j] + minNumbers[j + 1][i+1+x];
                        comb[1] = maxNumbers[i][j] + maxNumbers[j + 1][i+1+x];
                        comb[2] = minNumbers[i][j] + maxNumbers[j + 1][i+1+x];
                        comb[3] = maxNumbers[i][j] + minNumbers[j + 1][i+1+x];
                    }
                    else if (operators[j] == '-') {
                        comb[0] = minNumbers[i][j] - minNumbers[j + 1][i+1+x];
                        comb[1] = maxNumbers[i][j] - maxNumbers[j + 1][i+1+x];
                        comb[2] = minNumbers[i][j] - maxNumbers[j + 1][i+1+x];
                        comb[3] = maxNumbers[i][j] - minNumbers[j + 1][i+1+x];
                    }
                    else if (operators[j] == '*') {
                        comb[0] = minNumbers[i][j] * minNumbers[j + 1][i+1+x];
                        comb[1] = maxNumbers[i][j] * maxNumbers[j + 1][i+1+x];
                        comb[2] = minNumbers[i][j] * maxNumbers[j + 1][i+1+x];
                        comb[3] = maxNumbers[i][j] * minNumbers[j + 1][i+1+x];
                    }

                    for(int var = 0; var<4; var++){             //finding the minimum value from all the 4 solutions
                        if(minTmp>comb[var]){
                            minTmp = comb[var];
                        }
                    }
                    for(int var = 0; var<4; var++){             //finding the maximum value from all the 4 solutions
                        if(maxTmp<comb[var]){
                            maxTmp= comb[var];
                        }
                    }
                    //in the first step, it writes the minimum number to the diagonals for ex: computes the M[0][0]+M[1][1] -> writes the output to the M[0][1]
                    minNumbers[i][i+x+1] = minTmp;                  //update the min valued table,
                    maxNumbers[i][i+x+1] = maxTmp;                   //update the min valued table
                }
            }
        }
        //test

        /*System.out.println("Max values matrix");
        for(int i = 0; i< maxNumbers.length;i++){
            for(int j = 0; j< maxNumbers.length ; j++){
                System.out.print(maxNumbers[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println("Min values matrix");
        for(int i = 0; i< maxNumbers.length;i++){
            for(int j = 0; j< minNumbers.length ; j++){
                System.out.print(minNumbers[i][j]+" ");
            }
            System.out.println();
        }*/

        return maxNumbers[0][length-1];
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String line = s.next();
        int length = line.length();
        char []operators = new char[length/2];
        int[][] maxNumbers = new int[(length/2) +1][(length/2) + 1];
        int[][] minNumbers = new int[(length/2) +1][(length/2) + 1];
        int[] numbers = new int[(length/2) +1];

        int x = 0;
        for(int i = 0 ; i< line.length();i+=2){    //adding the numbers
            char number = line.charAt(i);
            int num = number -'0';
            numbers[x] = num;
            x++;
        }
        /*for(int i = 0; i<numbers.length;i++){
            System.out.println(numbers[i]);
        }*/
        for(int i = 0; i< numbers.length; i++){
            for(int j = 0; j< numbers.length;j++){
                maxNumbers[i][j] = 0;                           //making the max valued matrix all zero values, when others come, it will take their values
                minNumbers[i][j] = Integer.MAX_VALUE;           //making the min valued matrix all max values, when others come, it will take their values
                if(i==j){                                       //diagonals are the numbers in the expression for the bottom up the other values
                    maxNumbers[i][j] = numbers[i];
                    minNumbers[i][j] = numbers[i];
                }
            }
        }
        x = 0;
        for(int i = 1 ; i< line.length()-1 ;i+=2){             //adding all operators to an array
            char c = line.charAt(i);
            operators[x] = c;
            x++;
        }
        //testing
        /*for(int i = 0; i< maxNumbers.length;i++){
            for(int j = 0; j< maxNumbers.length ; j++){
                System.out.print(maxNumbers[i][j]+" ");
            }
            System.out.println();
        }*/
       /* for(int i = 0; i< operators.length; i++){
            System.out.print(operators[i]+" ");
        }
        System.out.println();*/

        /*for(int i = 0; i< maxNumbers.length;i++){
            for(int j = 0; j< maxNumbers.length ; j++){
                System.out.print(minNumbers[i][j]+" ");
            }
            System.out.println();
        }*/
        int result = maxOutput(maxNumbers, minNumbers, operators, numbers.length);
        System.out.println(result);
    }
}