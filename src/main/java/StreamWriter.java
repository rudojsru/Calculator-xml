
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class StreamWriter {

    XMLOutputFactory factory = XMLOutputFactory.newInstance();


    XMLStreamWriter writer = null;

    String file = "";   //"/home/rudojs/Downloads/java/rekrutacjaNordea/dataSolution003.xml";

    public void writer(Map map, String outputFile) {   //writes actions to a file
        file = outputFile + "/data_result.xml";

        try {
            writer = factory.createXMLStreamWriter(
                    new FileWriter(file));
            writer.writeStartDocument();
            writer.writeStartElement("expressions");


            Set<Map.Entry<String, String>> s = map.entrySet();
            for (Map.Entry m : s) {
                writer.writeStartElement("result");
                writer.writeAttribute("id", m.getKey().toString());
                writer.writeCharacters(m.getValue().toString());
                writer.writeEndElement();
            }


            writer.writeEndDocument();

            writer.flush();
            writer.close();
        } catch (XMLStreamException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}