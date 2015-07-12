package utils.excreporter;


import elements.detailed.Status;
import utils.Priority;

class Note {

    Note (String testCaseName, Status status, Priority priority) {
        this.testCaseName = testCaseName;
        this.status = status;
        this.priority = priority;
    }

    String getTestCaseName() {
        return this.testCaseName;
    }
    Status getStatus() {
        return this.status;
    }
    Priority getPriority() {
        return this.priority;
    }

    private String testCaseName;
    private Status status;
    private Priority priority;

}
