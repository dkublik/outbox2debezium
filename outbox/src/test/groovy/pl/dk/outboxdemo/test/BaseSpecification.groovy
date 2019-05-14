package pl.dk.outboxdemo.test

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionCallback
import org.springframework.transaction.support.TransactionTemplate
import pl.dk.outboxdemo.OutboxDemoApp
import pl.dk.outboxdemo.email.EmailSender
import spock.lang.Specification

@ContextConfiguration(classes = [OutboxDemoApp])
@SpringBootTest
@ActiveProfiles('test')
@Slf4j
abstract class BaseSpecification extends Specification {

    @Autowired
    protected PlatformTransactionManager transactionManager

    @Autowired
    DbCleaner dbCleaner

    @Autowired
    EmailSender emailSender

    def setup() {
        dbCleaner.clearTables()
        emailSender.clear()
    }

    void doInTx(TransactionCallback<?> callback) {
        TransactionTemplate tt = new TransactionTemplate(transactionManager)
        tt.execute(callback)
    }

    void logException(Runnable runnable) {
        try {
            runnable.run()
        } catch(Exception e) {
            log.error('', e)
        }
    }
}
