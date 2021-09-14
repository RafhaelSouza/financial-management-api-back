package com.studies.financialmanagement.api.repositories.entry;

import com.studies.financialmanagement.api.models.Entry;
import com.studies.financialmanagement.api.models.Entry_;
import com.studies.financialmanagement.api.repositories.filter.EntryFilter;
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
import java.util.ArrayList;
import java.util.List;

public class EntryRepositoryImpl implements EntryRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

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

    private void addPaginationConstraints(TypedQuery<Entry> query, Pageable pageable) {
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
