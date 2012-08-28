package com.chosen0ne.taskschedule.os;

import java.util.logging.Logger;

import com.chosen0ne.taskschedule.job.Job;

public class Task implements Runnable{
	private static Logger logger = Logger.getLogger(Task.class.getName());
	
	public static final int MIN_PRIORITY = 1;
	public static final int MAX_PRIORITY = 10;
	
	public static final int TASK_RUNNING = 1;
	public static final int TASK_INTERRUPTIBLE = 2;
	public static final int TASK_UNINTERRUPTIBLE = 3;
	
	private long startExec;
	private int priority = 1;
	
	private Job job;
	private boolean justStart = true;
	private String name;
	private int status = TASK_RUNNING;
	
	public Task(String name, Job job){
		this(name, job, 1);
	}
	
	public Task(String name, Job job, int prio){
		this.name = name;
		this.job = job;
		if(prio < MIN_PRIORITY || prio > MAX_PRIORITY){
			throw new RuntimeException("unsupported priority, must less then 11 and large than 0");
		}
		this.priority = prio;
		
		Scheduler.getInstance().addTask(this);
	}

	public long getStartExec() {
		return startExec;
	}

	public void setStartExec(long startExec) {
		this.startExec = startExec;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}


	public boolean isJustStart() {
		return justStart;
	}

	public void setJustStart(boolean justStart) {
		this.justStart = justStart;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * emulate system call 'fork'
	 * @param task
	 */
	public static void fork(Task task){
		logger.info(String.format("fork task '%s'", task.getName()));
		Scheduler.getInstance().addTask(task);
	}

	@Override
	public void run() {
		if(!justStart){
			job.contextSwitchIn();
			logger.info(String.format("Task %s switch in", name));
		}
		
		job.run();
	}
}