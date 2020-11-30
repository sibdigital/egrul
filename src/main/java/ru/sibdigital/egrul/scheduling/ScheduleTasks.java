package ru.sibdigital.egrul.scheduling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import ru.sibdigital.egrul.model.ModelTypes;
import ru.sibdigital.egrul.service.ImportService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Component
public class ScheduleTasks {

    @Autowired
    private ImportService importService;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Autowired
    private ScheduledAnnotationBeanPostProcessor postProcessor;

    Map<Integer, ScheduledFuture<?>> jobsMap = new HashMap<>();

    public void addTaskToScheduler(ModelTypes type, Integer loadVersion, Date date) {
        if (date.compareTo(new Date()) >= 0) {
            Runnable task = new ImportRunnableTask(type, loadVersion);
            ScheduledFuture<?> scheduledTask = taskScheduler.schedule(task, date);
            jobsMap.put(ModelTypes.EGRUL_LOAD.getValue(), scheduledTask);
        }
    }

    public void removeTaskFromScheduler(ModelTypes type) {
        Integer id = type.getValue();
        ScheduledFuture<?> scheduledTask = jobsMap.get(id);
        if(scheduledTask != null) {
            scheduledTask.cancel(true);
            postProcessor.postProcessBeforeDestruction(scheduledTask, "taskScheduler");
            jobsMap.put(id, null);
        }
    }

    public void addTaskToSchedulerAll(Date date) {
        if (date.compareTo(new Date()) >= 0) {
            Runnable task = new ImportRunnableTaskAll();
            ScheduledFuture<?> scheduledTask = taskScheduler.schedule(task, date);
            jobsMap.put(777, scheduledTask);
        }
    }


    class ImportRunnableTask implements Runnable {

        private ModelTypes type; // egrul/egrip
        private Integer loadVersion; // 0 - full, 1 - обновления

        public ImportRunnableTask(ModelTypes type, Integer loadVersion) {
            this.type = type;
            this.loadVersion = loadVersion;
        }

        @Override
        public void run() {
            if (type.getValue() == ModelTypes.EGRUL_LOAD.getValue()) {
                importService.importEgrul(loadVersion);
            }
            else if (type.getValue() == ModelTypes.EGRIP_LOAD.getValue()) {
                importService.importEgrip(loadVersion);
            }
        }
    }

    class ImportRunnableTaskAll implements Runnable {

        public ImportRunnableTaskAll() {
        }

        @Override
        public void run() {
            // FULL
            importService.importEgrul(0);
            importService.importEgrip(0);

            //Updates
            importService.importEgrul(1);
            importService.importEgrip(1);

        }
    }

}
