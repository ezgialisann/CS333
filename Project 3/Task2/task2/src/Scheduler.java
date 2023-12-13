import java.util.*;

public class Scheduler {

    //running the foldfulkerson algorithm for the finding max flow
    public static int fordFulkersonAlgorithm(int graph[][], int source, int terminal) {
        int previous[] = new int[graph.length];                            // for the residual graph to find the previous nodes
        int maxFlow = 0;

        int residualGraph[][] = new int[graph.length][graph[0].length];   // creating residual graph for backward operations

        for (int i = 0; i < graph.length; i++)                            // constructing the residual graph initially equals to normal graph
            for (int j = 0; j < graph[0].length; j++)
                residualGraph[i][j] = graph[i][j];
        while (breadthFirstSearch(residualGraph, source, terminal, previous)) { //while if there is a path in the residual graph

            int walk = Integer.MAX_VALUE;                                      // the total value of path initially infinity
            for (int nodeV = terminal; nodeV != source; nodeV = previous[nodeV]) { // updating the path
                int nodeU = previous[nodeV];
                walk = Math.min(walk, residualGraph[nodeU][nodeV]);
            }

            for (int nodeV = terminal; nodeV != source; nodeV = previous[nodeV]) {  // for updating residual graph
                int nodeU = previous[nodeV];
                residualGraph[nodeU][nodeV] = residualGraph[nodeU][nodeV] - walk;   // if it is reverse edge
                residualGraph[nodeV][nodeU] = residualGraph[nodeV][nodeU] + walk;   // if it is normal edge
            }
            maxFlow += walk;                                                       // flow equals to the maximum path
        }
        return maxFlow;
    }

