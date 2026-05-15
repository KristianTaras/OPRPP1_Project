package hr.algebra.controller.parser;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

public class XMLParser {

    private final String filePath = "src/main/resources/XMLFiles/File.xml";

    public static<T> void saveToXml(T data, String filePath) throws JAXBException{
        JAXBContext context = JAXBContext.newInstance(data.getClass());
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        marshaller.marshal(data, new File(filePath));
    }

    public static<T> T loadFromXml(Class<T> clazz, String filePath) throws JAXBException{
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        return clazz.cast(unmarshaller.unmarshal(new File(filePath)));
    }
}
