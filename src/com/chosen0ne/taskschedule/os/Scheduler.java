package com.chosen0ne.taskschedule.os;

import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

import com.chosen0ne.taskschedule.arch.CPU;

public class Scheduler {
	private static Logger logger = Logger.getLogger(Scheduler.class.getName());
	private static Scheduler scheduler = new Scheduler();
	
	private Queue<Task> runQueue = new ConcurrentLinkedQueue<Task>();

	private Scheduler(){}
	
	public static Scheduler getInstance(){
		return scheduler;
	}
	
	/**
	 * 进程调度程序，实现简单的基于时间片和优先级的进程调度算法。
	 * 进程的优先级范围：[1, 10]。优先级n将获得n个cpu时间片。
	 */
	public void schedule(){
		if(runQueue.isEmpty()){
			logger.info(String.format("no Task need to schedule"));
			return;
		}
		
		long now = new Date().getTime();
		
		Task current = CPU.getInstance().getCurrentTask();
		
		if(current == null){
			Task task = runQueue.poll();
			CPU.getInstance().setCurrentTask(task);
			task.setJustStart(false);
			return ;
		}
		
		if(now - current.getStartExec() >= CPU.DEFAULT_TIME_SLICE * current.getPriority()){
			
			if(runQueue.isEmpty()){
				/* 
				 * if the run queue is empty, it needn't to schedule.
				 * Current task will run continuously.
				 */
				
				return;
			}
			
			/* else if run queue isn't empty, another task will be scheduled. */
			Task task = runQueue.poll();
			
			logger.info(String.format("Task %s is selected to run, Task %s switch out", 
					task.getName(), current.getName()));
			CPU.getInstance().switchOutTask();
			task.setJustStart(false);
			CPU.getInstance().switchInTask(task);
			
			/* push current task into run queue */
			runQueue.offer(current);
		}

	}
	
	public void addTask(Task t){
		runQueue.offer(t);
	}
	
	public void removeTask(Task t){
		runQueue.poll();
	}
}
