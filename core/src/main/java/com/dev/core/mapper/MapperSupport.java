package com.dev.core.mapper;

import com.dev.core.utils.TimeUtil;

import javax.inject.Named;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Singleton
@Named("MapperSupport")
public class MapperSupport {
    /**
     * Big decimal to string.
     *
     * @param bigDecimal
     *          the big decimal
     * @return the string
     */
    public String bigDecimalToString(final BigDecimal bigDecimal) {
        return bigDecimal.toString();
    }

    /**
     * Date to timestamp.
     *
     * @param date
     *          the date
     * @return the timestamp
     */
    public LocalDateTime dateToLocalDateTime(final Date date) {
        return TimeUtil.dateToLocalDataTime(date);
    }

    /**
     * Timestamp to date.
     *
     * @param timestamp
     *          the timestamp
     * @return the date
     */
    public Date localDateTimeToDate(final LocalDateTime timestamp) {
        return TimeUtil.localDateTimeToDate(timestamp);
    }

    /**
     * String to big decimal.
     *
     * @param string
     *          the string
     * @return the big decimal
     */
    public BigDecimal stringToBigDecimal(final String string) {
        return new BigDecimal(string);
    }
}
