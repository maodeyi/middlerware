package com.jason.gateway.IDAuth;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.jason.gateway.IDAuth.modle.IDAuthRequest;
import com.jason.gateway.IDAuth.task.RequestTask;
import com.jason.gateway.IDAuth.task.ResultTask;
import com.jason.gateway.IDAuth.task.SendRequestTask;
import com.jason.gateway.IDAuth.utils.Utils;

public class IDAuthJob {
	public static ConcurrentLinkedQueue<IDAuthRequest> sendqueue = new ConcurrentLinkedQueue<IDAuthRequest>();

	private void executeFixedRate(Runnable task, int delay) {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(task, 0, delay, TimeUnit.SECONDS);
	}

	private void executeFixed(Runnable task, int threadsnum) {
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threadsnum);
		fixedThreadPool.execute(task);
	}

	public static void main(String[] args) {
		IDAuthJob job = new IDAuthJob();

		RequestTask reqtask = new RequestTask();
		job.executeFixedRate(reqtask, Utils.getRequestDelay());

		SendRequestTask sendtask = new SendRequestTask();
		job.executeFixed(sendtask, Utils.getRequestSendThread());

//		ResultTask restask = new ResultTask();
//		job.executeFixedRate(restask, Utils.getResutlThread());

	}
}