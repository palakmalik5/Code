package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Parser {
    private Scanner scanner;
    private CommandType commandType;
    private String symbol, dest, comp, jump;

    Parser(String filename) {
        try {
            var file = new File(filename);
            this.scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.exit(1);
        }
    }

    boolean hasMoreCommands() {
        return scanner.hasNext();
    }

    void advance() {
        symbol = scanner.nextLine().trim();
    }

    CommandType commandType() {
        dest = comp = jump = "";
        if (symbol.length() == 0) {
            return null;
        }
        switch (symbol.charAt(0)) {
        case '@':
            commandType = CommandType.A_COMMAND;
            break;
        case '(':
            commandType = CommandType.L_COMMAND;
            break;
        case '/':
            commandType = null;
            break;
        default:
            commandType = CommandType.C_COMMAND;
            var regexSemi = symbol.split(";");
            if (regexSemi.length == 2) {
                jump = regexSemi[1];
            } else {
                jump = "";
            }

            var regexEql = symbol.split("=");
            if (regexEql.length == 2) {
                dest = regexEql[0];
                comp = regexEql[1];
            } else {
                dest = "";
                comp = regexSemi[0];
            }
            break;
        }

        return commandType;
    }

    String symbol() {
        if (null == commandType) {
            return null;
        } else if (commandType.equals(CommandType.L_COMMAND)) {
            return symbol.split("[(,)]+")[1];
        } else {
            return symbol.split("@")[1];
        }
    }

    String dest() {
        return dest;
    }

    String comp() {
        return comp;
    }

    String jump() {
        return jump;
    }
}
