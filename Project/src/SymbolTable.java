package src;

import java.util.HashMap;
import java.util.Map;

class SymbolTable {

    private final Map<String, Integer> symbolTable;

    SymbolTable() {
        this.symbolTable = new HashMap<>();
        addEntry("SP", 0);
        addEntry("LCL", 1);
        addEntry("ARG", 2);
        addEntry("THIS", 3);
        addEntry("THAT", 4);
        addEntry("R0", 0);
        addEntry("R1", 1);
        addEntry("R2", 2);
        addEntry("R3", 3);
        addEntry("R4", 4);
        addEntry("R5", 5);
        addEntry("R6", 6);
        addEntry("R7", 7);
        addEntry("R8", 8);
        addEntry("R9", 9);
        addEntry("R10", 10);
        addEntry("R11", 11);
        addEntry("R12", 12);
        addEntry("R13", 13);
        addEntry("R14", 14);
        addEntry("R15", 15);
        addEntry("SCREEN", 16384);
        addEntry("KBD", 24576);
    }

    void addEntry(String symbol, int address) {
        symbolTable.put(symbol, address);
    }

    boolean contains(String symbol) {
        return symbolTable.containsKey(symbol);
    }

    int getAddress(String symbol) {
        return symbolTable.get(symbol);
    }
}
