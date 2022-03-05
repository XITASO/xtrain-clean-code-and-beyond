package de.xitaso.taskman.services;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import de.xitaso.taskman.data.TaskRepository;
import de.xitaso.taskman.entities.Goal;
import de.xitaso.taskman.entities.TaskState;

public class GoalPriorizationService {

    private TaskRepository taskRepository;

    @Autowired
    public GoalPriorizationService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Collection<Goal> findCriticalGoals(Collection<Goal> goals) {
        return goals.stream().filter(g -> isCritical(g)).collect(Collectors.toUnmodifiableList());
    }

    private boolean isCritical(Goal goal) {
        return responsibilityIsInMunich(goal) || hasMoreThan50OpenTasks(goal);
    }

    private boolean responsibilityIsInMunich(Goal goal) {
        return goal.getResponsiblePerson().getAddress().getCity().equals("Munich");
    }

    private boolean hasMoreThan50OpenTasks(Goal goal) {
        var openTasksCount = goal.getProjects().stream().flatMap(project -> project.getTaskIds().stream())
                .filter(taskId -> TaskState.IsOpen(taskRepository.findOne(taskId).getState())).count();
        return openTasksCount >= 50;
    }
}
