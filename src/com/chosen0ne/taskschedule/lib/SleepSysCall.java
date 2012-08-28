package com.chosen0ne.taskschedule.lib;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

import com.chosen0ne.taskschedule.arch.CPU;
import com.chosen0ne.taskschedule.os.Scheduler;
import com.chosen0ne.taskschedule.os.Task;

public abstract class SleepSysCall {
	private static Logger logger = Logger.getLogger(SleepSysCall.class.getName());
	
	protected Queue<Task> waitQueue = new ConcurrentLinkedQueue<Task>();
	
	public SleepSysCall(){
		
	}
	
	public abstract void proc(Queue<Task> q);
	
	public void call(){
		final Task current = CPU.getInstance().getCurrentTask();

		logger.info(String.format("%s sleeps", current.getName()));
		this.waitQueue.offer(current);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				proc(waitQueue);
				
				while(!waitQueue.isEmpty()){
					Task task = waitQueue.poll();
					Scheduler.getInstance().addTask(task);
					logger.info(String.format("%s wakes up", task.getName()));
				}
			}
		}).start();
		
		CPU.getInstance().switchOutTask();
		CPU.getInstance().setCurrentTask(null);
		Scheduler.getInstance().schedule();
	}
}
