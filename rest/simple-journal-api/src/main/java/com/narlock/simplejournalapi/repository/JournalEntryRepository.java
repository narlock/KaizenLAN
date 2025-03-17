package com.narlock.simplejournalapi.repository;

import com.narlock.simplejournalapi.model.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, LocalDate> {
    @Modifying
    @Transactional
    @Query("INSERT INTO JournalEntry(date, profileId, box1, box2, box3, box4, mood) VALUES (:date, :profileId, :box1, :box2, :box3, :box4, :mood)")
    void saveNewEntry(
            @Param("date") LocalDate date,
            @Param("profileId") Integer profileId,
            @Param("box1") String box1,
            @Param("box2") String box2,
            @Param("box3") String box3,
            @Param("box4") String box4,
            @Param("mood") Integer mood);

    List<JournalEntry> findByProfileIdAndDate(Integer profileId, LocalDate date);

    @Modifying
    @Transactional
    @Query("DELETE FROM JournalEntry j WHERE j.profileId = :profileId AND j.date = :date")
    void deleteByProfileIdAndDate(
            @Param("profileId") Integer profileId,
            @Param("date") LocalDate date);
}
