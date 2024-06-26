package com.example.vending_machine;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class VendingMachineControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testInitialise() throws Exception
    {
        String json = "[{\"denomination\": 1, \"count\": 10}, {\"denomination\": 2, \"count\": 5}, {\"denomination\": 5, \"count\": 2}]";
        mockMvc.perform( MockMvcRequestBuilders.post( "/vendingMachine/initialise" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( json ) )
                .andExpect( status().isOk() )
                .andExpect( content().string( "Vending machine initialised" ) );
    }

    @Test
    void testDeposit() throws Exception
    {
        String initialiseJson = "[{\"denomination\": 1, \"count\": 10}, {\"denomination\": 2, \"count\": 5}, {\"denomination\": 5, \"count\": 2}]";
        mockMvc.perform( MockMvcRequestBuilders.post( "/vendingMachine/initialise" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( initialiseJson ) )
                .andExpect( status().isOk() )
                .andExpect( content().string( "Vending machine initialised" ) );

        String depositJson = "[{\"denomination\": 1, \"count\": 5}, {\"denomination\": 2, \"count\": 3}]";
        mockMvc.perform( MockMvcRequestBuilders.post( "/vendingMachine/deposit" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( depositJson ) )
                .andExpect( status().isOk() )
                .andExpect( content().string( "Coins deposited" ) );
    }

    @Test
    void testGetChange() throws Exception
    {
        String initialiseJson = "[{\"denomination\": 1, \"count\": 10}, {\"denomination\": 2, \"count\": 5}, {\"denomination\": 5, \"count\": 2}]";
        mockMvc.perform( MockMvcRequestBuilders.post( "/vendingMachine/initialise" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( initialiseJson ) )
                .andExpect( status().isOk() )
                .andExpect( content().string( "Vending machine initialised" ) );

        mockMvc.perform( MockMvcRequestBuilders.post( "/vendingMachine/change?amount=7" ) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$[?(@.denomination == 5)].count" ).value( 1 ) )
                .andExpect( jsonPath( "$[?(@.denomination == 2)].count" ).value( 1 ) );
    }

    @Test
    void testGetCoins() throws Exception
    {
        String initialiseJson = "[{\"denomination\": 1, \"count\": 10}, {\"denomination\": 2, \"count\": 5}, {\"denomination\": 5, \"count\": 2}]";
        mockMvc.perform( MockMvcRequestBuilders.post( "/vendingMachine/initialise" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( initialiseJson ) )
                .andExpect( status().isOk() )
                .andExpect( content().string( "Vending machine initialised" ) );

        mockMvc.perform( MockMvcRequestBuilders.get( "/vendingMachine/coins" ) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$[?(@.denomination == 1)].count" ).value( 10 ) )
                .andExpect( jsonPath( "$[?(@.denomination == 2)].count" ).value( 5 ) )
                .andExpect( jsonPath( "$[?(@.denomination == 5)].count" ).value( 2 ) );
    }
}