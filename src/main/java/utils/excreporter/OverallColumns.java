package utils.excreporter;

enum OverallColumns {

    TEST_CASE_NAME(1), TC_STATUS(2), TC_PRIORITY(3), DATE(5), CASES_RUN(6), CASES_FAILED(7);

    OverallColumns(int position) {
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }

    private final int position;

}
