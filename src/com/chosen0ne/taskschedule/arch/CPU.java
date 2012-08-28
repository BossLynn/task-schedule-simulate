package com.chosen0ne.taskschedule.arch;

import java.util.Date;
import java.util.logging.Logger;

import com.chosen0ne.taskschedule.os.Task;

public class CPU implements Runnable{
	private static Logger logger = Logger.getLogger(CPU.class.getName());
	public static int DEFAULT_TIME_SLICE = 5000;
	
	private static CPU cpu = new CPU();
	private boolean isRunning;
	private Task currentTask;
	private Thread thread;
	
	private CPU() {
		thread = new Thread(this);
	}
	
	public static CPU getInstance() {
		return cpu;
	}
	
	@Override
	public void run() {
		logger.info("CPU runs");
		while(isRunning){
			if(currentTask != null){
				long ts = new Date().getTime();
				currentTask.setStartExec(ts);
				currentTask.run();
			}else{
				try {
					logger.info("dile");
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void start(){
		thread.start();
		isRunning = true;
	}
	
	public Task getCurrentTask() {
		return currentTask;
	}

	public void setCurrentTask(Task currentTask) {
		this.currentTask = currentTask;
	}
	
	public void switchInTask(Task task) {
		currentTask = task;
	}
	
	public void switchOutTask(){
		currentTask.getJob().contextSwitchOut();
		currentTask = null;
	}
}
