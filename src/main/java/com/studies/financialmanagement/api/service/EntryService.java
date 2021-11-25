package com.studies.financialmanagement.api.service;

import com.studies.financialmanagement.api.dto.EntryStatisticsPerson;
import com.studies.financialmanagement.api.mail.Mailer;
import com.studies.financialmanagement.api.models.Entry;
import com.studies.financialmanagement.api.models.Person;
import com.studies.financialmanagement.api.models.Users;
import com.studies.financialmanagement.api.repositories.EntryRepository;
import com.studies.financialmanagement.api.repositories.UsersRepository;
import com.studies.financialmanagement.api.service.exception.InactiveOrInexistentPersonException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Service
public class EntryService {

    private static final String RECIPIENTS = "ROLE_SEARCH_ENTRY";

    @Autowired
    private PersonService personService;

    @Autowired
    private EntryRepository repository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private Mailer mailer;

    @Scheduled(cron = "0 0 6 * * *")
    public void warnDueEntries() {
        List<Entry> dues = repository
                .findByDueDateLessThanEqualAndPaymentDateIsNull(LocalDate.now());

        List<Users> recipients = usersRepository
                .findByPermissionsDescription(RECIPIENTS);

        mailer.warnDueEntries(dues, recipients);
    }

    public byte[] reportByPerson(LocalDate begin, LocalDate end) throws Exception {
        List<EntryStatisticsPerson> data = repository.byPerson(begin, end);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("BEGIN_DATE", Date.valueOf(begin));
        parametros.put("END_DATE", Date.valueOf(end));
        parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));

        InputStream inputStream = this.getClass().getResourceAsStream(
                "/reports/entries-by-person.jasper");

        JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros,
                new JRBeanCollectionDataSource(data));

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    public Entry save(Entry entry) {
        personValidate(entry);

        return repository.save(entry);
    }

    public Entry update(Long id, Entry entry) {
        Entry savedEntry = searchExistentEntry(id);
        if (!entry.getPerson().equals(savedEntry.getPerson()))
            personValidate(entry);

        BeanUtils.copyProperties(entry, savedEntry, "id");

        return repository.save(savedEntry);
    }

    private void personValidate(Entry entry) {
        Person person = null;

        if (entry.getPerson().getId() != null)
            person = personService.getPersonByIdOrReturnNull(entry.getPerson().getId());

        if (person == null || person.isInactive())
            throw new InactiveOrInexistentPersonException();
    }

    private Entry searchExistentEntry(Long id) {
        Optional<Entry> savedEntry = repository.findById(id);

        if (savedEntry.isEmpty())
            throw new IllegalArgumentException();

        return savedEntry.get();
    }
}
