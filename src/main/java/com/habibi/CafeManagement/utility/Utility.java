package com.habibi.CafeManagement.utility;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Utility {
    public static Date localDateWithTime() {
        LocalDateTime now = LocalDateTime.now(); // Current date and time
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

    }
}
