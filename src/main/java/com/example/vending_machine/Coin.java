package com.example.vending_machine;

public class Coin
{
    private int denomination;
    private int count;

    public Coin( int denomination, int count )
    {
        this.denomination = denomination;
        this.count = count;
    }

    public int getDenomination()
    {
        return denomination;
    }

    public void setDenomination( int denomination )
    {
        this.denomination = denomination;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount( int count )
    {
        this.count = count;
    }

    public void addCount( int count )
    {
        this.count += count;
    }

    public void subtractCount( int count )
    {
        this.count -= count;
    }
}
