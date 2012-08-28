package com.chosen0ne.taskschedule.job;

import java.util.logging.Logger;

import com.chosen0ne.taskschedule.arch.CPU;
import com.chosen0ne.taskschedule.lib.ReadFS;

public class SleepCallJob extends AbstractJob{
private static Logger logger = Logger.getLogger(CounterForkJob.class.getName());
	
	private int counter;
	private int endVal;
	private int callVal;
	
	public SleepCallJob(int initVal, int endVal, int callVal){
		this.counter = initVal;
		this.endVal = endVal;
		this.callVal = callVal;
	}
	@Override
	public void jobRun() {
		while(isSched && counter <= endVal){
			logger.info(String.format("%s : %d", CPU.getInstance().getCurrentTask().getName(),
					counter++));
			
			if(counter == callVal){
				new ReadFS().call();
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