    // finding a path, exists or not?
    public static boolean breadthFirstSearch(int residualGraph[][], int source, int terminal, int previous[]) {
        boolean visited[] = new boolean[residualGraph.length];             // if there is a path nodes are visited
        for (int i = 0; i < residualGraph.length; ++i)
            visited[i] = false;
        LinkedList<Integer> bfsList = new LinkedList<Integer>();
        bfsList.add(source);                // start from source node for finding path
        visited[source] = true;             // make the source node as visited, since starting node
        previous[source] = -1;              // no other node can point the source node

        while (bfsList.size() > 0) {        // when the node list is 0 terminate
            int nodeU = bfsList.poll();     // take the node above others
            for (int nodeV = 0; nodeV < residualGraph[0].length; nodeV++) {
                if (visited[nodeV] == false && residualGraph[nodeU][nodeV] > 0) { // if it is not visited and not negative in the resiidual graph
                    if (nodeV == terminal) {                    // check that if it is a terminal node
                        previous[nodeV] = nodeU;                // if it is terminal update the previous node of it and return true, the path exists
                        return true;
                    }
                    bfsList.add(nodeV);                         // add the node to the list
                    previous[nodeV] = nodeU;                    // update previous node of it
                    visited[nodeV] = true;                      // mark the node as visited
                }
            }
        }
        return false;
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        // the code above is very complicated but if you write the matrix, you can understand what i try to do.
        List<Map<String, ArrayList<Integer>>> stations = new ArrayList<>();
        int workCapacity = 0;
        int p1 = 0;
        int p2 = 0;
        int p3 = 0;
        int p4 = 0;
        int []capacities = new int[4];
        // take the stations, only 4 stations
        for(int i = 0; i < 4; i++){
            String station = s.next();
            capacities[i] = s.nextInt();
            ArrayList<Integer> hours = new ArrayList<>();
            int total = 0;
            while(s.hasNextInt()){
                hours.add(s.nextInt());
                total++;
            }
            if(i==0){
                p1 = total;
            }else if(i == 1){
                p2 = total;
            } else if (i == 2) {
                p3 = total;
            }else if (i == 3) {
                p4 = total;
            }
            workCapacity = workCapacity+total;
            Map<String, ArrayList<Integer>> map = new HashMap<>();
            map.put(station,hours);
            stations.add(map);
        }
        // take the employees
        String emp=s.next();
        int empCount=s.nextInt();
        int numVertices = workCapacity+empCount;

        List<Map<ArrayList<String>, ArrayList<Integer>>> listOfEmployees = new ArrayList<>();
        for(int i = 0; i< empCount;i++){
            ArrayList<String> works = new ArrayList<>();
            ArrayList<Integer> hours = new ArrayList<>();
            while(!s.hasNextInt()){
                works.add(s.next());
            }
            while(s.hasNextInt()){
                hours.add(s.nextInt());
            }
            Map<ArrayList<String>, ArrayList<Integer>> map = new HashMap<>();
            map.put(works,hours);
            listOfEmployees.add(map);
        }

        // CONSTRUCTING A GRAPH
        int [][]matrix  = new int[numVertices+2][numVertices+2];

        for(int i = 0 ; i < empCount; i++){
            Map<ArrayList<String>, ArrayList<Integer>> employee = listOfEmployees.get(i);
            Collection<ArrayList<Integer>> employeeHours = employee.values();
            ArrayList<Integer> mergedHours = new ArrayList<>();
            for (ArrayList<Integer> hours : employeeHours) {
                mergedHours.addAll(hours);
            }
            int count = 0;
            for (ArrayList<String> works : employee.keySet()) {
                for (String work : works) {
                    if(work.equals("blend")){
                        Map<String, ArrayList<Integer>> st = stations.get(0);
                        Collection<ArrayList<Integer>> stHours = st.values();
                        ArrayList<Integer> stationMergedHours = new ArrayList<>();
                        for (ArrayList<Integer> hours : stHours) {
                            stationMergedHours.addAll(hours);
                        }
                        for(int k = 0; k< stationMergedHours.size() ; k++ ){
                            for(int p = 0; p< mergedHours.size(); p++){
                                if(mergedHours.get(p) == stationMergedHours.get(k)){
                                    matrix[i+1][empCount+k+1]++;
                                    //matrix[empCount+k+1][numVertices-2+i]++;
                                }
                            }
                        }
                    }

                    if(work.equals("cook")){
                        Map<String, ArrayList<Integer>> st = stations.get(1);
                        Collection<ArrayList<Integer>> stHours = st.values();
                        ArrayList<Integer> stationMergedHours = new ArrayList<>();
                        for (ArrayList<Integer> hours : stHours) {
                            stationMergedHours.addAll(hours);
                        }
                        for(int k = 0; k< stationMergedHours.size() ; k++ ){
                            for(int p = 0; p< mergedHours.size(); p++){
                                if(mergedHours.get(p) == stationMergedHours.get(k)){
                                    matrix[i+1][empCount+k+p1+1]++;
                                    //matrix[empCount+k+p1+1][numVertices-2+i]++;
                                }
                            }
                        }
                    }
                    if(work.equals("strain")){
                        Map<String, ArrayList<Integer>> st = stations.get(2);
                        Collection<ArrayList<Integer>> stHours = st.values();
                        ArrayList<Integer> stationMergedHours = new ArrayList<>();
                        for (ArrayList<Integer> hours : stHours) {
                            stationMergedHours.addAll(hours);
                        }
                        for(int k = 0; k< stationMergedHours.size() ; k++ ){
                            for(int p = 0; p< mergedHours.size(); p++){
                                if(mergedHours.get(p) == stationMergedHours.get(k)){
                                    matrix[i+1][empCount+k+p1+p2+1]++;
                                    //matrix[empCount+k+p1+p2+1][numVertices-2+i]++;
                                }
                            }
                        }
                    }
                    if(work.equals("finish")){
                        Map<String, ArrayList<Integer>> st = stations.get(3);
                        Collection<ArrayList<Integer>> stHours = st.values();
                        ArrayList<Integer> stationMergedHours = new ArrayList<>();
                        for (ArrayList<Integer> hours : stHours) {
                            stationMergedHours.addAll(hours);
                        }
                        for(int k = 0; k< stationMergedHours.size() ; k++ ){
                            for(int p = 0; p< mergedHours.size(); p++){
                                if(mergedHours.get(p) == stationMergedHours.get(k)){
                                    matrix[i+1][empCount+k+p1+p2+p3+1]++;
                                    // matrix[empCount+k+p1+p2+p3+1][numVertices-2+i]++;
                                }
                            }
                        }
                    }
                }
            }
        }

        for(int j = 0; j< p1;j++){
            matrix[empCount+j+1][matrix[0].length-1]=capacities[0];
        }
        for(int j = 0; j< p2;j++){
            matrix[empCount+j+p1+1][matrix[0].length-1]=capacities[1];
        }
        for(int j = 0; j< p3;j++){
            matrix[empCount+j+p1+p2+1][matrix[0].length-1]=capacities[2];
        }
        for(int j = 0; j< p4;j++){
            matrix[empCount+j+p1+p2+p3+1][matrix[0].length-1]=capacities[3];
        }
        //initialization of source nodes to employees
        for(int i = 0; i<empCount; i++){
            matrix[0][i+1] =Integer.MAX_VALUE;
        }

        int p[] = new int[4];
        p[0]=p1;
        p[1]=p2;
        p[2]=p3;
        p[3]=p4 ;
        int totalWork = 0;
        for(int i = 0; i<4;i++){
            totalWork+=p[i]*capacities[i];
        }
        boolean value = fordFulkersonAlgorithm(matrix,0, matrix.length-1) == totalWork;
        System.out.println(value);
    }
}