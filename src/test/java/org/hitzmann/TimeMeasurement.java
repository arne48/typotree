package org.hitzmann;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arne on 26.04.16.
 */
public class TimeMeasurement {

    private List<Long> durationsMilli;
    private List<Long> durationsNano;
    private boolean isRunning;
    private long startTimeMilli;
    private long startTimeNano;

    public TimeMeasurement(){
        this.durationsMilli = new ArrayList<Long>();
        this.durationsNano = new ArrayList<Long>();
        this.isRunning = false;
    }


    public void stop(){
        if(isRunning){
            durationsMilli.add(System.currentTimeMillis() - startTimeMilli);
            durationsNano.add(System.nanoTime() - startTimeNano);
            isRunning = false;
        }
    }

    public void start(){
        if(!isRunning){
            this.startTimeMilli = System.currentTimeMillis();
            this.startTimeNano = System.nanoTime();
            isRunning = true;
        }
    }

    public Long getMeanDurationInNanos(){

        Long ret = new Long(0);

        for(Long duration : this.durationsNano){
            ret = ret + new Long(duration);
        }

        return ret / this.durationsNano.size();
    }

    public Long getMeanDurationInMillis(){

        Long ret = new Long(0);

        for(Long duration : this.durationsMilli){
            ret = ret + new Long(duration);
        }

        return ret / this.durationsMilli.size();

    }

    public Long getTotalTimeInMillis(){
        Long ret = new Long(0);

        for(Long duration : this.durationsMilli){
            ret = ret + new Long(duration);
        }

        return ret;
    }

    public void reset(){
        durationsMilli.clear();
    }
}
