public class RelaxingMethod {

    private double matrA[][];
    private double vectorB[];
    private double prevX[];
    private double curX[];
    private double epsilon = 0.00001;
    private double w = 0.98;
    private double discrepancy[];
    private int count;
    private int n;


    public RelaxingMethod(double matrA[][], double vectorB[]){
        n = matrA.length;
        count = 0;
        this.vectorB = new double[n];
        prevX = new double[n];
        curX = new double[n];
        this.matrA = new double[n][n];
        for(int i = 0; i < n; i++){
            this.vectorB[i] = vectorB[i];
            for(int j = 0; j < n; j++){
                this.matrA[i][j] = matrA[i][j];
            }
        }
    }

    public void createSymmetricMatr(){
        double symmetricMatr[][] = new double[n][n];
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                symmetricMatr[i][j] = 0;
                for (int k = 0; k < n; k++) {
                    symmetricMatr[i][j] += matrA[k][i] * matrA[k][j];
                }
            }
        }
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                matrA[i][j] = symmetricMatr[i][j];
            }
        }
    }

    public void createNewVectorB(){
        double newVector[] = new double[n];
        for(int i=0; i<n; i++)
        {
            newVector[i]=0;
            for(int j=0; j<n; j++)
            {
                newVector[i] += matrA[j][i]*vectorB[j];
            }
        }
        for(int i = 0; i < n; i++){
            prevX[i] = vectorB[i] / matrA[i][i];
            vectorB[i] = newVector[i];
        }
    }

    private double difference(){
        double diff = Math.abs(prevX[0] - curX[0]);
        for(int i = 0; i < n; i++){
            if(Math.abs(prevX[i] - curX[i]) > diff){
                diff = Math.abs(prevX[i] - curX[i]);
            }
        }
        return diff;
    }
    private void newPrevX(){
        for(int i = 0; i < n; i++){
            prevX[i] = curX[i];
        }
    }

    public void relaxingMethod(){
        double sum;
        createNewVectorB();
        createSymmetricMatr();
        while (difference() > epsilon){
            count++;
            newPrevX();
            for(int i = 0; i < n; i++){
                sum = 0;
                for(int j = 0; j < n; j++){
                    if(j != i){
                        if(j < i){
                            sum -= matrA[i][j] / matrA[i][i] * curX[j];
                        }
                        else{
                            sum -= matrA[i][j] / matrA[i][i] * prevX[j];
                        }
                    }
                }
                sum += vectorB[i] / matrA[i][i];
                curX[i] = (1-w)*prevX[i] + w*sum;
            }
        }
    }
    public void createDiscrepancy(double matrA[][], double vectorB[]){
        discrepancy = new double[n];
        double[] res = new double[n];
        for(int i = 0; i < n; i++){
            res[i] = 0;
            for(int j = 0; j < n; j++){
                res[i] += matrA[i][j]*curX[j];
            }
            discrepancy[i] = vectorB[i] - res[i];
        }
    }

    public void print(){
        System.out.println("Vector X");
        for(double item: curX){
            System.out.format("%25s", item + "    ");
        }
        System.out.println("count = " + count);
    }
    public void printDiscrepancy(){
        System.out.println("Discrepancy");
        for(double item: discrepancy){
            System.out.format("%25s", item + "    ");
        }
        System.out.println();
    }

    public void printAll(){
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                System.out.format("%25s", matrA[i][j] + "    ");
            }
            System.out.format("%25s", vectorB[i] + "\n");
        }
    }

}
