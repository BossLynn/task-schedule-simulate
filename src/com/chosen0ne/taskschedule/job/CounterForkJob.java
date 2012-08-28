package com.chosen0ne.taskschedule.job;

import java.util.logging.Logger;

import com.chosen0ne.taskschedule.arch.CPU;
import com.chosen0ne.taskschedule.os.Scheduler;
import com.chosen0ne.taskschedule.os.Task;

public class CounterForkJob extends AbstractJob{
	private static Logger logger = Logger.getLogger(CounterForkJob.class.getName());
	
	private int counter;
	private int endVal;
	private int forkVal;
	
	public CounterForkJob(int initVal, int endVal, int forkVal){
		this.counter = initVal;
		this.endVal = endVal;
		this.forkVal = forkVal;
	}
	@Override
	public void jobRun() {
		while(isSched && counter <= endVal){
			logger.info(String.format("%s : %d", CPU.getInstance().getCurrentTask().getName(),
					counter++));
			
			if(counter == forkVal){
				Scheduler.getInstance().addTask(new Task("JOB C", new CounterJob(1000, 1050)));
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
