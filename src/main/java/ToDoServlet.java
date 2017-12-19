import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ToDoServlet extends HttpServlet {
    public void writer(HashMap<String, List<String>> base) {
        try {
            BufferedWriter writer = Files.newBufferedWriter
                    (Paths.get("D:/Учеба/3курс/Программирование/Курсовая/ToDoList/Todo.txt")
                            , StandardCharsets.UTF_8);
            for (String p : base.keySet()) {
                writer.write(p + ":"
                        + String.join(",", base.get(p)));
                writer.newLine();
            }
            writer.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String[] lgnPswd = Stream.of(req.getCookies()).filter(c -> c.getName()
                .equals("login")).findFirst().get().getValue().split("\\|\\|\\|");
        String login = lgnPswd[0];
        HashMap<String, List<String>> base = new HashMap<>();

        try (Stream<String> lines = Files.lines
                (Paths.get("D:/Учеба/3курс/Программирование/Курсовая/ToDoList/Todo.txt")
                        , StandardCharsets.UTF_8)) {
            lines.forEach(line -> {
                String[] lgnArr = line.split(":");
                String l = lgnArr[0];
                String arr = lgnArr[1];
                String[] toDos = arr.split(","); //не юзать пароль с , и : и |||
                List<String> toDo = Arrays.asList(toDos);//массив строк в список
                base.put(l, toDo);
            });
        }

        List<String> todos = base.get(login);
        if (todos == null) {
            todos = new ArrayList<>();
        }

        req.setAttribute("todolist", todos);
        resp.setContentType("text/html;charset = utf-8");
        req.getRequestDispatcher("toDo.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {


        // github
        //unit для входа по логину
        String[] lgnPswd = Stream.of(req.getCookies()).filter(c -> c.getName()
                .equals("login")).findFirst().get().getValue().split("\\|\\|\\|");
        String login = lgnPswd[0];

        String addDescr = req.getParameter("addDescr");
        String delDescr = req.getParameter("delDescr");

        if ((addDescr == null) && (delDescr != null)) {
            HashMap<String, List<String>> base = new HashMap<>();
            try (Stream<String> lines = Files.lines
                    (Paths.get("D:/Учеба/3курс/Программирование/Курсовая/ToDoList/Todo.txt")
                            , StandardCharsets.UTF_8)) {
                lines.forEach(line -> {
                    String[] lgnArr = line.split(":");
                    String l = lgnArr[0];
                    if (l.equals(login)) {

                        String arr = lgnArr[1];
                        String[] toDos = arr.split(",");
                        List<String> tmp = new LinkedList<String>(Arrays.asList(toDos));
                        int i = tmp.indexOf(delDescr);
                        tmp.remove(i);
                        System.out.println(tmp);
                        base.put(l, tmp);


                        //List<String> toDo = Arrays.asList(toDos);
                        //base.replace(l, toDo);
                    }
                });
            }
            writer(base);
        }


        if ((delDescr == null) && (addDescr != null)) {
            HashMap<String, List<String>> base = new HashMap<>();
            try (Stream<String> lines = Files.lines
                    (Paths.get("D:/Учеба/3курс/Программирование/Курсовая/ToDoList/Todo.txt")
                            , StandardCharsets.UTF_8)) {
                lines.forEach(line -> {
                    String[] lgnArr = line.split(":");
                    String l = lgnArr[0];
                    String arr = lgnArr[1];
                    String[] toDos = arr.split(","); //не юзать пароль с , и : и |||
                    List<String> toDo = Arrays.asList(toDos);//массив строк в список
                    base.put(l, toDo);

                    if (l.equals(login)) {
                        arr = lgnArr[1];
                        arr += "," + addDescr;
                        toDos = arr.split(",");
                        toDo = Arrays.asList(toDos);
                        base.put(l, toDo);
                    }
                });
            }
            writer(base);
        }
        resp.sendRedirect(req.getContextPath() + "/ToDo");
    }
    //найти номер строки юзера и внести новую задачу
    //после добавления задачи сохранять hasgMap в файл
    //String joined = String.join(",", name); передать ей массив,преобразоваывыет массив в э
    //стоку разделенную запятыми


   /* Iterator<Map.Entry<String,List<String>>> iterator = base.entrySet().iterator();
        while (iterator.hasNext()) {
        Map.Entry<String,List<String>> pair = iterator.next();
        String key = pair.getKey();
        List value = pair.getValue();
        System.out.println(key + ":" + value);
    }

    int choice = 1;
        while (choice != 0) {
        //print list
        System.out.println();
        System.out.println( base.toString());  //calls toString()

        //print menu choices
        System.out.println("MENU:");
        System.out.println("1 - Add item");
        System.out.println("2 - Remove last item");
        System.out.println("3 - Remove specific item");
        System.out.println("0 - Quit");
        System.out.print("Enter your menu choice: ");

        //process user's menu choice
        try {
            choice = keybd.nextInt();
            keybd.nextLine();
            switch (choice) {
                case 1:  //ADD
                    System.out.print("Enter the thing you need to do: ");
                    String task = keybd.nextLine();
                    boolean added = list.add(task);
                    if (!added) {
                        System.out.println("Sorry, but this to-do list is already full!");
                    }
                    break;

                case 2:  //REMOVE LAST
                    String removed = list.remove(list.getSize());
                    if (removed != null) {
                        System.out.println("Removed: " + removed);
                    } else {
                        System.out.println("The to-do list is already empty.");
                    }
                    break;

                case 3:  //REMOVE AT
                    System.out.print("Enter the index of the item to remove: ");
                    int index = keybd.nextInt();
                    removed = list.remove(index);
                    if (removed != null) {
                        System.out.println("Removed: " + removed);
                    } else {
                        System.out.println("There is no item to be removed at index " +
                                index + ".");
                    }
                    break;

                case 0:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Sorry, but " + choice + " is not one of " +
                            "the menu choices. Please try again.");
                    break;
            }
        } catch (java.util.InputMismatchException ime) {
            System.out.println("Sorry, but you must enter a number.");
            keybd.nextLine();
        }

    }*/
}

