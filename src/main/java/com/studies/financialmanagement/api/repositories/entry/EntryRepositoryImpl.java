package com.studies.financialmanagement.api.repositories.entry;

import com.studies.financialmanagement.api.dto.EntryStatisticsCategory;
import com.studies.financialmanagement.api.dto.EntryStatisticsDay;
import com.studies.financialmanagement.api.dto.EntryStatisticsPerson;
import com.studies.financialmanagement.api.models.Category_;
import com.studies.financialmanagement.api.models.Entry;
import com.studies.financialmanagement.api.models.Entry_;
import com.studies.financialmanagement.api.models.Person_;
import com.studies.financialmanagement.api.repositories.filter.EntryFilter;
import com.studies.financialmanagement.api.repositories.projections.EntrySummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EntryRepositoryImpl implements EntryRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<EntryStatisticsPerson> byPerson(LocalDate begin, LocalDate end) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();

        CriteriaQuery<EntryStatisticsPerson> criteriaQuery = criteriaBuilder.
                createQuery(EntryStatisticsPerson.class);

        Root<Entry> root = criteriaQuery.from(Entry.class);

        criteriaQuery.select(criteriaBuilder.construct(EntryStatisticsPerson.class,
                root.get(Entry_.entryType),
                root.get(Entry_.person),
                criteriaBuilder.sum(root.get(Entry_.price))));

        criteriaQuery.where(
                criteriaBuilder.greaterThanOrEqualTo(root.get(Entry_.dueDate),
                        begin),
                criteriaBuilder.lessThanOrEqualTo(root.get(Entry_.dueDate),
                        end));

        criteriaQuery.groupBy(root.get(Entry_.entryType),
                root.get(Entry_.person));

        TypedQuery<EntryStatisticsPerson> typedQuery = manager
                .createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }

    @Override
    public List<EntryStatisticsDay> byDay(LocalDate referenceMonth) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();

        CriteriaQuery<EntryStatisticsDay> criteriaQuery = criteriaBuilder.
                createQuery(EntryStatisticsDay.class);

        Root<Entry> root = criteriaQuery.from(Entry.class);

        criteriaQuery.select(criteriaBuilder.construct(EntryStatisticsDay.class,
                root.get(Entry_.entryType),
                root.get(Entry_.dueDate),
                criteriaBuilder.sum(root.get(Entry_.price))));

        LocalDate firstDay = referenceMonth.withDayOfMonth(1);
        LocalDate lastDay = referenceMonth.withDayOfMonth(referenceMonth.lengthOfMonth());

        criteriaQuery.where(
                criteriaBuilder.greaterThanOrEqualTo(root.get(Entry_.dueDate),
                        firstDay),
                criteriaBuilder.lessThanOrEqualTo(root.get(Entry_.dueDate),
                        lastDay));

        criteriaQuery.groupBy(root.get(Entry_.entryType),
                root.get(Entry_.dueDate));

        TypedQuery<EntryStatisticsDay> typedQuery = manager
                .createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }

    @Override
    public List<EntryStatisticsCategory> byCategory(LocalDate referenceMonth) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();

        CriteriaQuery<EntryStatisticsCategory> criteriaQuery = criteriaBuilder.
                createQuery(EntryStatisticsCategory.class);

        Root<Entry> root = criteriaQuery.from(Entry.class);

        criteriaQuery.select(criteriaBuilder.construct(EntryStatisticsCategory.class,
                root.get(Entry_.category),
                criteriaBuilder.sum(root.get(Entry_.price))));

        LocalDate firstDay = referenceMonth.withDayOfMonth(1);
        LocalDate lastDay = referenceMonth.withDayOfMonth(referenceMonth.lengthOfMonth());

        criteriaQuery.where(
                criteriaBuilder.greaterThanOrEqualTo(root.get(Entry_.dueDate),
                        firstDay),
                criteriaBuilder.lessThanOrEqualTo(root.get(Entry_.dueDate),
                        lastDay));

        criteriaQuery.groupBy(root.get(Entry_.category));

        TypedQuery<EntryStatisticsCategory> typedQuery = manager
                .createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }

    @Override
    public Page<Entry> toFilter(EntryFilter entryFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Entry> criteria = builder.createQuery(Entry.class);
        Root<Entry> root = criteria.from(Entry.class);

        Predicate[] predicates = toCreateRestrictions(entryFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Entry> query = manager.createQuery(criteria);

        addPaginationConstraints(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, totalPages(entryFilter));
    }

    @Override
    public Page<EntrySummary> toSummary(EntryFilter entryFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<EntrySummary> criteria = builder.createQuery(EntrySummary.class);
        Root<Entry> root = criteria.from(Entry.class);

        criteria.select(builder.construct(EntrySummary.class,
            root.get(Entry_.id), root.get(Entry_.description),
            root.get(Entry_.dueDate), root.get(Entry_.paymentDate),
            root.get(Entry_.price), root.get(Entry_.entryType),
            root.get(Entry_.category).get(Category_.NAME),
            root.get(Entry_.person).get(Person_.NAME)
        ));

        Predicate[] predicates = toCreateRestrictions(entryFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<EntrySummary> query = manager.createQuery(criteria);

        addPaginationConstraints(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, totalPages(entryFilter));
    }

    private Predicate[] toCreateRestrictions(EntryFilter entryFilter, CriteriaBuilder builder, Root<Entry> root) {

        List<Predicate> predicates = new ArrayList<>();

        if (entryFilter.getDescription() != null)
            predicates.add(builder.like(
                    builder.lower(root.get(Entry_.description)), "%" + entryFilter.getDescription().toLowerCase() + "%"));

        if (entryFilter.getDueDateFrom() != null)
            predicates.add(builder.greaterThanOrEqualTo(
                    root.get(Entry_.dueDate), entryFilter.getDueDateFrom()));

        if (entryFilter.getDueDateTo() != null)
            predicates.add(builder.lessThanOrEqualTo(
                    root.get(Entry_.dueDate), entryFilter.getDueDateTo()));

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private void addPaginationConstraints(TypedQuery<?> query, Pageable pageable) {
        int currentPage = pageable.getPageNumber();
        int totalOfRegisterPerPage = pageable.getPageSize();
        int firstPageRegister = currentPage * totalOfRegisterPerPage;

        query.setFirstResult(firstPageRegister);
        query.setMaxResults(totalOfRegisterPerPage);
    }

    private Long totalPages(EntryFilter entryFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Entry> root = criteria.from(Entry.class);

        Predicate[] predicates = toCreateRestrictions(entryFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));

        return manager.createQuery(criteria).getSingleResult();
    }

}
