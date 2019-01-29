package com.sebastian_daschner.barista.control;

import com.sebastian_daschner.barista.entity.CoffeeBrew;

import java.util.Random;

public class RandomStatusProcessor {

    private static final double PERCENTAGE = 0.4;

    public String processStatus(CoffeeBrew brew) {
        String status = brew.getStatus();
        if (randomSelection())
            return updateStatus(brew);
        return status;
    }

    private boolean randomSelection() {
        return new Random().nextDouble() <= PERCENTAGE;
    }

    private String updateStatus(CoffeeBrew brew) {
        switch (brew.getStatus()) {
            case "PREPARING":
                return "FINISHED";
            case "FINISHED":
                return "COLLECTED";
            case "COLLECTED":
                return "COLLECTED";
            default:
                throw new IllegalArgumentException("Unknown status " + brew.getStatus());
        }
    }

}
