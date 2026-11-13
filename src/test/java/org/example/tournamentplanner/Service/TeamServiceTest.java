package org.example.tournamentplanner.Service;

import org.example.tournamentplanner.Entities.Player;
import org.example.tournamentplanner.Entities.Team;
import org.example.tournamentplanner.Entities.Tournament;
import org.example.tournamentplanner.Repository.IPlayerRepository;
import org.example.tournamentplanner.Repository.ITeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private ITeamRepository teamRepository;

    @Mock
    private IPlayerRepository playerRepository;

    @InjectMocks
    private TeamService teamService;

    private Player createPlayer(Long id) {
        return Player.builder()
                .id(id)
                .firstName("John")
                .lastName("Doe")
                .email("player" + id + "@example.com")
                .build();
    }

    private Team createTeamWithCaptain(Player captain) {
        Team team = Team.builder()
                .id(10L)
                .name("Team A")
                .captain(captain)
                .build();
        team.getPlayers().add(captain);
        return team;
    }

    // createTeam

    @Test
    void createTeam_setsCaptainAddsThemToPlayersAndSaves() {
        Player captain = createPlayer(1L);
        Team team = Team.builder().name("Team A").build();
        when(playerRepository.findById(1L)).thenReturn(Optional.of(captain));
        when(teamRepository.save(team)).thenReturn(team);

        Team result = teamService.createTeam(1L, team);

        assertEquals(captain, result.getCaptain());
        assertTrue(result.getPlayers().contains(captain));
        verify(teamRepository).save(team);
    }

    @Test
    void createTeam_whenPlayerNotFound_throwsException() {
        Team team = Team.builder().name("Team A").build();
        when(playerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> teamService.createTeam(99L, team));
    }

    // addPlayer

    @Test
    void addPlayer_whenValid_addsPlayerToTeam() {
        Player captain = createPlayer(1L);
        Player newPlayer = createPlayer(2L);
        Team team = createTeamWithCaptain(captain);
        when(teamRepository.findById(10L)).thenReturn(Optional.of(team));
        when(playerRepository.findById(2L)).thenReturn(Optional.of(newPlayer));

        Team result = teamService.addPlayer(10L, 2L, 1L);

        assertEquals(2, result.getPlayers().size());
        assertTrue(result.getPlayers().contains(newPlayer));
    }

    @Test
    void addPlayer_whenTournamentHasRoom_addsPlayerToTeam() {
        Player captain = createPlayer(1L);
        Player newPlayer = createPlayer(2L);
        Team team = createTeamWithCaptain(captain);
        team.setTournament(Tournament.builder().maxTeamSize(5).build());
        when(teamRepository.findById(10L)).thenReturn(Optional.of(team));
        when(playerRepository.findById(2L)).thenReturn(Optional.of(newPlayer));

        Team result = teamService.addPlayer(10L, 2L, 1L);

        assertEquals(2, result.getPlayers().size());
    }

    @Test
    void addPlayer_whenRequesterIsNotCaptain_throwsException() {
        Team team = createTeamWithCaptain(createPlayer(1L));
        when(teamRepository.findById(10L)).thenReturn(Optional.of(team));

        assertThrows(IllegalStateException.class,
                () -> teamService.addPlayer(10L, 2L, 99L));
    }

    @Test
    void addPlayer_whenPlayerAlreadyInTeam_throwsException() {
        Team team = createTeamWithCaptain(createPlayer(1L));
        when(teamRepository.findById(10L)).thenReturn(Optional.of(team));

        assertThrows(IllegalStateException.class,
                () -> teamService.addPlayer(10L, 1L, 1L));
    }

    @Test
    void addPlayer_whenTeamIsFullForTournament_throwsException() {
        Team team = createTeamWithCaptain(createPlayer(1L));
        team.setTournament(Tournament.builder().maxTeamSize(1).build());
        when(teamRepository.findById(10L)).thenReturn(Optional.of(team));

        assertThrows(IllegalStateException.class,
                () -> teamService.addPlayer(10L, 2L, 1L));
    }

    @Test
    void addPlayer_whenPlayerNotFound_throwsException() {
        Team team = createTeamWithCaptain(createPlayer(1L));
        when(teamRepository.findById(10L)).thenReturn(Optional.of(team));
        when(playerRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> teamService.addPlayer(10L, 2L, 1L));
    }

    @Test
    void addPlayer_whenTeamNotFound_throwsException() {
        when(teamRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> teamService.addPlayer(99L, 2L, 1L));
    }

    // removePlayer

    @Test
    void removePlayer_whenValid_removesPlayerFromTeam() {
        Player captain = createPlayer(1L);
        Player otherPlayer = createPlayer(2L);
        Team team = createTeamWithCaptain(captain);
        team.getPlayers().add(otherPlayer);
        when(teamRepository.findById(10L)).thenReturn(Optional.of(team));

        Team result = teamService.removePlayer(10L, 2L, 1L);

        assertEquals(1, result.getPlayers().size());
        assertTrue(result.getPlayers().contains(captain));
    }

    @Test
    void removePlayer_whenRequesterIsNotCaptain_throwsException() {
        Team team = createTeamWithCaptain(createPlayer(1L));
        when(teamRepository.findById(10L)).thenReturn(Optional.of(team));

        assertThrows(IllegalStateException.class,
                () -> teamService.removePlayer(10L, 2L, 99L));
    }

    @Test
    void removePlayer_whenRemovingCaptain_throwsException() {
        Team team = createTeamWithCaptain(createPlayer(1L));
        when(teamRepository.findById(10L)).thenReturn(Optional.of(team));

        assertThrows(IllegalStateException.class,
                () -> teamService.removePlayer(10L, 1L, 1L));
    }

    // getTeam

    @Test
    void getTeam_whenExists_returnsTeam() {
        Team team = createTeamWithCaptain(createPlayer(1L));
        when(teamRepository.findById(10L)).thenReturn(Optional.of(team));

        Team result = teamService.getTeam(10L);

        assertSame(team, result);
    }

    @Test
    void getTeam_whenNotFound_throwsException() {
        when(teamRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> teamService.getTeam(99L));
    }
}