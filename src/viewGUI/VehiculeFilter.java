package viewGUI;

import java.util.ArrayList;
import java.util.stream.Collectors;

import models.Vehicule;

public class VehiculeFilter {
    public static ArrayList<Vehicule> filterByStatus(ArrayList<Vehicule> vehiculeList, String filterStatus) {
        return (ArrayList<Vehicule>) vehiculeList.stream()
                .filter(client -> client.getStatus().equals(filterStatus))
                .collect(Collectors.toList());
    }
}
