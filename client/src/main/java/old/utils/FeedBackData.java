package old.utils;

import java.util.List;
import java.util.UUID;

public class FeedBackData {
    private UUID uuid;
    private long date;
    private String author;
    private String subject;
    private String content;
    private List<String> attachments; //TODO File in Base64+
    private FeedBackType feedBackType;
    private FeedBackState feedBackState;
    private List<FeedBackAnswer> feedBackAnswers;
}
