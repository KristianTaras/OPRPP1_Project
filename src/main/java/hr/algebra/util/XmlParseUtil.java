package hr.algebra.util;

import hr.algebra.model.entities.HealthFunction;
import hr.algebra.model.entities.OperatingSystem;
import hr.algebra.model.entities.SmartWatch;
import hr.algebra.model.xml.SmartWatchXmlDto;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.File;
import java.util.List;


public final class XmlParseUtil {

    private XmlParseUtil() {}

    public static void saveSmartWatch(SmartWatch watch, String filePath) throws JAXBException{
        SmartWatchXmlDto dto = toDto(watch);

        new File(filePath).getParentFile().mkdirs();

        JAXBContext context = JAXBContext.newInstance(SmartWatchXmlDto.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(dto, new File(filePath));
    }

    private static SmartWatchXmlDto toDto(SmartWatch watch){
        SmartWatchXmlDto dto = new SmartWatchXmlDto();

        dto.setId(watch.getId());
        dto.setName(watch.getName());
        dto.setYearOfMaking(watch.getYearOfMaking());
        dto.setScreenSize(watch.getScreenSize());
        dto.setBatteryLife(watch.getBatteryLife());
        dto.setIpRating(watch.getIpRating());
        dto.setPrice(watch.getPrice());


        if (watch.getBrand() != null) {
            dto.setBrand(watch.getBrand().getName());
            dto.setBrandCountry(watch.getBrand().getCountry());
            dto.setBrandDescription(watch.getBrand().getDescription());
        }

        if (watch.getCategory() != null) {
            dto.setCategory(watch.getCategory().getName());
        }

        if (watch.getOperatingSystem() != null) {
            dto.setOperatingSystem(watch.getOperatingSystem().getName());
            dto.setOsVersion(watch.getOperatingSystem().getVersion());
            dto.setOsDeveloper(watch.getOperatingSystem().getDeveloper());
        }

        if (watch.getHealthFunctions() != null) {
            List<String> hfNames = watch.getHealthFunctions().stream()
                    .map(HealthFunction::getName)
                    .toList();
            dto.setHealthFunctions(hfNames);
        }

        if (watch.getSmartWatchOperatingSystem() != null) {
            List<String> osTypeNames = watch.getSmartWatchOperatingSystem().stream()
                    .map(OperatingSystem::getName)
                    .toList();
            dto.setOsTypes(osTypeNames);
        }

        return dto;
    }

}
