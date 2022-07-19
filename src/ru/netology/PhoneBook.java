package ru.netology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PhoneBook {

    private String phoneBookName; // имя телефонной книги

    private HashMap<String, List<Contact>> groupList; // список групп контактов в телефонной книге

    // конструктор Телефонной Книги, сразу создается пустой список Групп
    public PhoneBook(String phoneBookName) {
        this.phoneBookName = phoneBookName; // имя Телефонной Книги
        this.groupList = new HashMap<>(); // создать пустой список Групп в Телефонной Книге
    }

    // создать ПУСТУЮ ГРУППУ
    public boolean createGroup(String groupName) {
        if (groupName == null || groupName.equals("") || groupName.length() == 0) {
            return false;
        } else {
            List<Contact> contactList = new ArrayList<>();
            this.groupList.put(groupName, contactList);
            return true;
        }
    }

    // вернуть КОЛИЧЕСТВО ГРУПП
    public int getNumberOfGroups() {
        return this.groupList.size();
    }

    // ГРУППА есть в Телефонной Книге ???
    public boolean isGroupInPhoneBook(String groupName) {
        if (this.groupList.containsKey(groupName)) {
            return true;
        } else {
            return false;
        }
    }

    // создать НОВЫЙ контакт и СРАЗУ добавить его в Группу в Телефонной Книге
    public boolean createContactInGroup(String name, String phone, String groupName) {
        if (this.groupList.containsKey(groupName)) { // если в списке Групп есть группа с именем groupName
            List<Contact> contactList = this.groupList.get(groupName); // получаем список контактов contactList из группы
            contactList.add(new Contact(name, phone)); // добавляем новый контакт
            return true;
        } else {
            return false;
        }
    }

    // Показать имена Групп В ЛИНИЮ
    public void showGroupsNamesInLine() {
        if (this.groupList.isEmpty()) {
            System.out.println("Нет Групп контактов в Телефонной Книге !");
        } else {
            System.out.print("Доступные группы: ");
            int count = 0;
            for (String s : groupList.keySet()) {
                System.out.print("<" + s + ">");
                count++;
                if (count != groupList.keySet().size()) {
                    System.out.print(", ");
                } else {
                    System.out.print("\n");
                }
            }
        }
    }

    // Показать контакты из Группы
    public void showContactsInGroup(String groupName) {
        if (this.groupList.containsKey(groupName)) { // если в списке Групп есть группа с именем groupName
            List<Contact> contactList = this.groupList.get(groupName); // получаем список контактов contactList из группы
            if (contactList.size() > 0) { // если список контактов НЕ ПУСТОЙ
                System.out.println("Контакты в группе <" + groupName + ">");
                for (Contact contact : contactList) { // перебираем контакты в цикле и выводим в консоль
                    System.out.println(contact.getName() + " : " + contact.getPhone());
                }
            } else {
                System.out.println("В группе <" + groupName + "> нет контактов."); // сообщаем что группа ПУСТАЯ
            }
        } else { // нет группы контактов с именем groupName
            System.out.println("Нет группы контактов <" + groupName + "> в Телефонной Книге !");
        }
    }

    // Показать ВСЕ КОНТАКТЫ из ВСЕХ ГРУПП в Телефонной Книге
    public void showAllContactsInAllGroups() {
        if (this.groupList.isEmpty()) {
            System.out.println("Нет Групп контактов в Телефонной Книге !");
        } else {
            for (String s : groupList.keySet()) {
                this.showContactsInGroup(s);
                System.out.println();
            }
        }
    }

    // Найти контакт ПО НОМЕРУ ТЕЛЕФОНА
    public Contact searchContactByPhone(String phone) {
        for (String s : groupList.keySet()) { // по всем группам в Телефонной Книге
            List<Contact> contactList = this.groupList.get(s); // получаем список контактов contactList из группы
            for (Contact contact : contactList) { // перебираем контакты в цикле и ищем совпадение номеров
                if (contact.getPhone().equals(phone)) { // если нашли совпадение номеров
                    return contact; // возвращаем контакт
                }
            }
        }
        return null;
    }

    // Вернуть список групп для Контакта
    public List<String> getGroupListForContact(Contact contact) {
        List<String> contactGroupList = new ArrayList<>(); // список групп в которых состоит Контакт
        String phone = contact.getPhone(); // номер телефона от переданного в метод контакта
        for (String s : groupList.keySet()) { // по всем группам в Телефонной Книге
            List<Contact> contactList = this.groupList.get(s); // получаем список контактов contactList из группы
            for (Contact c : contactList) { // перебираем контакты в цикле и ищем совпадение номеров
                if (c.getPhone().equals(phone)) { // если нашли совпадение номеров
                    contactGroupList.add(s);
                }
            }
        }
        if (contactGroupList.size() > 0) { // если число групп в которых состоит Контакт
            return contactGroupList; // вернуть список групп в которых состоит Контакт
        } else {
            return null;
        }
    }


    @Override
    public String toString() {
        return "PhoneBook { " +
                "phoneBookName = '" + phoneBookName + '\'' +
                ", groupList = " + groupList +
                " } ";
    }
}
