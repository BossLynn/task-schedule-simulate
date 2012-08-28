package com.chosen0ne.taskschedule.job;

public interface Job {
	void contextSwitchIn();
	void contextSwitchOut();
	void run();
}
