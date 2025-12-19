package Entities.StandingOrders;

import java.time.LocalDate;

public enum FrequencyType {
    DAILY {
        public LocalDate next(LocalDate d) { return d.plusDays(1); }
    },
    WEEKLY {
        public LocalDate next(LocalDate d) { return d.plusWeeks(1); }
    },
    MONTHLY {
        public LocalDate next(LocalDate d) { return d.plusMonths(1); }
    },
    AUTO {
        public LocalDate next(LocalDate d) { return d.plusMonths(1); }
    };

    public abstract LocalDate next(LocalDate date);
}


