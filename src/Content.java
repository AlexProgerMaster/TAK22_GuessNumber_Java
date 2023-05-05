public class Content {
    private String name;
    private int steps;
    public Content(String name, int steps) {
        this.name = name;
        this.steps = steps;
    }
    public void showData() {
        if(name.length() > 15) {
            name = name.substring(0, 15);
        } else {
            name = String.format("%15s", name);
        }
        String n = String.format("%-10s", name);
        String s = String.format("%3d", steps);
        System.out.println(n + s);
    }
}
