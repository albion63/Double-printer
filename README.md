# Double-printer
This program does two things:
1. It parses .csv files and prints requested data in given format (file smallStickerFormat.txt) onto first printer.
2. It prints .pdf files onto second printer.

Typically .csv files contain data about customers: customer name, customer address, etc.
But only portion of data needs to be printed, which is why csv files are parsed in order for needed data to be selected.
On the other hand, .pdf files are printed such as they are.

Note that application window was done in JavaFX.
