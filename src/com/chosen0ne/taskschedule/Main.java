package com.chosen0ne.taskschedule;

import com.chosen0ne.taskschedule.arch.CPU;
import com.chosen0ne.taskschedule.arch.Timer;
import com.chosen0ne.taskschedule.job.CounterForkJob;
import com.chosen0ne.taskschedule.job.CounterJob;
import com.chosen0ne.taskschedule.job.SleepCallJob;
import com.chosen0ne.taskschedule.os.Task;

public class Main {

	public static void main(String[] args) {
		CPU.getInstance().start();
		new Timer();
		
		new Task("JOB A", new CounterForkJob(1, 20, 5));
		new Task("JOB B", new CounterJob(100, 150));
		new Task("JOB D", new SleepCallJob(200, 220, 205));
	}
}
