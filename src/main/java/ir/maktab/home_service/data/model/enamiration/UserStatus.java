package ir.maktab.home_service.data.model.enamiration;

public enum UserStatus {
    CONFIRMED, WAITING, NEW;

    public static UserStatus getValue(String status) {
        switch (status.toLowerCase()) {
            case "confirmed":
                return CONFIRMED;
            case "new":
                return NEW;
            default:
                return WAITING;
        }
    }
}
