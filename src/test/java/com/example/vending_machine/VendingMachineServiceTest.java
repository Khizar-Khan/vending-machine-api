package com.example.vending_machine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VendingMachineServiceTest
{
    private VendingMachineService vendingMachineService;

    @BeforeEach
    void setUp()
    {
        vendingMachineService = new VendingMachineService();
    }

    @Test
    void testInitialise()
    {
        List<Coin> initialFloat = new ArrayList<>();
        initialFloat.add( new Coin( 1, 10 ) );
        initialFloat.add( new Coin( 2, 5 ) );
        initialFloat.add( new Coin( 5, 2 ) );

        vendingMachineService.initialise( initialFloat );

        List<Coin> coinList = vendingMachineService.getCoins();
        assertEquals( 10, coinList.stream().filter( c -> c.getDenomination() == 1 ).findFirst().get().getCount() );
        assertEquals( 5, coinList.stream().filter( c -> c.getDenomination() == 2 ).findFirst().get().getCount() );
        assertEquals( 2, coinList.stream().filter( c -> c.getDenomination() == 5 ).findFirst().get().getCount() );
    }

    @Test
    void testDeposit()
    {
        List<Coin> initialFloat = new ArrayList<>();
        initialFloat.add( new Coin( 1, 10 ) );
        vendingMachineService.initialise( initialFloat );

        List<Coin> deposit = new ArrayList<>();
        deposit.add( new Coin( 1, 5 ) );
        deposit.add( new Coin( 2, 3 ) );
        vendingMachineService.deposit( deposit );

        List<Coin> coins = vendingMachineService.getCoins();
        assertEquals( 15, coins.stream().filter( c -> c.getDenomination() == 1 ).findFirst().get().getCount() );
        assertEquals( 3, coins.stream().filter( c -> c.getDenomination() == 2 ).findFirst().get().getCount() );
    }

    @Test
    void testGetChange()
    {
        List<Coin> initialFloat = new ArrayList<>();
        initialFloat.add( new Coin( 1, 10 ) );
        initialFloat.add( new Coin( 2, 5 ) );
        initialFloat.add( new Coin( 5, 2 ) );
        vendingMachineService.initialise( initialFloat );

        List<Coin> change = vendingMachineService.getChange( 7 );
        assertEquals( 1, change.stream().filter( c -> c.getDenomination() == 5 ).findFirst().get().getCount() );
        assertEquals( 1, change.stream().filter( c -> c.getDenomination() == 2 ).findFirst().get().getCount() );

        List<Coin> coins = vendingMachineService.getCoins();
        assertEquals( 10, coins.stream().filter( c -> c.getDenomination() == 1 ).findFirst().get().getCount() );
        assertEquals( 4, coins.stream().filter( c -> c.getDenomination() == 2 ).findFirst().get().getCount() );
        assertEquals( 1, coins.stream().filter( c -> c.getDenomination() == 5 ).findFirst().get().getCount() );
    }

    @Test
    void testInsufficientChange()
    {
        List<Coin> initialFloat = new ArrayList<>();
        initialFloat.add( new Coin( 1, 1 ) );
        initialFloat.add( new Coin( 2, 1 ) );
        vendingMachineService.initialise( initialFloat );

        List<Coin> change = vendingMachineService.getChange( 5 );
        assertTrue( change.isEmpty() );

        List<Coin> coins = vendingMachineService.getCoins();
        assertEquals( 1, coins.stream().filter( c -> c.getDenomination() == 1 ).findFirst().get().getCount() );
        assertEquals( 1, coins.stream().filter( c -> c.getDenomination() == 2 ).findFirst().get().getCount() );
    }
}