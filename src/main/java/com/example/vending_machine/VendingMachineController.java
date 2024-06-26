package com.example.vending_machine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/vendingMachine" )
public class VendingMachineController
{
    @Autowired
    private VendingMachineService vendingMachineService;

    @PostMapping( "/initialise" )
    public ResponseEntity<String> initialise( @RequestBody List<Coin> initialFloat )
    {
        vendingMachineService.initialise( initialFloat );
        return ResponseEntity.ok( "Vending machine initialised" );
    }

    @PostMapping( "/deposit" )
    public ResponseEntity<String> deposit( @RequestBody List<Coin> depositCoinList )
    {
        vendingMachineService.deposit( depositCoinList );
        return ResponseEntity.ok( "Coins deposited" );
    }

    @PostMapping( "/change" )
    public ResponseEntity<?> getChange( @RequestParam int amount )
    {
        List<Coin> change = vendingMachineService.getChange( amount );
        if( change.isEmpty() )
            return ResponseEntity.status( HttpStatus.CONFLICT ).body( "Insufficient coins to provide change for the requested amount" );

        return ResponseEntity.ok( change );
    }

    @GetMapping( "/coins" )
    public ResponseEntity<List<Coin>> getCoins()
    {
        return ResponseEntity.ok( vendingMachineService.getCoins() );
    }
}
