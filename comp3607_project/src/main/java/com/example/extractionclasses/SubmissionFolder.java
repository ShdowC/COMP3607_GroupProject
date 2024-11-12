package com.example.extractionclasses;

//Composite Class
import java.util.ArrayList;
import java.util.List;

public class SubmissionFolder implements SubmissionComponent {
    private String name;
    private List<SubmissionComponent> children = new ArrayList<>();

    public SubmissionFolder(String name) {
        this.name = name;
    }

    public void add(SubmissionComponent component) {
        children.add(component);
    }

    public void remove(SubmissionComponent component) {
        children.remove(component);
    }

    public List<SubmissionComponent> getChildren() {
        List<SubmissionComponent> childComponents = new ArrayList<>();
        for (SubmissionComponent child : this.children) {
            if (child instanceof SubmissionFolder) {
                childComponents.addAll(((SubmissionFolder) child).getChildren());
            } else {
                childComponents.add(child);
            }
        }
        return childComponents;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void runTests() {
        for (SubmissionComponent component : children) {
            component.runTests();
        }
    }
}
