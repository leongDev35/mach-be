package com.leong.mach.mangaApp.creater.creater;

import jakarta.validation.constraints.*;

public record CreaterRequest(
        @NotNull(message = "Name is mandatory") String name) {
}