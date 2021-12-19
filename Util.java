public class Util {
    public static boolean isPowerTwo(int n){
        while(n%2==0){
            n=n/2;
        }
        return n==1;
    }
    public static int log2Int(int n){
        int ret = 0;
        while(n%2==0){
            n=n/2;
            ret++;
        }
        return ret;
    }
    public static int closestPowerTwo(int num){
        int sq = 0;
        for(int i =num; i>0; i--){
            if(isPowerTwo(i)){
                sq = i;
                break;
            }
        }
        for(int i = num; i<num+(num-sq); i++){
            if(isPowerTwo(i))return i;
        }
        return sq;
    } 
}
