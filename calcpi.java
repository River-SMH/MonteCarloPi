/* River Smith
 * CS426
 * Program that calculates pi using the Monte Carlo Method
 */
import java.util.concurrent.ThreadLocalRandom;

public class calcpi {
    public static int thread=8;
    public static int limit = 20000000;
	public static void main(String[] args) throws InterruptedException{
        long start=System.nanoTime();//run time of program
        CalcPiThread[] threads = new CalcPiThread[thread];
        double totalhit=0.0;

        for(int i=0;i<thread;i++){ //creates a thread within the range of arrays.
            threads[i] = new CalcPiThread();
            threads[i].start(); //starts the threading process when each thread is initialized
        }
        for(int i=0;i<thread;i++){
            threads[i].join(); //combines each thread into their array position.
        }
        for(int i=0;i<thread;i++){
            totalhit+=threads[i].sum(); //gets the sum of every array value returned from the threads.
        }

        double result = (totalhit/limit) * 16.0; //if the distance was set to 1 then it would be *4 
                                                 //however since it is half of that it must be squared to achieve pi
        long end = System.nanoTime();
        long runTime=(end-start)/1000000000; //converts the nanoseconds into whole seconds
        
        System.out.println("PI = "+result+" taking "+runTime+" seconds to finish.");

    }

    static class CalcPiThread extends Thread{ //Carries the amount of hits each thread gets when calculating pi
        private double hit=0.0;

        public void run(){
            
            for(int i=0;i<(limit/thread);i++){ //gets the hits within the 0.5 distance for pi.
                if(getDistance()==true)hit++;
            }
            System.out.println(hit);
        }

        public double sum(){ //returns the hits from the thread
            return hit;
        }
    }

    public static boolean getDistance(){ //creates random doubles for coordinates then calculates using distance formula for pi

    	double x = ThreadLocalRandom.current().nextDouble(-1,1); //gets random double for both x and y cords.
        double y = ThreadLocalRandom.current().nextDouble(-1,1);
        //double x = Math.random()*2.0-1.0; this line of code cost me over an hour to figure out that it was bad.
        //double y = Math.random()*2.0-1.0; Using math.random here makes it so that single thread is faster than any multithread.
        double distance = Math.sqrt((x*x)+(y*y));
        
        if(distance<=0.5) return true;
        return false;
    }

}