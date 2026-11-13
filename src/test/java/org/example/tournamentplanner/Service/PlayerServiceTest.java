package org.example.tournamentplanner.Service;

import org.example.tournamentplanner.Entities.Player;
import org.example.tournamentplanner.Repository.IPlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    private IPlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    private Player createPlayer(Long id) {
        return Player.builder()
                .id(id)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();
    }

    @Test
    void getAllPlayers_returnsAllPlayers() {
        List<Player> players = List.of(createPlayer(1L), createPlayer(2L));
        when(playerRepository.findAll()).thenReturn(players);

        List<Player> result = playerService.getAllPlayers();

        assertEquals(2, result.size());
        assertEquals(players, result);
    }

    @Test
    void getPlayerById_whenPlayerExists_returnsPlayer() {
        Player player = createPlayer(1L);
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));

        Player result = playerService.getPlayerById(1L);

        assertEquals(player, result);
    }

    @Test
    void getPlayerById_whenPlayerDoesNotExist_returnsNull() {
        when(playerRepository.findById(99L)).thenReturn(Optional.empty());

        Player result = playerService.getPlayerById(99L);

        assertNull(result);
    }

    @Test
    void save_returnsSavedPlayer() {
        Player player = createPlayer(1L);
        when(playerRepository.save(player)).thenReturn(player);

        Player result = playerService.save(player);

        assertEquals(player, result);
        verify(playerRepository).save(player);
    }

    @Test
    void deletePlayer_callsDeleteById() {
        playerService.deletePlayer(1L);

        verify(playerRepository).deleteById(1L);
    }
}
