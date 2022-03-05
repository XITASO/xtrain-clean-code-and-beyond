package de.xitaso.taskman.services;

import java.util.Collection;
import java.util.stream.Collectors;

import de.xitaso.taskman.entities.Goal;

public class GoalPriorizationService {

    public Collection<Goal> findCriticalGoals(Collection<Goal> goals) {
        return goals.stream().filter(g -> isCritical(g)).collect(Collectors.toUnmodifiableList());
    }

    private boolean isCritical(Goal goal) {
        return responsibilityIsInMunich(goal);
    }

    private boolean responsibilityIsInMunich(Goal goal) {
        return goal.getResponsiblePerson().getAddress().getCity().equals("Munich");
    }
}
