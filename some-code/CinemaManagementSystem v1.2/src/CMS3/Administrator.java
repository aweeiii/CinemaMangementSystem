package CMS3;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Scanner;

public class Administrator extends Person {
    private String username;
    private String password;

    public Administrator() {
    }

    public Administrator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    //管理员登录
    public void loginAdmin(String username, String password) {
        int userIndex = findUserIndexByUsername(administratorList, username);
        if (userIndex != -1) {
            Administrator administrator = (Administrator) administratorList.get(userIndex);
            if (!administrator.isLocked()) {
                // 获取存储在数据库或文件中的密码哈希
                String storedHashedPassword = administrator.getPassword();
                // 比较用户提供的密码哈希与存储的密码哈希
                if (BCrypt.checkpw(password, storedHashedPassword)) {
                    loginAttempts.remove(username);
                    System.out.println("登录成功，欢迎：" + username + "!");
                    administratorMenu1(username, password);
                } else {
                    int attempts = loginAttempts.getOrDefault(username, 0) + 1;
                    loginAttempts.put(username, attempts);
                    if (attempts >= 5) {
                        //administrator.lockAccount();
                        System.out.println("由于登录次数过多，账户已被锁定");
                    } else {
                        System.out.println("密码错误。剩余尝试次数：" + (5 - attempts));
                    }
                }
            } else {
                System.out.println("账户已被锁定，请联系客服支持。");
            }
        } else {
            System.out.println("用户不存在");
        }
    }

    public void administratorMainMenu() {
        Scanner sc = new Scanner(System.in);
        boolean shouldExit = false;
        while (!shouldExit) {
            System.out.println("=====================================");
            System.out.println("亲爱的管理员，请输入下列数字选择你想要办理的服务：         ");
            System.out.println("      1.登录您的账户                 ");
            System.out.println("      e.退出                 ");
            System.out.println("=====================================");
            String choice = sc.next();
            switch (choice) {
                case "1" -> {
                    System.out.println("请输入你的用户名：");
                    String username = sc.next();
                    System.out.println("请输入你的密码：");
                    String password = sc.next();
                    loginAdmin(username, password);
                }
                case "e" -> shouldExit = true;
                default -> System.out.println("无效的选项，请重新选择。");
            }
        }
    }

    public void administratorMenu1(String username, String password) {
        Scanner sc = new Scanner(System.in);
        boolean shouldExit = false;
        while (!shouldExit) {
            System.out.println("=====================================");
            System.out.println("     登陆成功，请选择你需要的服务            ");
            System.out.println("           1.密码管理                ");
            System.out.println("           2.用户（影城方）管理              ");
            System.out.println("           e.退出               ");
            System.out.println("=====================================");
            String choice = sc.next();
            switch (choice) {
                case "1" -> administratorMenu2(username, password);
                case "2" -> administratorMenu3();
                case "e" -> shouldExit = true;
                default -> System.out.println("无效的选项，请重新选择。");
            }
        }
    }

    public void administratorMenu2(String username, String password) {
        Scanner sc = new Scanner(System.in);
        boolean shouldExit = false;
        while (!shouldExit) {
            System.out.println("=====================================");
            System.out.println("        密码管理，请选择你需要的服务            ");
            System.out.println("           1.修改自身密码                ");
            System.out.println("           2.重置指定影城方用户（经理、前台）的密码             ");
            System.out.println("           e.退出               ");
            System.out.println("=====================================");
            String choice = sc.next();
            switch (choice) {
                case "1" -> changePassword(administratorList, username, password);
                case "2" -> {
                    System.out.println("请输入相应数字选择你要重置密码的用户类型");
                    System.out.println("1.经理");
                    System.out.println("2.前台");
                    int num = sc.nextInt();
                    System.out.println("请输入需要重置密码的用户名：");
                    username = sc.next();
                    resetPassword2(username, num);
                }
                case "e" -> shouldExit = true;
                default -> System.out.println("无效的选项，请重新选择。");
            }
        }
    }

