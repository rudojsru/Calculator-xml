
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;

public class StreamReader {
    private static XMLStreamReader reader;
    private static Map map;

    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
        System.out.println("Start .....");

        XMLInputFactory factory = XMLInputFactory.newInstance();
        File f = new File(args[0]);
        File f2 = new File(args[1]);
        String file="";
        String outputFile=f2.getPath();
       if(f.exists()) {
           String inputFile = f.getPath();
      //      String file = "/home/rudojs/Downloads/java/rekrutacjaNordea/data003.xml";
             file = inputFile;
       }else{
           System.out.println("File was not found.  Exit ......... ");
           return ;
       }
        reader = factory.createXMLStreamReader(new FileInputStream(file));

        map = new LinkedHashMap();

        action();
      //  System.out.println(map);
        StreamWriter streamWriter = new StreamWriter();
        streamWriter.writer(map,outputFile);
        System.out.println(" The script has finished work.");
    }

    private static void action() throws XMLStreamException {   //chooses which algorithm we should execute (addition, subtraction ...)
        String action;
        while (reader.hasNext()) {
            reader.next();
            if (reader.isStartElement()) {

                action = reader.getLocalName().toLowerCase();

                if (action == "addition"
                        || action == "subtraction"
                        || action == "multiplication"
                        || action == "division") {    //addition  :    +
                    int id = Integer.parseInt(reader.getAttributeValue(0));
                    int number = numberCollector(action);
                    map.put(id, number);
                }
            }
        }
    }

    private static int numberCollector(String action) throws XMLStreamException {  //  collects results for each action
        int result = 0;
        do {
            reader.next();
            int n;
            if (reader.hasText() && reader.getText().trim().length() > 0) {
                n = Integer.parseInt(reader.getText());
                if (result == 0) {
                    result = n;
                    continue;
                }

                result = getResult(action, result, n);

            }
            if (reader.isEndElement()) {
                if (reader.getLocalName() == action) {
                    break;
                }
            }


            if (reader.isStartElement()) {           // check whether there is any other arithmetic action inside the action.
                String action2If = reader.getLocalName().toLowerCase();
                if (action2If == "addition"
                        || action2If == "subtraction"
                        || action2If == "division"
                        || action2If == "multiplication") {
                    n = numberCollector(action2If);
                    if (result != 0) {
                        result = getResult(action, result, n);
                    } else {
                        result = n;
                    }
                }
            }

        } while (true);
        return result;
    }

    private static int getResult(String action, int result, int n) {
        switch (action) {
            case "addition":
                result = result + n;
                break;
            case "subtraction":
                result = result - n;
                break;
            case "division":
                if (result != 0)
                    result = result / n;
                break;
            case "multiplication":
                result = result * n;
                break;
        }
        return result;
    }
}