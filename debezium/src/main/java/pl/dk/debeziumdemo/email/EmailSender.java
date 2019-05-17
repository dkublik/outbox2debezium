package pl.dk.debeziumdemo.email;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Getter
public class EmailSender {

    static final int BATCH_SIZE = 10;

    private final List<Email> sentEmails = new ArrayList<>();

    public void sendEmail(UUID orderId) {
        sentEmails.add(new Email("received order: " + orderId));
    }

    public void clear() {
        sentEmails.clear();
    }
}