    public void administratorMenu3() {
        Scanner sc = new Scanner(System.in);
        boolean shouldExit = false;
        int num;
        while (!shouldExit) {
            System.out.println("=====================================");
            System.out.println("         用户（影城方）管理，请选择你需要的服务                             ");
            System.out.println("           1.列出所有影城方用户（经理、前台）信息          ");
            System.out.println("           2.删除影城方用户（经理、前台）信息             ");
            System.out.println("           3.查询影城方用户（经理、前台）信息              ");
            System.out.println("           4.增加影城方用户（经理、前台）信息             ");
            System.out.println("           5.修改影城方用户（经理、前台）信息              ");
            System.out.println("           e.退出                                    ");
            System.out.println("=====================================");
            String choice = sc.next();
            switch (choice) {
                case "1" -> {
                    printUserInfo(managerList);
                    printUserInfo(FrontDeskList);
                }
                case "2" -> {
                    System.out.println("请输入你要删除的用户类型：");
                    System.out.println("1.经理");
                    System.out.println("2.前台");
                    num = sc.nextInt();
                    System.out.println("请输入你要删除的用户名：");
                    String username = sc.next();
                    System.out.println("确认删除？？？");
                    System.out.println("确认请按1");
                    System.out.println("取消请按0");
                    int choose = sc.nextInt();
                    if (choose == 1) deleteUserInfo(username, num);
                    if (choose == 0) return;
                }
                case "3" -> {
                    System.out.println("请输入你要查找的用户类型：");
                    System.out.println("1.经理");
                    System.out.println("2.前台");
                    num = sc.nextInt();
                    if (num == 1) findUserInfo(managerList);
                    else if (num == 2) {
                        findUserInfo(FrontDeskList);
                    }
                }
                case "4" -> addNewUser();
                case "5" -> {
                    System.out.println("请输入你要修改的用户类型");
                    System.out.println("1.经理");
                    System.out.println("2.前台");
                    num = sc.nextInt();
                    System.out.println("请输入你要修改的用户的用户名");
                    String username = sc.next();
                    modifyUserInfo(username, num);
                }
                case "e" -> shouldExit = true;
                default -> System.out.println("无效的选项，请重新选择。");
            }
        }
    }

    //管理员删除影城方信息
    public void deleteUserInfo(String username, int num) {
        int userIndex;
        if (num == 1) {
            userIndex = findUserIndexByUsername(managerList, username);
            if (userIndex != -1) {
                managerList.remove(userIndex);
            } else System.out.println("用户不存在。");
        } else if (num == 2) {
            userIndex = findUserIndexByUsername(FrontDeskList, username);
            if (userIndex != -1) {
                FrontDeskList.remove(userIndex);
            } else System.out.println("用户不存在。");
        } else System.out.println("无效的用户类型");
    }

    //管理员添加影城方用户信息
    public void addNewUser() {
        System.out.println("请开始输入你要增加的用户信息：");
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入你要添加的用户ID：");
        String userID = sc.next();
        System.out.println("请输入你要添加的用户名：");
        String username = sc.next();
        System.out.println("请输入你要添加的用户类型：");
        String userType = sc.next();
        System.out.println("请输入你要添加的用户手机号：");
        String phoneNumber = sc.next();
        System.out.println("请输入你要添加的用户邮箱：");
        String email = sc.next();
        System.out.println("请输入你要添加的用户密码：");
        String password = sc.next();
        if (userType.equals("经理")) {
            managerList.add(new Manager(userID, username, generateRegistrationTime(), userType, password, phoneNumber, email));
        } else if (userType.equals("前台")) {
            FrontDeskList.add(new FrontDesk(userID, username, generateRegistrationTime(), userType, password, phoneNumber, email));
        } else {
            System.out.println("无效的用户类型");
        }
    }

    //管理员修改影城方用户信息
    public void modifyUserInfo(String username, int num) {
        Scanner sc = new Scanner(System.in);
        int userIndex;
        if (num == 1) {
            userIndex = findUserIndexByUsername(managerList, username);
            if (userIndex != -1) {
                System.out.println("请选择你想要修改的用户信息：");
                System.out.println("1.用户名");
                System.out.println("2.用户邮箱");
                System.out.println("3.用户手机号");
                int num1 = sc.nextInt();
                switch (num1) {
                    case 1 -> {
                        System.out.println("请输入新的用户名");
                        String newUsername = sc.next();
                        managerList.get(userIndex).setUsername(newUsername);
                    }
                    case 2 -> {
                        System.out.println("请输入新的用户邮箱");
                        String newEmail = sc.next();
                        managerList.get(userIndex).setEmail(newEmail);
                    }
                    case 3 -> {
                        System.out.println("请输入新的用户手机号");
                        String newPhoneNumber = sc.next();
                        managerList.get(userIndex).setPhoneNumber(newPhoneNumber);
                    }
                }
                System.out.println("用户信息已成功修改。");
            } else {
                System.out.println("用户不存在");
            }
        } else if (num == 2) {
            userIndex = findUserIndexByUsername(FrontDeskList, username);
            if (userIndex != -1) {
                System.out.println("请选择你想要修改的用户信息：");
                System.out.println("1.用户名");
                System.out.println("2.用户邮箱");
                System.out.println("3.用户手机号");
                int num1 = sc.nextInt();
                switch (num1) {
                    case 1 -> {
                        System.out.println("请输入新的用户名");
                        String newUsername = sc.next();
                        FrontDeskList.get(userIndex).setUsername(newUsername);
                    }
                    case 2 -> {
                        System.out.println("请输入新的用户邮箱");
                        String newEmail = sc.next();
                        FrontDeskList.get(userIndex).setEmail(newEmail);
                    }
                    case 3 -> {
                        System.out.println("请输入新的用户手机号");
                        String newPhoneNumber = sc.next();
                        FrontDeskList.get(userIndex).setPhoneNumber(newPhoneNumber);
                    }
                }
                System.out.println("用户信息已成功修改。");
            } else {
                System.out.println("用户不存在");
            }
        }
    }

}

