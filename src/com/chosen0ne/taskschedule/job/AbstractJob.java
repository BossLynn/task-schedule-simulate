package com.chosen0ne.taskschedule.job;

import com.chosen0ne.taskschedule.arch.CPU;
import com.chosen0ne.taskschedule.os.Scheduler;

public abstract class AbstractJob implements Job{
	protected boolean isSched;
	
	@Override
	public void contextSwitchIn(){
		isSched = true;
	}
	
	@Override
	public void contextSwitchOut(){
		isSched = false;
	}
	
	@Override
	public void run(){
		jobRun();
		
		if(isSched){
			/**
			 * if 'isSched' flag is true, it shows current task is normally stopped.
			 * This will trigger the next schedule.
			 */
			
			CPU.getInstance().setCurrentTask(null);
			Scheduler.getInstance().schedule();
		}
	}
	
	public abstract void jobRun();
}
