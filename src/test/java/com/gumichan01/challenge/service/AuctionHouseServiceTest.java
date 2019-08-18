package com.gumichan01.challenge.service;

import com.gumichan01.challenge.domain.AuctionHouse;
import com.gumichan01.challenge.persistence.AuctionHouseRepository;
import com.gumichan01.challenge.persistence.AuctionRepository;
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
    private AuctionRepository auctionRepositoryMock;

    @Mock
    private AuctionHouseRepository auctionHouseRepositoryMock;

    @InjectMocks
    private AuctionHouseService auctionHouseService;

    @Test
    public void shouldRetrieveNotAuctionHouseFromEmptyRepository() {
        when(auctionHouseRepositoryMock.findAll()).thenReturn(new ArrayList<>());
        assertThat(auctionHouseService.retrieveAllAuctionHouses()).isEmpty();
    }

    @Test
    public void shouldReturnRegisteredAuctionHouse() {
        final AuctionHouse auctionHouse = new AuctionHouse("mock house");
        when(auctionHouseRepositoryMock.save(auctionHouse)).thenReturn(auctionHouse);
        assertThat(auctionHouseService.registerAuctionHouse(auctionHouse)).isEqualTo(auctionHouse);
    }

    @Test(expected = AlreadyRegisteredException.class)
    public void shouldNotRegisterTheAuctionHouseTwice() {
        final AuctionHouse auctionHouse = new AuctionHouse("another mock house");
        when(auctionHouseRepositoryMock.findByName(auctionHouse.getName())).thenReturn(null);
        auctionHouseService.registerAuctionHouse(auctionHouse);
        when(auctionHouseRepositoryMock.findByName(auctionHouse.getName())).thenReturn(auctionHouse);
        auctionHouseService.registerAuctionHouse(auctionHouse);
    }

    @Test
    public void shouldNotFailWhenTheRegisteredHouseIsDeleted() {
        final AuctionHouse auctionHouse = new AuctionHouse("mock house to delete");
        final Optional<AuctionHouse> optionalHouse = Optional.of(auctionHouse);
        auctionHouse.setId(1L);
        when(auctionRepositoryMock.findAllByAuctionHouseId(auctionHouse.getId())).thenReturn(new ArrayList<>());
        when(auctionHouseRepositoryMock.findByName(auctionHouse.getName())).thenReturn(null);
        when(auctionHouseRepositoryMock.findById(auctionHouse.getId())).thenReturn(optionalHouse);
        auctionHouseService.registerAuctionHouse(auctionHouse);
        auctionHouseService.deleteAuctionHouse(auctionHouse.getId());
    }

    @Test(expected = BadRequestException.class)
    public void shouldThrowBadRequestExceptionWhenTheHouseToDeleteHasNoId() {
        final AuctionHouse auctionHouse = new AuctionHouse("mock house to delete");
        auctionHouse.setId(null);
        // No need to register the house first. The service must not perform the deletion
        auctionHouseService.deleteAuctionHouse(auctionHouse.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowResourceNotFoundExceptionWhenTheHouseToDeleteDoesNotExist() {
        auctionHouseService.deleteAuctionHouse(1L);
    }
}