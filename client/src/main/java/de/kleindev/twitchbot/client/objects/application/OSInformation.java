package de.kleindev.twitchbot.client.objects.application;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OSInformation {
    private String operatingSystem;
    private String cpu;
    private String ram;
    private String[] lastLogs;
}
