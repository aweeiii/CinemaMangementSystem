package CMS4;

import java.util.Scanner;

import static CMS4.AllUserManagement.*;
public class Main {
    public static void main(String[] args) {
        loadPersonsFromExcel(userFile);
        loadPersonsFromExcel(adminFile);
        loadPersonsFromExcel(managerFile);
        loadPersonsFromExcel(frontDeskFile);
        loadMoviesFromExcel(movieFile);
        loadMovieSessionsFromExcel(movieSessionFile);
        /*administratorList.add(new Administrator("admin","ynuinfo#777"));
        managerList.add(new Manager("123123","manager", generateRegistrationTime(),"经理","111aaaAAA@@@","10086","10000"));
        FrontDeskList.add(new FrontDesk("456456","frontdesk",generateRegistrationTime(),"前台","222aaaAAA@@@","12315","20000"));
        writePersonsToExcel(administratorList,adminFile);
        writePersonsToExcel(managerList,managerFile);
        writePersonsToExcel(FrontDeskList,frontDeskFile);*/
        showMenu();
    }

    public static void showMenu() {
        Scanner sc = new Scanner(System.in);
        boolean shouldExit = false;
        while (!shouldExit) {
            System.out.println("=====================================");
            System.out.println("         欢迎来到云大影城           ");
            System.out.println("      请输入下列数字选择相应的身份            ");
            System.out.println("  1.消费者 2.管理员 3.前台 4.经理 e.退出   ");
            System.out.println("=====================================");
            String choice = sc.next();
            switch (choice) {
                case "1" -> {
                    User user = new User();
                    user.userMainMenu();
                    writePersonsToExcel(userList, userFile);
                    writeMovieSessionToExcel(sessionList, movieSessionFile);
                }
                case "2" -> {
                    Administrator administrator = new Administrator();
                    administrator.administratorMainMenu();
                    writePersonsToExcel(FrontDeskList, frontDeskFile);
                    writePersonsToExcel(managerList, managerFile);
                    writePersonsToExcel(administratorList, adminFile);
                }
                case "3" -> {
                    FrontDesk frontDesk = new FrontDesk();
                    frontDesk.frontDeskMainMenu();
                }
                case "4" -> {
                    Manager manager = new Manager();
                    manager.managerMainMenu();
                    writePersonsToExcel(managerList, managerFile);
                    writePersonsToExcel(userList, userFile);
                    writeMovieToExcel(movieList, movieFile);
                    writeMovieSessionToExcel(sessionList, movieSessionFile);
                }
                case "e" -> shouldExit = true;
                default -> System.out.println("无效的选项，请重新选择。");
            }
        }
    }
}