package com.example.vending_machine;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendingMachineService
{
    private final VendingMachine vendingMachine = new VendingMachine();

    public void initialise( List<Coin> initialFloat )
    {
        vendingMachine.initialise( initialFloat );
    }

    public void deposit( List<Coin> depositCoinList )
    {
        vendingMachine.deposit( depositCoinList );
    }

    public List<Coin> getChange( int amount )
    {
        return vendingMachine.getChange( amount );
    }

    public List<Coin> getCoins()
    {
        return vendingMachine.getCoins();
    }
}
