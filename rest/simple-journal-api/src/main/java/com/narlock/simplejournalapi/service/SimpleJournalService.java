package com.narlock.simplejournalapi.service;

import com.narlock.simplejournalapi.model.JournalEntry;
import com.narlock.simplejournalapi.model.error.NotFoundException;
import com.narlock.simplejournalapi.repository.JournalEntryRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleJournalService {

    private final JournalEntryRepository journalEntryRepository;

    public JournalEntry saveNewJournalEntry(JournalEntry journalEntry) {
        journalEntryRepository.saveNewEntry(journalEntry.getDate(), journalEntry.getProfileId(), journalEntry.getBox1(), journalEntry.getBox2(), journalEntry.getBox3(), journalEntry.getBox4(), journalEntry.getMood());
        Optional<JournalEntry> journalEntryOptional = journalEntryRepository.findById(journalEntry.getDate());
        if(journalEntryOptional.isPresent()) {
            return journalEntryOptional.get();
        }

        throw new RuntimeException("Unexpected error occurred when trying to retrieve newly created journal entry");
    }

    public JournalEntry getJournalEntryByDateProfile(Integer profileId, LocalDate date) {
        List<JournalEntry> journalEntryList = journalEntryRepository.findByProfileIdAndDate(profileId, date);
        if (journalEntryList.size() > 1) {
            throw new RuntimeException(
                    "Unexpected result from backend - more then one result for water entry on " + date);
        }

        if (!journalEntryList.isEmpty()) {
            return journalEntryList.get(0);
        }

        throw new NotFoundException("No entry found on " + date);
    }

    public JournalEntry updateJournalEntry(JournalEntry journalEntry) {
        return journalEntryRepository.save(journalEntry);
    }

    public void deleteJournalEntry(LocalDate date, Integer profileId) {
        journalEntryRepository.deleteByProfileIdAndDate(profileId, date);
    }
}
