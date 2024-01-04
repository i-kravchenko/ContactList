package com.example.ContactList.repository;

import com.example.ContactList.exception.ContactNotFoundException;
import com.example.ContactList.model.Contact;
import com.example.demo.jooq.db.Tables;
import com.example.demo.jooq.db.tables.records.ContactRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class ContactRepository
{
    private final DSLContext context;

    public List<Contact> findAll() {
        return context.selectFrom(Tables.CONTACT)
                .fetchInto(Contact.class);
    }

    public Contact findById(Long id) {
        return Objects.requireNonNull(context.selectFrom(Tables.CONTACT)
                        .where(Tables.CONTACT.ID.eq(id))
                        .fetchOptional()).map(record -> record.into(Contact.class))
                .orElseThrow(() -> new ContactNotFoundException("Contact not found"));
    }

    public Contact save(Contact contact) {
        ContactRecord record = context.newRecord(Tables.CONTACT, contact);
        record.store();
        return record.into(Contact.class);
    }

    public Contact update(Contact contact) {
        var record = context.update(Tables.CONTACT)
                .set(context.newRecord(Tables.CONTACT, contact))
                .where(Tables.CONTACT.ID.eq(contact.getId()))
                .returning()
                .fetchOptional();
        return record.map(r -> r.into(Contact.class))
                .orElseThrow(() -> new ContactNotFoundException("Contact not found"));
    }

    public void delete(Long id) {
        context.deleteFrom(Tables.CONTACT)
                .where(Tables.CONTACT.ID.eq(id))
                .execute();
    }
}
