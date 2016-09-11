package exercises.flight.search.impl;

import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;

import exercises.flight.search.*;

public class SearchEngineByOriginAndDestinationInCSVStream implements SearchEngine {

    private static final int CSVLINE_BASEPRICE_POSITION = 3;
    private static final int CSVLINE_FLIGHTNAME_POSITION = 2;
    private static final String REGEX_ANYCHARSEQUENCE = ".*";
    private static final String DEFAULT_CSV_SEPARATORS_SET = "[,;]";

    // Modifiable, configurable separatorSet will be used.
    private String separatorsSet = DEFAULT_CSV_SEPARATORS_SET;
    
    private final Reader flightListReader;

    public SearchEngineByOriginAndDestinationInCSVStream(Reader flightListReader) {
        this.flightListReader = flightListReader;
    }

    @Override
    public List<FlightTicket> getFlightTickets(SearchConditions searchConditions) {

        Predicate<? super String> originDestinationFilter = createOriginDestinationCSVSearchFilter(searchConditions);
        try (BufferedReader reader = new BufferedReader(flightListReader)) {

            return reader.lines()
                    .filter(originDestinationFilter)
                    .map(line -> createTicket(line))
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    private Predicate<? super String> createOriginDestinationCSVSearchFilter(SearchConditions searchConditions) {

        String regex = getOriginDestinationCSVSearchPattern(searchConditions);
        return line -> line.matches(regex);
    }

    private String getOriginDestinationCSVSearchPattern(SearchConditions searchConditions) {

        StringBuilder joiner = new StringBuilder()
                .append(Pattern.quote(searchConditions.origin))
                .append(separatorsSet)
                .append(Pattern.quote(searchConditions.destination))
                .append(separatorsSet)
                .append(REGEX_ANYCHARSEQUENCE);
        return joiner.toString();
    }

    private FlightTicket createTicket(String line) {

        String[] csvFields = parseLine(line);
        // May get a NumberFormatException, propagate 'cause out of specs or continue searching?
        // As of right now, will break search
        return new FlightTicket(csvFields[CSVLINE_FLIGHTNAME_POSITION], csvFields[CSVLINE_BASEPRICE_POSITION]);
    }

    private String[] parseLine(String line) {
        return line.split(separatorsSet);
    }
}
