package hr.algebra.controller.task;

import hr.algebra.model.entities.SmartWatch;
import hr.algebra.util.converter.DoubleToStringConverter;
import hr.algebra.util.converter.IntegerToStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;

import java.util.List;

public class FilterSmartWatchTask extends Task<ObservableList<SmartWatch>> {

    private final ObservableList<SmartWatch> allWatches;
    private final String query;

    public FilterSmartWatchTask(ObservableList<SmartWatch> allWatches, String query){
        this.allWatches = allWatches;
        this.query = query;
    }

    @Override
    protected ObservableList<SmartWatch> call(){
        if (query == null || query.isBlank()) {
            return allWatches;
        }

        String lower = query.toLowerCase();
        IntegerToStringConverter intToString = new IntegerToStringConverter();
        DoubleToStringConverter doubleToString = new DoubleToStringConverter();

        FilteredList<SmartWatch> filtered = new FilteredList<>(allWatches,w ->
                 w.getName().toLowerCase().contains(lower)
                        || intToString.convert(w.getYearOfMaking()).contains(lower)
                        || doubleToString.convert(w.getScreenSize()).contains(lower)
                        || intToString.convert(w.getBatteryLife()).contains(lower)
                        || (w.getIpRating() != null && w.getIpRating().toLowerCase().contains(lower))
                        || doubleToString.convert(w.getPrice()).contains(lower)
                        || (w.getBrand() != null && w.getBrand().getName().toLowerCase().contains(lower))
                        || (w.getCategory() != null && w.getCategory().getName().toLowerCase().contains(lower))
                        || (w.getOperatingSystem() != null && w.getOperatingSystem().getName().toLowerCase().contains(lower))
                        || (w.getHealthFunctions() != null && w.getHealthFunctions().stream()
                        .anyMatch(hf -> hf.getName().toLowerCase().contains(lower))));

        return FXCollections.observableArrayList(filtered);
    }
}
