package RestAPI;


import io.restassured.response.Response;
import testCases.BaseTest;
import utils.configuration.AppStrings;

import java.time.LocalDate;
import java.util.List;

import static io.restassured.RestAssured.get;


public class APIPage extends BaseTest {
    public boolean getPTSLocationPicker(){
        Response response = get(AppStrings.PTS_LOCATION_PICKER);
        List<String> locationsNumber = response.jsonPath().getList("name");

        if (locationsNumber.size() == 10) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getPTSHubCollections(){
        Response response = get(AppStrings.PTS_HUB_COLLECTION);
        List<String> collectionType = response.jsonPath().getList("data.collectionType");

        if (collectionType.contains("FILTERED_ITEM_V2")) {
            return true;
        } else {
            return false;
        }
    }


    public boolean getPTSAvailability(){
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate todayPlus1Month = today.plusMonths(1);
        LocalDate todayPlus1MonthAnd1Day = tomorrow.plusMonths(1);

        Response response = get(AppStrings.PTS_AVAILABILITY_1ST + todayPlus1Month + AppStrings.PTS_AVAILABILITY_2ND + todayPlus1MonthAnd1Day + AppStrings.PTS_AVAILABILITY_3RD);
        List<String> hotelName = response.jsonPath().getList("data.name");

        if (!hotelName.get(0).equals(null)){
            return true;
        } else {
            System.out.println("No availability for PTS");
            return false;
        }
    }


    public boolean getExperiencesCollections(){
        Response response = get(AppStrings.EXPERIENCES_COLLECTIONS);
        List<String> collectionType = response.jsonPath().getList("data.collectionType");

        if (collectionType.contains("FILTERED_EXPERIENCE_V2")) {
            return true;
        } else {
            return false;
        }
    }
}
