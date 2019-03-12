package com.company.service;

import com.company.entity.Category;
import com.company.entity.Operation;
import com.company.util.ValidationUtil;

import java.math.BigDecimal;
import java.util.*;

public class ConsoleService {

    private final static String INCORRECT_DATE_ERROR = "By default, the current date will be used, " +
            "because you entered the date in an incorrect format.";

    private static ConsoleService INSTANCE;

    public static ConsoleService getInstance(OperationCreateListener createListener) {
        if (INSTANCE == null) {
            INSTANCE = new ConsoleService(createListener);
        }
        return INSTANCE;
    }

    public interface OperationCreateListener {
        void onSelectedAction(int action);

        void onSelectCategory();

        void onChooseDate(Date date);

        void createOperation(Operation operation);
    }

    private final List<Category> allCategories;
    private OperationCreateListener createListener;
    private Scanner scanner;
    private int categoryId = 0;

    private ConsoleService(OperationCreateListener createListener) {
        allCategories = Arrays.asList(Category.values());
        this.createListener = createListener;
        scanner = new Scanner(System.in);
    }

    public void chooseAction() {
        System.out.println("Choose action");
        System.out.println("For create new entry - press 1");
        System.out.println("For get some date balance - press 2");
        int action = 0;
        try {
            action = scanner.nextInt();
            if (ValidationUtil.isNotValidAction(action)) {
                showError("Uknown action");
                chooseAction();
                return;
            }
            createListener.onSelectedAction(action);
        } catch (InputMismatchException e) {
            showError("Incorrect input type");
            if (scanner.hasNextLine()) scanner.nextLine();
            chooseAction();
        }
    }

    public void chooseCategory() {
        System.out.println("Choose your operation category");
        for (int i = 1; i < allCategories.size(); i++) {
            System.out.println(allCategories.get(i).name + " - choose key - press " + allCategories.get(i).id);
        }
        int category = 0;
        try {
            category = scanner.nextInt();
            if (ValidationUtil.isNotValidCategory(category)) {
                showError("Unknown category");
                chooseCategory();
                return;
            }
            categoryId = category;
            createListener.onSelectCategory();
        } catch (InputMismatchException e) {
            showError("Incorrect input type");
            if (scanner.hasNextLine()) scanner.nextLine();
            chooseCategory();
        }
    }

    public void readAmount() {
        System.out.println("Please enter the operation sum");
        double sum = 0;
        try {
            sum = scanner.nextDouble();

            BigDecimal decimal = new BigDecimal(sum);
            decimal = decimal.setScale(2, BigDecimal.ROUND_HALF_EVEN);

            createListener.createOperation(new Operation(categoryId, decimal.doubleValue()));
            categoryId = 0;
        } catch (InputMismatchException e) {
            showError("Incorrect input type");
            if (scanner.hasNextLine()) scanner.nextLine();
            readAmount();
        }
    }


    public void readSearchDate() {
        System.out.println("Please enter the date in dd.mm.yyyy format");
        scanner.nextLine();

        String userDate = scanner.nextLine();
        String[] dateArray = userDate.split("\\.", 3);
        Calendar calendar = Calendar.getInstance();
        if (ValidationUtil.isCorrectDateFormat(dateArray)) {
            try {
                int day = Integer.parseInt(dateArray[0]);
                int month = Integer.parseInt(dateArray[1]);
                int year = Integer.parseInt(dateArray[2]);
                if (ValidationUtil.isCorrectDate(day, month, year)) {
                    calendar.set(year, month - 1, day, 23, 59);
                    createListener.onChooseDate(calendar.getTime());
                } else {
                    showError(INCORRECT_DATE_ERROR);
                    createListener.onChooseDate(calendar.getTime());
                }
            } catch (NumberFormatException e) {
                showError(INCORRECT_DATE_ERROR);
                createListener.onChooseDate(calendar.getTime());
            }

        } else {
            showError(INCORRECT_DATE_ERROR);
            createListener.onChooseDate(calendar.getTime());
        }

        for (String value : dateArray) {
            System.out.println(value);
        }
    }

    private void showError(String error) {
        System.out.println(error);
    }
}
