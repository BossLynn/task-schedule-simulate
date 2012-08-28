package com.chosen0ne.taskschedule.job;

import java.util.logging.Logger;

import com.chosen0ne.taskschedule.arch.CPU;

public class CounterJob extends AbstractJob{
	private static Logger logger = Logger.getLogger(CounterJob.class.getName());
	
	private int counter;
	private int endVal;
	
	public CounterJob(int initVal, int endVal){
		this.counter = initVal;
		this.endVal = endVal;
	}
	
	@Override
	public void jobRun() {
		 while(this.isSched && counter <= endVal){
			 logger.info(String.format("%s : %d", CPU.getInstance().getCurrentTask().getName()
					 , counter++));
			 
			 try {
				 Thread.sleep(1000);
			 } catch (InterruptedException e) {
				 e.printStackTrace();
			 }
		 }
	}

}
