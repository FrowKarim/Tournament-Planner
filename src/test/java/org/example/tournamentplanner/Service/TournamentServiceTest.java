package org.example.tournamentplanner.Service;

import org.example.tournamentplanner.Entities.Player;
import org.example.tournamentplanner.Entities.Team;
import org.example.tournamentplanner.Entities.Tournament;
import org.example.tournamentplanner.Entities.TournamentStatus;
import org.example.tournamentplanner.Repository.playerRepository;
import org.example.tournamentplanner.Repository.teamRepository;
import org.example.tournamentplanner.Repository.tournamentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TournamentServiceTest {

    @Mock
    private tournamentRepository tournamentRepository;
    @Mock
    private playerRepository playerRepository;
    @Mock
    private teamRepository teamRepository;
    @Mock
    private Team team;

    @InjectMocks
    private TournamentService tournamentService;

    private Player createPlayer(Long id) {
        return Player.builder()
                .id(id)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();
    }

    private Tournament createTournament() {
        return Tournament.builder()
                .id(1L)
                .name("Summer Cup")
                .maxTeamSize(5)
                .organizer(createPlayer(1L))
                .build();
    }

    // createTournament

    @Test
    void createTournament_setsOrganizerAndSaves() {
        Player organizer = createPlayer(1L);
        Tournament request = Tournament.builder().name("Summer Cup").maxTeamSize(5).build();
        when(playerRepository.findById(1L)).thenReturn(Optional.of(organizer));
        when(tournamentRepository.existsByName("Summer Cup")).thenReturn(false);
        when(tournamentRepository.save(any(Tournament.class))).thenAnswer(i -> i.getArgument(0));

        Tournament result = tournamentService.createTournament(1L, request);

        assertEquals(organizer, result.getOrganizer());
        assertEquals("Summer Cup", result.getName());
        verify(tournamentRepository).save(any(Tournament.class));
    }

    @Test
    void createTournament_whenPlayerNotFound_throwsException() {
        Tournament request = Tournament.builder().name("Summer Cup").build();
        when(playerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> tournamentService.createTournament(99L, request));
        verify(tournamentRepository, never()).save(any(Tournament.class));
    }

    @Test
    void createTournament_whenNameExists_throwsException() {
        Tournament request = Tournament.builder().name("Summer Cup").maxTeamSize(5).build();
        when(playerRepository.findById(1L)).thenReturn(Optional.of(createPlayer(1L)));
        when(tournamentRepository.existsByName("Summer Cup")).thenReturn(true);

        assertThrows(ResponseStatusException.class,
                () -> tournamentService.createTournament(1L, request));
        verify(tournamentRepository, never()).save(any(Tournament.class));
    }

    // updateTournament

    @Test
    void updateTournament_whenRequesterIsOrganizer_updatesFields() {
        Tournament tournament = createTournament();
        Tournament request = Tournament.builder().name("Winter Cup").game("Chess").maxTeamSize(4).build();
        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(tournament));

        Tournament result = tournamentService.updateTournament(1L, 1L, request);

        assertEquals("Winter Cup", result.getName());
        assertEquals("Chess", result.getGame());
        assertEquals(4, result.getMaxTeamSize());
    }

    @Test
    void updateTournament_whenRequesterIsNotOrganizer_throwsException() {
        Tournament request = Tournament.builder().name("Winter Cup").maxTeamSize(4).build();
        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(createTournament()));

        assertThrows(ResponseStatusException.class,
                () -> tournamentService.updateTournament(1L, 99L, request));
    }

    @Test
    void updateTournament_whenTournamentAlreadyStarted_throwsException() {
        Tournament tournament = createTournament();
        tournament.setStatus(TournamentStatus.STARTED);
        Tournament request = Tournament.builder().name("Winter Cup").maxTeamSize(4).build();
        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(tournament));

        assertThrows(ResponseStatusException.class,
                () -> tournamentService.updateTournament(1L, 1L, request));
    }

    // addTeam

    @Test
    void addTeam_whenValid_assignsTeamToTournament() {
        Tournament tournament = createTournament();
        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(tournament));
        when(teamRepository.findById(2L)).thenReturn(Optional.of(team));
        when(team.getCaptain()).thenReturn(createPlayer(5L));
        when(team.getPlayers()).thenReturn(List.of(createPlayer(5L)));

        Team result = tournamentService.addTeam(1L, 2L, 5L);

        assertEquals(team, result);
        verify(team).setTournament(tournament);
    }

    @Test
    void addTeam_whenRequesterIsNotCaptain_throwsException() {
        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(createTournament()));
        when(teamRepository.findById(2L)).thenReturn(Optional.of(team));
        when(team.getCaptain()).thenReturn(createPlayer(5L));

        assertThrows(ResponseStatusException.class,
                () -> tournamentService.addTeam(1L, 2L, 6L));
    }

    @Test
    void addTeam_whenTournamentAlreadyStarted_throwsException() {
        Tournament tournament = createTournament();
        tournament.setStatus(TournamentStatus.STARTED);
        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(tournament));
        when(teamRepository.findById(2L)).thenReturn(Optional.of(team));
        when(team.getCaptain()).thenReturn(createPlayer(5L));

        assertThrows(ResponseStatusException.class,
                () -> tournamentService.addTeam(1L, 2L, 5L));
    }

    @Test
    void addTeam_whenTeamAlreadyInTournament_throwsException() {
        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(createTournament()));
        when(teamRepository.findById(2L)).thenReturn(Optional.of(team));
        when(team.getCaptain()).thenReturn(createPlayer(5L));
        when(team.getTournament()).thenReturn(createTournament());

        assertThrows(ResponseStatusException.class,
                () -> tournamentService.addTeam(1L, 2L, 5L));
    }

    @Test
    void addTeam_whenTournamentIsFull_throwsException() {
        Tournament tournament = createTournament();
        tournament.setMaxTeams(1);
        tournament.getTeams().add(mock(Team.class));
        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(tournament));
        when(teamRepository.findById(2L)).thenReturn(Optional.of(team));
        when(team.getCaptain()).thenReturn(createPlayer(5L));

        assertThrows(ResponseStatusException.class,
                () -> tournamentService.addTeam(1L, 2L, 5L));
    }

    @Test
    void addTeam_whenTeamIsTooBig_throwsException() {
        Tournament tournament = createTournament();
        tournament.setMaxTeamSize(1);
        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(tournament));
        when(teamRepository.findById(2L)).thenReturn(Optional.of(team));
        when(team.getCaptain()).thenReturn(createPlayer(5L));
        when(team.getPlayers()).thenReturn(List.of(createPlayer(5L), createPlayer(6L)));

        assertThrows(ResponseStatusException.class,
                () -> tournamentService.addTeam(1L, 2L, 5L));
    }

    @Test
    void addTeam_whenTeamNotFound_throwsException() {
        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(createTournament()));
        when(teamRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> tournamentService.addTeam(1L, 99L, 5L));
    }

    // removeTeam

    @Test
    void removeTeam_whenRequesterIsOrganizer_removesTeamFromTournament() {
        Tournament tournament = createTournament();
        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(tournament));
        when(teamRepository.findById(2L)).thenReturn(Optional.of(team));
        when(team.getTournament()).thenReturn(tournament);

        tournamentService.removeTeam(1L, 2L, 1L);

        verify(team).setTournament(null);
    }

    @Test
    void removeTeam_whenRequesterIsNotOrganizer_throwsException() {
        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(createTournament()));

        assertThrows(ResponseStatusException.class,
                () -> tournamentService.removeTeam(1L, 2L, 99L));
        verify(team, never()).setTournament(null);
    }

    @Test
    void removeTeam_whenTeamIsNotInTournament_throwsException() {
        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(createTournament()));
        when(teamRepository.findById(2L)).thenReturn(Optional.of(team));

        assertThrows(ResponseStatusException.class,
                () -> tournamentService.removeTeam(1L, 2L, 1L));
        verify(team, never()).setTournament(null);
    }

    // getTournament

    @Test
    void getTournament_whenExists_returnsTournament() {
        Tournament tournament = createTournament();
        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(tournament));

        Tournament result = tournamentService.getTournament(1L);

        assertEquals(tournament, result);
    }

    @Test
    void getTournament_whenNotFound_throwsException() {
        when(tournamentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> tournamentService.getTournament(99L));
    }
}