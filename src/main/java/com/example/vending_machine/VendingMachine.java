package com.example.vending_machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class VendingMachine
{
    private List<Coin> coinList;

    public VendingMachine()
    {
        this.coinList = new ArrayList<>();
    }

    // Initialize the vending machine with a set of coins
    public void initialise( List<Coin> initialFloat )
    {
        deposit( initialFloat );
    }

    public void deposit( List<Coin> depositCoinList )
    {
        for( Coin depositCoin : depositCoinList )
        {
            Optional<Coin> existingCoin = coinList.stream().filter( c -> c.getDenomination() == depositCoin.getDenomination() ).findFirst();
            if( existingCoin.isPresent() )
                existingCoin.get().addCount( depositCoin.getCount() );
            else
                coinList.add( depositCoin );
        }
    }

    public List<Coin> getChange( int amount )
    {
        List<Coin> changeList = new ArrayList<>();

        List<Coin> tempCoins = new ArrayList<>();
        for( Coin coin : coinList )
            tempCoins.add( new Coin( coin.getDenomination(), coin.getCount() ) );
        tempCoins.sort( ( c1, c2 ) -> Integer.compare( c2.getDenomination(), c1.getDenomination() ) );

        int remainingAmount = amount;
        for( Coin coin : tempCoins )
        {
            int denomination = coin.getDenomination();
            int availableCoins = coin.getCount();

            if( denomination > remainingAmount )
                continue;

            int neededCoins = remainingAmount / denomination;
            int coinsToUse = Math.min( neededCoins, availableCoins );

            if( coinsToUse > 0 )
            {
                changeList.add( new Coin( denomination, coinsToUse ) );
                coin.subtractCount( coinsToUse );
                remainingAmount -= coinsToUse * denomination;
            }

            if( remainingAmount == 0 )
            {
                // If exact change can be given, update the original coins list
                for( Coin changeCoin : changeList )
                {
                    coinList.stream()
                            .filter( c -> c.getDenomination() == changeCoin.getDenomination() )
                            .findFirst()
                            .ifPresent( c -> c.subtractCount( changeCoin.getCount() ) );
                }

                return changeList;
            }
        }

        // If exact change cannot be given, return an empty list without modifying the original coin list
        return Collections.emptyList();
    }

    public List<Coin> getCoins()
    {
        return new ArrayList<>( coinList );
    }
}