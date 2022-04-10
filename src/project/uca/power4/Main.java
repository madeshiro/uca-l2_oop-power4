package project.uca.power4;

public class Main {

    public static void main(String[] args) {
        Application app = new Application(false);
        System.exit(app.exec()); // eq. return app.exec();
    }
}
