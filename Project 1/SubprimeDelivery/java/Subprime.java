import java.util.*;

public class Subprime{

    public static void subprime_path(ArrayList<ArrayList<Pair>> capacities, ArrayList<ArrayList<Pair>> loads, int start, int end){

        ArrayList<ArrayList<Pair>> capacityPercentages = new ArrayList<ArrayList<Pair>>();

        for(int i = 0; i<capacities.size();i++){
            capacityPercentages.add(new ArrayList<Pair>());
            for(int j = 0; j<capacities.get(i).size();j++){
                if(capacities.get(i).get(j).second!=loads.get(i).get(j).second){                       //full capacity condition should be never chosen
                    double rate = (loads.get(i).get(j).second)*1.0/(capacities.get(i).get(j).second);  // trucks with their capacity
                    double percentage = rate*100;                                                      // and capacity percentages
                    capacityPercentages.get(i).add(new Pair(capacities.get(i).get(j).first,(int)percentage));
                }
            }
        }
       /*for(int i = 0; i<capacityPercentages.size();i++){               //For testing the capacity percentages
            for(int j=0; j<capacityPercentages.get(i).size();j++){
                System.out.println(i+" node: "+capacityPercentages.get(i).get(j).first + ", weight: "+capacityPercentages.get(i).get(j).second);
            }
        }*/
        int edgeSize = 0;
        for(int i= 0; i<capacities.size();i++){       //find all the edges
            edgeSize+= capacities.get(i).size();
        }
        double[] distance = new double[edgeSize];     //creating distances size of edge
        int pred[] = new int[edgeSize];               //for tracing predecessors

        for(int i= 0; i<edgeSize;i++){                //making the all distances infinity
            distance[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }
        distance[start] = 0;      //setting start node distance equal to 0

        PriorityQueue<Pair> pq = new PriorityQueue<>();   // creating priority queue for storing the nodes(cities)
        pq.add(new Pair(start,0));       //adding start(source) node to the priority queue, first node in pq should be start node

        while(!pq.isEmpty()){           //when our queue is not empty
            Pair current = pq.poll();   //take the node at the head of the queue, and make the current pair is this, start node will be polled initial step

            for(Pair n: capacityPercentages.get(current.first)){           // get the current node's neighbours which has an edge between them (there is an edge, current -- n's )
                if(distance[current.first]+n.second< distance[n.first]){   // if the sum of currentNode distance and the cost of the edge between current and n is less than n's distance
                    distance[n.first] = distance[current.first]+n.second;  // update the n's distance to sum of current distance and edge cost
                    pq.add(new Pair(n.first,distance[n.first]));           // add this pair to the priority queue with new updated value
                    pred[n.first] = current.first;                         // make the node n's predecessor -> current.first
                }
            }
        }
        //printing the total cost
        /*System.out.println("******************************");
        System.out.println("The cost is: "+distance[end]);                      // total distance to the end node*/

        LinkedList<Integer> path = new LinkedList<Integer>();   // for the path, creating a linkedlist and will be pushed like (end -> last-1 -> ... -> start )
        int last = end;
        path.add(last);                                         // last node is added to the linked list
        while (pred[last] != -1) {
            path.add(pred[last]);                               // finding predecessor of the last element and adding it
            last = pred[last];
        }

        for (int i= path.size()-1; i >= 0; i--) {              //for printing the path
            System.out.println(path.get(i));
        }
    }

    public static void main(String[] args){
        Scanner s = new Scanner(System.in);

        int c = s.nextInt();
        ArrayList<ArrayList<Pair>> capacities = new ArrayList<ArrayList<Pair>>();
        ArrayList<ArrayList<Pair>> loads = new ArrayList<ArrayList<Pair>>();
        for (int i = 0; i < c; i++){
            capacities.add(new ArrayList<Pair>());
            String nextline = s.next();
            if (nextline.startsWith(".")){continue;}
            String[] pairs = nextline.split(";",0);
            for (String p : pairs){
                String[] pair = p.split(",",0);
                int destination = Integer.parseInt(pair[0]);
                int weight = Integer.parseInt(pair[1]);
                capacities.get(i).add(new Pair(destination,weight));
            }
        }
        for (int i = 0; i < c; i++){
            loads.add(new ArrayList<Pair>());
            String nextline = s.next();
            if (nextline.startsWith(".")){continue;}
            String[] pairs = nextline.split(";",0);
            for (String p : pairs){
                String[] pair = p.split(",",0);
                int destination = Integer.parseInt(pair[0]);
                int weight = Integer.parseInt(pair[1]);
                loads.get(i).add(new Pair(destination,weight));
            }
        }
        int start = s.nextInt();
        int end = s.nextInt();
        subprime_path(capacities, loads, start, end);
    }

}

class Pair implements Comparable<Pair>{
    public int first;
    public double second;
    public Pair(int a1, double a2) {
        first = a1;
        second = a2;
    }

    public boolean equals(Pair other){
        return (this.first == other.first) && (this.second == other.second);
    }

    /*public int hashCode(){
        return this.first^this.second;
    }*/

    public int compareTo(Pair other){
        if (this.first != other.first){
            return this.first - other.first;
        }
        return (int)this.second - (int)other.second;
    }

    public String toString(){
        return "(" + this.first + "," + this.second + ")";
    }
}