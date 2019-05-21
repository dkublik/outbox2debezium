package pl.dk.outboxeldemo.email;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Getter
public class EmailSender {

    private final List<Email> sentEmails = new ArrayList<>();

    // imitating communication with an external email-service
    public void send(Email email) {
        sentEmails.add(email);
    }

    public void clear() {
        sentEmails.clear();
    }
}
