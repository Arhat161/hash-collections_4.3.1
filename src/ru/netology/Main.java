package ru.netology;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        PhoneBook myPhoneBook = new PhoneBook("Мои Контакты"); // создать Телефонную книгу
        Scanner scanner = new Scanner(System.in); // новый Сканнер
        String input = ""; // по-умолчанию ввод ничему не равен

        String name, phone, groupName = null;

        while (!input.equals("0")) {
            showActionMenu(); // показать меню действий
            input = scanner.nextLine(); // считать номер действия КАК СТРОКУ (!!!)
            switch (input) {
                case "1": // --- СОЗДАТЬ ГРУППУ ---
                    System.out.println("Введите имя группы:");
                    groupName = scanner.nextLine();
                    if (!myPhoneBook.isGroupInPhoneBook(groupName)) { // если такой группы еще нет
                        if (myPhoneBook.createGroup(groupName)) { // создаем группу
                            System.out.println("Группа <" + groupName + "> создана!\n");
                        } else {
                            System.out.println("Что-то пошло не так...\n");
                        }
                    } else {
                        System.out.println("Группа <" + groupName + "> уже существует!");
                    }
                    break;
                case "2": // --- ПОКАЗАТЬ СПИСОК ГРУПП ---
                    myPhoneBook.showGroupsNamesInLine();
                    break;
                case "3": // --- Создать КОНТАКТ и ДОБАВИТЬ В ГРУППЫ ---
                    System.out.println("Введите в одной строке имя и номер телефона");
                    System.out.println("Например: Иван +7(923)1231231");
                    input = scanner.nextLine();
                    String[] splitNamePhone = input.trim().split("\\s++"); // массив полей контакта
                    System.out.println("В какие группы добавляем контакт (укажите через запятую) ?");
                    if (myPhoneBook.getNumberOfGroups() > 0) {
                        myPhoneBook.showGroupsNamesInLine();
                    }
                    System.out.println("Если такой группы нет, она будет создана.");
                    input = scanner.nextLine();
                    String[] splitGroup = input.trim().split(","); // массив имен групп
                    if (splitNamePhone.length == 2) {
                        name = splitNamePhone[0];
                        phone = splitNamePhone[1];
                        // вносим контакт
                        if (splitGroup.length > 0) {
                            for (String group : splitGroup) {
                                group = group.trim();
                                if (!myPhoneBook.isGroupInPhoneBook(group)) {
                                    myPhoneBook.createGroup(group);
                                }
                                if (myPhoneBook.createContactInGroup(name, phone, group)) {
                                    System.out.println("Контакт [" + name + " : " + phone +
                                            "] добавлен в группу " + group + "!");
                                }
                            }
                        } else {
                            System.out.println("Вы не указали ГРУППЫ, куда будем добавлять контакт.");
                        }
                    } else {
                        System.out.println("Одно из двух значений для КОНТАКТА не введено!");
                    }
                    break;
                case "4": // --- ПОИСК КОНТАКТА (с показов групп в которых состоит найденный контакт) ---
                    System.out.println("Введите номер телефона для поиска контакта: ");
                    phone = scanner.nextLine();
                    try {
                        Contact searchResult = myPhoneBook.searchContactByPhone(phone); // объект по номеру телефона
                        List<String> contactGroupList = myPhoneBook.getGroupListForContact(searchResult); // группы для объекта
                        System.out.print("Найдено: [" + searchResult.getName() + " : " + searchResult.getPhone() + "]");
                        if (contactGroupList.size() > 0) {
                            System.out.print(", состоит в группах: ");
                            for (String s : contactGroupList) {
                                System.out.print("<" + s + "> ");
                            }
                            System.out.println();
                        }
                    } catch (Exception e) {
                        System.out.println("Контакт не найден!");
                    }
                    break;
                case "5": // --- ПОКАЗАТЬ ВСЕ ГРУППЫ И ВСЕ КОНТАКТЫ В НИХ ---
                    myPhoneBook.showAllContactsInAllGroups();
                    break;
            }

        }
    }

    public static void showActionMenu() {
        System.out.println("\nДействия:");
        System.out.println(
                "1. Добавить Группу в Телефонную Книгу\n" +
                        "2. Список Групп в Телефонной Книге\n" +
                        "3. Создать Контакт в Телефонной Книге \n" +
                        "4. Найти Контакт в Телефонной Книге \n" +
                        "5. Показать ВСЕ группы и ВСЕ контакты из Телефонной Книги\n" +
                        "0. Выход из программы");
    }
}
