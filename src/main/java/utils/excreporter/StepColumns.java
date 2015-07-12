package utils.excreporter;

enum StepColumns {

    STEP_NUMBER(0), DESCRIPTION(1), DATA(2), ACTUAL_RESULT(3), EXPECTED_RESULT(4), STATUS(5), CONDITION(6), ADDITIONAL_INFO(7);

    private StepColumns(int position) {
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }

    private final int position;
}
