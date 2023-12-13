import java.util.Scanner;

class Boxx {
    double length;
    double width;
    double height;

    public Boxx(double length, double width, double height){
        this.length=length;
        this.width=width;
        this.height=height;
    }
    public Boxx(){
        this.length=length;
        this.width=width;
        this.height=height;
    }
    boolean compatibleWith(Boxx other){
        if(other.length<this.length && other.width< this.width && other.height < this.height){
            return true;
        }
        return false;
    }
}

public class Box {
    public static int maxDepth(Boxx[] boxes){
        int n = boxes.length;
        selectionSort(boxes);                          // sort boxes by lengths initially
        int [][]p = new int[n+1][n+1];                 // creating an 2-D array match the compatible boxes with the current box
        p[0][0] = 0;
        //compute the p[i] values
        for(int i = n; i > 0;i--){
            for (int j = 1; j < n+1 ; j++){
                if(boxes[i-1].compatibleWith(boxes[j-1])){  // if it is compatible, then assign the box
                    p[i][j] = j;
                }
            }
        }
        int M[] = new int[n+1];                         //creating an array for finding the maximum compatibility of the boxes
        M[0] = 0;

        for(int j = 1 ; j < n+1 ; j++){
            int max = 0;
            for(int i = 0; i< n+1; i++){
                if(M[p[j][i]] > max){
                    max= M[p[j][i]];
                }
            }
            //M[j] = Math.max(M[j-1], max+1);
            M[j] =  max+1;
        }

        int max = 0;
        for(int i = 0; i< n+1 ; i++){                   //it finds the max value in the array M[]
            if(M[i]>max){
                max = M[i];
            }
        }
        //System.out.println("max dept is = "+max);
        return max;
    }
    public static Boxx[] selectionSort(Boxx[] array){
        int n = array.length;
        for (int i = 0; i < n-1; i++)
        {
            int min_idx = i;
            for (int j = i+1; j < n; j++)
                if (array[j].length < array[min_idx].length)
                    min_idx = j;

            Boxx temp = array[min_idx];
            array[min_idx] = array[i];
            array[i] = temp;
        }
        return array;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        int n = s.nextInt();
        //System.out.println(n);
        double[][] boxes = new double[n][3];
        Boxx[] boxes2 = new Boxx[n];
        for(int i = 0; i < n ; i++){
            for (int j = 0; j < 3; j++){
                double value = Double.parseDouble(s.next());
                boxes[i][j] = value;
                // System.out.println("i = " + i + " j = " +j +" "+ boxes[i][j]);
            }
            Boxx box = new Boxx(boxes[i][0], boxes[i][1], boxes[i][2]);
            // System.out.println(boxes[i][0] + " "+boxes[i][1] + " "+boxes[i][2] + " ");
            boxes2[i] = box;
            //System.out.println("Box :" + i + "length : "+ boxes2[i].length + "width: "+ boxes2[i].width + "height: " +boxes2[i].height);
            //   boxes2[]
        }
        System.out.println("Max depth is " + maxDepth(boxes2));
       /* for(int i = 0; i < n ; i++){
            System.out.println("Box :" + i + "length : "+ boxes2[i].length + "width: "+ boxes2[i].width + "height: " +boxes2.length);
        }*/

    }
}