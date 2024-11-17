package com.example.evaluation;

import java.util.List;

public class RequiredClassInfo {
    private List<String> requiredMethods;
    private List<String> requiredAttributes;

    public RequiredClassInfo(List<String> requiredMethods, List<String> requiredAttributes) {
        this.requiredMethods = requiredMethods;
        this.requiredAttributes = requiredAttributes;
    }

    public List<String> getRequiredMethods() {
        return requiredMethods;
    }

    public List<String> getRequiredAttributes() {
        return requiredAttributes;
    }
}