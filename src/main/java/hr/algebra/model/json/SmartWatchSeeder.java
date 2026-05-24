package hr.algebra.model.json;

import hr.algebra.model.entities.*;
import hr.algebra.model.repositories.entities.UnitOfWorkImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SmartWatchSeeder {
    public static void seedIfEmpty() {
        try (UnitOfWorkImpl uow = new UnitOfWorkImpl()) {

            if (!uow.getSmartWatchRepository().getAll().isEmpty()) {
                System.out.println("DB already seeded, skipping.");
                return;
            }

            List<SmartWatchDTO> dtos = JsonLoader.loadSmartWatches();

            for (SmartWatchDTO dto : dtos) {
                seedWatch(uow, dto);
            }

            uow.commit();
            System.out.println("Seeded " + dtos.size() + " smart watches.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void seedWatch(UnitOfWorkImpl uow, SmartWatchDTO dto) throws Exception {

        // Brand
        Brand brand = new Brand(0, dto.brand, "", "");
        uow.getBrandRepository().save(brand);

        // Category
        Category category = new Category(0, dto.category);
        uow.getCategoryRepository().save(category);

        // OperatingSystem
        OperatingSystem os = new OperatingSystem(0, dto.operatingSystem, "", "");
        uow.getOperatingSystemRepository().save(os);

        // SmartWatch
        SmartWatch watch = new SmartWatch(
                0,
                dto.name,
                dto.yearOfMaking,
                dto.screenSize,
                dto.batteryLife,
                dto.ipRating,
                dto.price,
                dto.imagePath,
                brand,
                category,
                os
        );
        uow.getSmartWatchRepository().save(watch);

        // HealthFunctions
        if (dto.healthFunctions != null) {
            Set<SmartWatchHealthFunction> smartWatchHealthFunction = new HashSet<>();



            for (String hfName : dto.healthFunctions) {
                HealthFunction hf = new HealthFunction(0, hfName, "");
                uow.getHealthFunctionRepository().save(hf);

                SmartWatchHealthFunction swhf = new SmartWatchHealthFunction(0, watch.getId(), hf.getId());
                uow.getSmartWatchHealthFunctionRepository().save(swhf);

                smartWatchHealthFunction.add(swhf);

            }
            watch.setSmartWatchHealthFunction(smartWatchHealthFunction);
        }

        // CompatibleOsTypes
        if (dto.osTypes != null) {
            for (String osTypeName : dto.osTypes) {
                OperatingSystem compatibleOs = new OperatingSystem(0, osTypeName, "", "");
                uow.getOperatingSystemRepository().save(compatibleOs);
                uow.getSmartWatchRepository().linkOperatingSystem(watch.getId(), compatibleOs.getId());
            }
        }
    }

}
