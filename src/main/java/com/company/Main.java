package com.company;


import com.company.entity.Operation;
import com.company.service.ConsoleService;
import com.company.storage.StorageServise;

import java.util.Date;

public class Main {

    static ConsoleService consoleService;
    static StorageServise storageServise;

    public static void main(String[] args) {
        // write your code here

        ConsoleService.OperationCreateListener createListener = new ConsoleService.OperationCreateListener() {
            @Override
            public void onSelectedAction(int action) {
                if (action == 1) {
                    consoleService.chooseCategory();
                } else {
                    consoleService.readSearchDate();
                }
            }

            @Override
            public void onSelectCategory() {
                consoleService.readAmount();
            }

            @Override
            public void onChooseDate(Date date) {
                System.out.println(storageServise.getBalanceByDate(date));
                System.out.println();
                System.out.println();
                System.out.println();
                consoleService.chooseAction();
            }

            @Override
            public void createOperation(Operation operation) {
                storageServise.saveOperation(operation);
                System.out.println();
                System.out.println();
                System.out.println();
                consoleService.chooseAction();
            }
        };

        consoleService = ConsoleService.getInstance(createListener);
        storageServise = StorageServise.getInstance();
        consoleService.chooseAction();
    }
}
