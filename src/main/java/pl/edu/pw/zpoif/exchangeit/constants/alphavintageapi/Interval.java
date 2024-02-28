package pl.edu.pw.zpoif.exchangeit.constants.alphavintageapi;

public enum Interval {
    ONE_MIN("1min"), FIVE_MIN("5min"), FIFTEEN_MIN("15min"), THIRTY_MIN("30min"), SIXTY_MIN("60min");

    private final String val;

    Interval(String val) {
        this.val = val;
    }

    public static String concatenate() {
        StringBuilder result = new StringBuilder();

        for (Interval constant : Interval.values()) {
            if (!result.isEmpty()) {
                result.append(", ");
            }
            result.append(constant.getVal());
        }

        return result.toString();
    }

    public String getVal() {
        return val;
    }

    public static boolean isCorrect(String val) {
        for (Interval interval : Interval.values()) {
            if (interval.getVal().equals(val)) {
                return true;
            }
        }
        return false;
    }
}
