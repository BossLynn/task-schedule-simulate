package com.chosen0ne.taskschedule.lib;

import java.util.Queue;
import java.util.logging.Logger;

import com.chosen0ne.taskschedule.os.Task;

public class ReadFS extends SleepSysCall{
	private static Logger logger = Logger.getLogger(ReadFS.class.getName());
	
	@Override
	public void proc(Queue<Task> queue) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(Task t: queue){
			logger.info(String.format("#### %s read data from FS", t.getName()));
		}
	}

}
