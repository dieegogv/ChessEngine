import org.python.util.PythonInterpreter;

public class PythonInterpreter {
  public static void main(String[] args) {
    PythonInterpreter interpreter = new PythonInterpreter();
    interpreter.exec("sunfish.py");
  }
}

