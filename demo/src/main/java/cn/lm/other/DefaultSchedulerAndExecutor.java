package cn.lm.other;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DefaultSchedulerAndExecutor {
	
	@Scheduled(cron="0 0 * * * ?")
	public void scheduledTask() {
		System.out.println("@Scheduled Task");
	}
	
	@Async()
	public /*Future<String>*/void asyncTask(int i) {
		System.out.println("@Async Task:"+i);
	}
	
}
