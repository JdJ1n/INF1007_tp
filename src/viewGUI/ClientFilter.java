package viewGUI;

import models.Client;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ClientFilter {

    public static ArrayList<Client> filterByStatus(ArrayList<Client> clientList, String filterStatus) {
        return (ArrayList<Client>) clientList.stream()
                .filter(client -> client.getStatus().equals(filterStatus))
                .collect(Collectors.toList());
    }
}
