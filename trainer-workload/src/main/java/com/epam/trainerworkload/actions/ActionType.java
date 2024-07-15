package com.epam.trainerworkload.actions;

public enum ActionType {
    ADD("add", new AddAction()),
    DELETE("delete", new DeleteAction());

    private final String action;
    private final TrainingAction trainingAction;

    ActionType(String action, TrainingAction trainingAction) {
        this.action = action;
        this.trainingAction = trainingAction;
    }

    public String getAction() {
        return action;
    }

    public TrainingAction getTrainingAction() {
        return trainingAction;
    }

    public static ActionType fromString(String action) {
        for (ActionType type : ActionType.values()) {
            if (type.getAction().equalsIgnoreCase(action)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid action type: " + action);
    }
}

