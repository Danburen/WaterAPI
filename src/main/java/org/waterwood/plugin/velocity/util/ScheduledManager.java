package org.waterwood.plugin.velocity.util;

import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.scheduler.ScheduledTask;
import org.apache.commons.lang3.ClassUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *  A manager util that hold Velocity Scheduled tasks
 *  Need proxy server and plugin instance
 *
 * @since 1.0.4
 * @author Danburen
 */

public class ScheduledManager {
    private final Map<String,ScheduledTask> tasks = new HashMap<>();
    private final ProxyServer proxyServer;
    private final Object pluginInstance;

    public ScheduledManager(ProxyServer proxyServer,Object pluginInstance) {
        this.proxyServer = proxyServer;
        this.pluginInstance = pluginInstance;
    }

    /**
     * Cancel and Remove all the tasks
     */
    public void cancelAllTasks() {
        tasks.values().forEach(ScheduledTask::cancel);
    }

    /**
     * Cancel and remove the target task
     * @param taskName task's name whose to remove
     */
    public void cancelTask(String taskName) {
        ScheduledTask task = tasks.get(taskName);
        if (task != null) {
            task.cancel();
            tasks.remove(taskName);
        }
        System.out.println("task " + taskName + " cancelled");
    }

    /**
     * Add delay and repeated complexes task
     * @param taskName task's name
     * @param task Runnable task to run
     * @param delay delay time
     * @param timeUnit delay timeUtil
     * @param repeat repeat time
     * @param repeatUnit repeat timeUtil
     */
    public void addDelayAndRepeatTask(String taskName, Runnable task ,long delay, TimeUnit timeUnit,long repeat,TimeUnit repeatUnit) {
        taskCheck(taskName, () -> tasks.put(taskName, proxyServer.getScheduler()
                .buildTask(pluginInstance,task)
                .delay(delay, timeUnit)
                .repeat(repeat, repeatUnit).schedule()));
    }

    /**
     * Add repeated task
     * @param taskName task's name
     * @param task Runnable task to run
     * @param repeat repeat time
     * @param timeUnit time util
     */
    public void addRepeatTask(String taskName, Runnable task, long repeat,TimeUnit timeUnit) {
        taskCheck(taskName, () -> tasks.put(taskName, proxyServer.getScheduler()
                .buildTask(pluginInstance,task)
                .repeat(repeat, timeUnit).schedule()));
    }

    /**
     * Add repeated task
     * @param taskName task's name
     * @param task Runnable task to run
     * @param delay delay time
     * @param timeUnit time util
     */
    public void addDelayTask(String taskName, Runnable task, long delay, TimeUnit timeUnit) {
        taskCheck(taskName, () -> tasks.put(taskName, proxyServer.getScheduler()
                .buildTask(pluginInstance,task)
                .delay(delay, timeUnit)
                .schedule()));
    }

    /**
     * Check whether the task is already existed.
     * <p></p>
     * <b>If existed then cancel and do next</b>
     * @param taskName task name
     * @param handler task execute handler
     */
    private void taskCheck(String taskName,taskHandler handler) {
        if(tasks.containsKey(taskName)) {
            cancelTask(taskName);
        }
        handler.execute();
    }

    private interface taskHandler{
        void execute();
    }
}
