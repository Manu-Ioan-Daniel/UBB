package strategies;
import model.MyNextTask;
import model.NatatieData;
import model.Rata;
import model.Task;

public class BinarySearchStrategy implements SolvingStrategy {
    @Override
    public void solve(Task t) {
        if(t instanceof MyNextTask myTask){
            NatatieData data=myTask.getData();
            Rata[] rate=data.getRate();
            //sortam dupa rezistenta
            for (int i = 0; i <data.getN()-1; i++) {
                for (int j = i + 1; j < data.getN(); j++) {
                    if (rate[i].getRezistenta() > rate[j].getRezistenta()) {
                        Rata temp = rate[i];
                        rate[i] = rate[j];
                        rate[j] = temp;
                    }
                }
            }
            double low=0;
            double high= (double) (2 * data.getDistante()[data.getM() - 1]) /minViteza(rate);
            Rata[] rateFolosite=new Rata[data.getM()];
            while(high-low>1e-4){
                double mid=(low+high)/2;
                if(canFinish(rate,data.getDistante(),data.getM(),mid,rateFolosite)){
                    high=mid;
                }else{
                    low=mid;
                }
            }
            canFinish(rate,data.getDistante(),data.getM(),high,rateFolosite);
            System.out.println("Best time: "+String.format("%.3f s",high));
            for(int m=0;m<data.getM();m++){
                System.out.println("Lane "+(m+1)+" ("+"d="+data.getDistante()[m]+") <- Duck "+m+1+" (v="+rateFolosite[m].getViteza()+", st="+rateFolosite[m].getRezistenta()+")");
            }


        }else {
            System.out.println("Cooked");
        }
    }
    private  int minViteza(Rata[] rate){
        int min=rate[0].getViteza();
        for(Rata r:rate){
            if(r.getViteza()<min){
                min=r.getViteza();
            }
        }
        return min;
    }
    private boolean canFinish(Rata[] rate,int[] distante,int M,double timp,Rata[] rateFolosite){
        int N=rate.length;
        int used=0;
        int ultimaRezFolosita=-1;
        for(int i = 0;i<N && used<M;i++){
            double T= (double) (2 * distante[used]) /rate[i].getViteza();
            if(rate[i].getRezistenta()>=ultimaRezFolosita && T<=timp){
                rateFolosite[used]=rate[i];
                ultimaRezFolosita=rate[i].getRezistenta();
                used++;
            }
        }
        return used==M;
    }
}
