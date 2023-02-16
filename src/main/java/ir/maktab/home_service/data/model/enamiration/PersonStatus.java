package ir.maktab.home_service.data.model.enamiration;

public enum PersonStatus {
    CONFIRMED, WAITING, NEW;

    public static PersonStatus getValue(String status) {
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
