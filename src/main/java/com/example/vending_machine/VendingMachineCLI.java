package com.example.vending_machine;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class VendingMachineCLI
{
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final String BASE_URL = "http://localhost:8080/vendingMachine";

    // ANSI escape codes for colored text
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_RED = "\u001B[31m";

    private static final String[] MENU_OPTIONS = {
            "Vending Machine CLI",
            "1. Initialize Vending Machine",
            "2. Deposit Coins",
            "3. Get Change",
            "4. View Coins",
            "5. Exit"
    };

    public static void main( String[] args )
    {
        Scanner scanner = new Scanner( System.in );

        boolean running = true;
        while( running )
        {
            displayMenu();
            System.out.print( ANSI_CYAN + "Choose an option: " + ANSI_RESET );
            int choice = scanner.nextInt();
            scanner.nextLine();

            try
            {
                switch( choice )
                {
                    case 1 -> initializeVendingMachine( scanner );
                    case 2 -> depositCoins( scanner );
                    case 3 -> getChange( scanner );
                    case 4 -> viewCoins();
                    case 5 -> running = false;
                    default -> System.out.println( ANSI_RED + "Invalid option. Please try again." + ANSI_RESET );
                }
            } catch( IOException | InterruptedException e )
            {
                System.err.println( ANSI_RED + "An error occurred: " + e.getMessage() + ANSI_RESET );
            }
        }
    }

    private static void displayMenu()
    {
        for( String option : MENU_OPTIONS )
            System.out.println( option );
    }

    private static void initializeVendingMachine( Scanner scanner ) throws IOException, InterruptedException
    {
        String initialFloat = collectCoinData( scanner );
        String response = sendHttpRequest( "/initialise", "POST", initialFloat );
        System.out.println( ANSI_GREEN + "Response: " + response + ANSI_RESET );
    }

    private static void depositCoins( Scanner scanner ) throws IOException, InterruptedException
    {
        String depositCoins = collectCoinData( scanner );
        String response = sendHttpRequest( "/deposit", "POST", depositCoins );
        System.out.println( ANSI_GREEN + "Response: " + response + ANSI_RESET );
    }

    private static void getChange( Scanner scanner ) throws IOException, InterruptedException
    {
        System.out.print( ANSI_CYAN + "Enter amount to get change for: " + ANSI_RESET );
        int amount = scanner.nextInt();
        String response = sendHttpRequest( "/change?amount=" + amount, "POST", null );
        System.out.println( ANSI_GREEN + "Response: " + response + ANSI_RESET );
    }

    private static void viewCoins() throws IOException, InterruptedException
    {
        String response = sendHttpRequest( "/coins", "GET", null );
        System.out.println( ANSI_GREEN + "Coins in the vending machine: " + response + ANSI_RESET );
    }

    private static String collectCoinData( Scanner scanner )
    {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append( "[" );

        while( true )
        {
            System.out.print( ANSI_CYAN + "Enter denomination (or 'done' to finish): " + ANSI_RESET );
            String denominationInput = scanner.nextLine();
            if( denominationInput.equalsIgnoreCase( "done" ) )
                break;
            int denomination = parseIntWithValidation( scanner, denominationInput, "Invalid denomination. Please enter a number: " );

            System.out.print( ANSI_CYAN + "Enter count: " + ANSI_RESET );
            String countInput = scanner.nextLine();
            int count = parseIntWithValidation( scanner, countInput, "Invalid count. Please enter a number: " );

            if( jsonBuilder.length() > 1 )
                jsonBuilder.append( "," );
            jsonBuilder.append( "{\"denomination\":" ).append( denomination ).append( ",\"count\":" ).append( count ).append( "}" );
        }

        jsonBuilder.append( "]" );
        return jsonBuilder.toString();
    }

    private static int parseIntWithValidation( Scanner scanner, String input, String errorMessage )
    {
        while( true )
        {
            try
            {
                return Integer.parseInt( input );
            } catch( NumberFormatException e )
            {
                System.out.print( ANSI_RED + errorMessage + ANSI_RESET );
                input = scanner.nextLine();
            }
        }
    }

    private static String sendHttpRequest( String path, String method, String body ) throws IOException, InterruptedException
    {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri( URI.create( BASE_URL + path ) )
                .header( "Content-Type", "application/json" );

        if( "POST".equalsIgnoreCase( method ) )
            requestBuilder.POST( HttpRequest.BodyPublishers.ofString( body == null ? "" : body ) );
        else if( "GET".equalsIgnoreCase( method ) )
            requestBuilder.GET();

        HttpRequest request = requestBuilder.build();
        HttpResponse<String> response = CLIENT.send( request, HttpResponse.BodyHandlers.ofString() );
        return response.body();
    }
}
