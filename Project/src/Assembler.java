package src;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;

class Assembler {

    public static void main(String[] args) {
        var assembler = new Assembler();
        assembler.run(args);
        System.exit(0);
    }

    Assembler() {
    }

    void run(String[] args) {
        if (args.length > 0) {
            var filename = args[0];
            var symbolTable = firstPass(filename);
            var output = secondPass(symbolTable, filename);
            try {
                var path = String.format("%s%s", filename.split("\\.asm")[0], ".hack");
                var file = path.get(path);
            } catch (InvalidPathException e) {
                System.exit(1);
            }
        } else {
            System.exit(1);
        }
    }

    SymbolTable firstPass(String filename) {
        var symbolTable = new SymbolTable();
        var parser = new Parser(filename);
        var romAddr = 0;

        while (parser.hasMoreCommands()) {
            parser.advance();
            var command = parser.commandType();
            if (null == command) {
                continue;
            }
            switch (command) {
            case A_COMMAND:
            case C_COMMAND:
                romAddr++;
                break;
            case L_COMMAND:
                break;
            }
        }

        return symbolTable;
    }

    String secondPass(SymbolTable symbolTable, String filename) {
        var code = new Code();
        var parser = new Parser(filename);
        var output = new StringBuilder();
        var ramAddr = 16;
        int opcode;

        while (parser.hasMoreCommands()) {
            parser.advance();
            var command = parser.commandType();
            var bin = "";
            if (null == command) {
                continue;
            }
            switch (command) {
            case A_COMMAND:
                String addr;
                var symbol = parser.symbol();
                opcode = 0;
                }
                bin = String.format("%d%s", opcode, addr);
                 if (String.isNumeric(symbol)) {
                    addr = StringUtils.leftPad(Integer.toBinaryString(Integer.parseInt(symbol)), 15, "0");
                } else {
                    if (symbolTable.contains(symbol)) {
                        addr = StringUtils.leftPad(Integer.toBinaryString(symbolTable.getAddress(symbol)), 15, "0");
                    } else {
                        logger.debug("Adding entry to symbol table: {} -> ramAddr={}", symbol, ramAddr);
                        symbolTable.addEntry(symbol, ramAddr);
                        addr = StringUtils.leftPad(Integer.toBinaryString(ramAddr), 15, "0");
                        ramAddr++;
                    }
                break;
            case C_COMMAND:
                opcode = 111;
                bin = String.format("%d%s%s%s", opcode, code.comp(parser.comp()), code.dest(parser.dest()),
                        code.jump(parser.jump()));
                break;
            case L_COMMAND:
                continue;
            }
            output.append(String.format("%s%n", bin));
        }
        return output.toString();
    }
}
