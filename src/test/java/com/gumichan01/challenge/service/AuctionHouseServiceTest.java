package com.gumichan01.challenge.service;

import com.gumichan01.challenge.domain.AuctionHouse;
import com.gumichan01.challenge.persistence.AuctionHouseRepository;
import com.gumichan01.challenge.service.exception.AlreadyRegisteredException;
import com.gumichan01.challenge.service.exception.BadRequestException;
import com.gumichan01.challenge.service.exception.ResourceNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuctionHouseServiceTest {

    @Mock
    private AuctionHouseRepository repositoryMock;

    @InjectMocks
    private AuctionHouseService service;

    @Test
    public void shouldRetrieveNotAuctionHouseFromEmptyRepository() {
        when(repositoryMock.findAll()).thenReturn(new ArrayList<>());
        assertThat(service.retrieveAllAuctionHouses()).isEmpty();
    }

    @Test
    public void shouldReturnRegisteredAuctionHouse() {
        final AuctionHouse auctionHouse = new AuctionHouse("mock house");
        auctionHouse.setId(1L);
        when(repositoryMock.save(auctionHouse)).thenReturn(auctionHouse);
        assertThat(service.registerAuctionHouse(auctionHouse)).isEqualTo(auctionHouse);
    }

    @Test(expected = AlreadyRegisteredException.class)
    public void shouldNotRegisterTheAuctionHouseTwice() {
        final AuctionHouse auctionHouse = new AuctionHouse("another mock house");
        auctionHouse.setId(1L);
        when(repositoryMock.findByName(auctionHouse.getName())).thenReturn(null);
        service.registerAuctionHouse(auctionHouse);
        when(repositoryMock.findByName(auctionHouse.getName())).thenReturn(auctionHouse);
        service.registerAuctionHouse(auctionHouse);
    }

    @Test
    public void shouldReturnNothingWhenTheRegisteredHouseIsDeleted() {
        final AuctionHouse auctionHouse = new AuctionHouse("mock house to delete");
        final Optional<AuctionHouse> optionalHouse = Optional.of(auctionHouse);
        auctionHouse.setId(1L);
        when(repositoryMock.findByName(auctionHouse.getName())).thenReturn(null);
        when(repositoryMock.findById(auctionHouse.getId())).thenReturn(optionalHouse);
        service.registerAuctionHouse(auctionHouse);
        service.deleteAuctionHouse(auctionHouse.getId());
    }

    @Test(expected = BadRequestException.class)
    public void shouldThrowBadRequestExceptionWhenTheHouseToDeleteHasNoId() {
        final AuctionHouse auctionHouse = new AuctionHouse("mock house to delete");
        auctionHouse.setId(null);
        // No need to register the house first. The service must not perform the deletion
        service.deleteAuctionHouse(auctionHouse.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowResourceNotFoundExceptionWhenTheHouseToDeleteDoesNotExist() {
        service.deleteAuctionHouse(1L);
    }
}