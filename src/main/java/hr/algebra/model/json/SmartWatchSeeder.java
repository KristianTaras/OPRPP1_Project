package hr.algebra.model.json;

import hr.algebra.model.entities.*;
import hr.algebra.model.repositories.entities.UnitOfWorkImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SmartWatchSeeder {

    private SmartWatchSeeder() {}

    public static void seedIfEmpty() {
        try (UnitOfWorkImpl uow = new UnitOfWorkImpl()) {

            if (!isDatabaseEmpty(uow)) {
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

    private static boolean isDatabaseEmpty(UnitOfWorkImpl uow) {
        String sql = "SELECT COUNT(*) FROM SmartWatch";

        try(PreparedStatement ps = uow.getConnection().prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) == 0;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    private static void seedWatch(UnitOfWorkImpl uow, SmartWatchDTO dto) throws Exception {

        // Brand
        Brand brand = saveBrandIfNonExistent(uow,dto);

        // Category
        Category category = saveCategoryIfNonExistent(uow,dto);

        // OperatingSystem
        OperatingSystem operatingSystem = saveOSIfNonExistent(uow, dto);

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
                operatingSystem
        );
        uow.getSmartWatchRepository().save(watch);


        // HealthFunctions - SmartWatchHealthFunction
        saveHealthFunctionIfNonExistent(uow,watch,dto);

        // CompatibleOsTypes - SmartWatchOperatingSystem
        if (dto.osTypes != null) {
            for (String osTypeName : dto.osTypes) {
                OperatingSystem compatibleOs = uow.getOperatingSystemRepository().getByName(osTypeName)
                                .orElseGet(()->{
                                    OperatingSystem os = new OperatingSystem(0, osTypeName,dto.osVersion, dto.osDeveloper);
                                    try { uow.getOperatingSystemRepository().save(os); } catch(Exception e){throw new RuntimeException(e);}
                                    return os;
                                });

                uow.getSmartWatchRepository().linkOperatingSystem(watch.getId(), compatibleOs.getId());
            }
        }
    }

    private static void saveHealthFunctionIfNonExistent(UnitOfWorkImpl uow, SmartWatch watch, SmartWatchDTO dto) throws Exception {
        if (dto.healthFunctions != null) {
            Set<SmartWatchHealthFunction> smartWatchHealthFunctions = new HashSet<>();

            for(int i=0; i<dto.healthFunctions.size();i++){
                String hfName = dto.healthFunctions.get(i);
                String hfDesc = (dto.healthFunctionDescriptions != null && i < dto.healthFunctionDescriptions.size())
                        ? dto.healthFunctionDescriptions.get(i)
                        : "";

                HealthFunction hf = uow.getHealthFunctionRepository().getByName(hfName)
                        .orElseGet(()->{
                            HealthFunction h = new HealthFunction(0, hfName, hfDesc);
                            try{ uow.getHealthFunctionRepository().save(h);}catch(Exception e){throw new RuntimeException(e);}
                            return h;
                        });

                SmartWatchHealthFunction swhf = new SmartWatchHealthFunction(0, watch.getId(), hf.getId());
                uow.getSmartWatchHealthFunctionRepository().save(swhf);
                smartWatchHealthFunctions.add(swhf);
            }

            watch.setSmartWatchHealthFunction(smartWatchHealthFunctions);
        }
    }

    private static OperatingSystem saveOSIfNonExistent(UnitOfWorkImpl uow, SmartWatchDTO dto) {
        return uow.getOperatingSystemRepository().getByName(dto.operatingSystem)
                .orElseGet(()->{
                    OperatingSystem os = new OperatingSystem(0, dto.operatingSystem, dto.osVersion, dto.osDeveloper);
                    try{ uow.getOperatingSystemRepository().save(os); } catch(Exception e) { throw new RuntimeException(e); }
                    return os;
                });
    }

    private static Category saveCategoryIfNonExistent(UnitOfWorkImpl uow, SmartWatchDTO dto) {
        return uow.getCategoryRepository().getByName(dto.category)
                .orElseGet(() -> {
                    Category c = new Category(0, dto.category);
                    try{ uow.getCategoryRepository().save(c);} catch(Exception e){throw new RuntimeException(e);}
                    return c;
                });
    }

    private static Brand saveBrandIfNonExistent(UnitOfWorkImpl uow, SmartWatchDTO dto) {
        return uow.getBrandRepository().getByName(dto.brand)
                .orElseGet(() -> {
                    Brand b = new Brand(0, dto.brand, dto.brandCountry, dto.brandDescription);
                    try{ uow.getBrandRepository().save(b);} catch (Exception e ) { throw new RuntimeException(e); }
                    return b;
                });

    }
}
