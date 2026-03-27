package fintech.driver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import fintech.model.Account;
import fintech.model.Transaction;
import fintech.model.TransferTransaction;

public class Driver2 {

    public static List<Transaction> createTransactions(String[] commands, List<Account> accounts) {
        List<Transaction> transactions = new ArrayList<>();
        for (String command : commands) {
            String[] tokens = command.split("#");
            if (tokens[0].equals("create-transaction")) {
                Account account = findAccount(tokens[1], accounts);
                if (account != null) {
                    double amount = Double.parseDouble(tokens[2]);
                    String postedAt = tokens[3];
                    String note = tokens[4];
                    Transaction transaction = new Transaction(account, amount, postedAt, note);
                    account.addTransaction(transaction);
                    transactions.add(transaction);
                }
            }
        }
        return transactions;
    }
}
