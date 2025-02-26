package com.leong.mach.common;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TimeAgoUtil {

    public static String timeAgo(LocalDateTime releaseDate) {
        ZonedDateTime utcNow = ZonedDateTime.now(ZoneId.of("UTC"));
        Duration duration = Duration.between(releaseDate, utcNow);
        if (duration.isNegative()) {
            return "In the future";
        } else if (duration.toSeconds() < 60) {
            return "a few seconds ago";
        } else if (duration.toMinutes() < 60) {
            return duration.toMinutes() + " minutes ago";
        } else if (duration.toHours() < 24) {
            return duration.toHours() + " hours ago";
        } else if (duration.toDays() < 30) {
            return duration.toDays() + " days ago";
        } else {
            return "More than a month ago";
        }
    }

    public static String timeAgoChapter(LocalDateTime releaseDate) {
        ZonedDateTime utcNow = ZonedDateTime.now(ZoneId.of("UTC"));
        Duration duration = Duration.between(releaseDate, utcNow);
        if (duration.isNegative()) {
            return "In the future";
        } else if (duration.toSeconds() < 60) {
            return "a few seconds ago";
        } else if (duration.toMinutes() < 60) {
            return duration.toMinutes() + " minutes ago";
        } else if (duration.toHours() < 24) {
            return duration.toHours() + " hours ago";
        } else if (duration.toDays() < 30) {
            return duration.toDays() + " days ago";
        }

        // Nếu trên 30 ngày, tính theo tháng và năm
        long months = ChronoUnit.MONTHS.between(releaseDate, utcNow);
        if (months < 12) {
            return months + " months ago";
        }

        long years = ChronoUnit.YEARS.between(releaseDate, utcNow);
        return years + " years ago";
    }
}
