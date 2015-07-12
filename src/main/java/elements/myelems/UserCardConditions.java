package elements.myelems;

import elements.detailed.PageElements;
import elements.detailed.Result;
import elements.detailed.Status;
import org.openqa.selenium.WebElement;

import java.util.List;

public class UserCardConditions {

    public UserCardConditions(List<WebElement> listKeys, List<WebElement> listValues) {
        this.listKeys = new PageElements(listKeys, "keys");
        this.listValues = new PageElements(listValues, "values");
    }

    public Result<String> getTextAgainst(String option) {
        Result<Integer> pos = listKeys.getPositionOfElementContainsText(option);
        int position = 0;
        String message;
        String result = "";
        boolean status;
        if (pos.getStatus().getBooleanValue()) {
            position = pos.getValue();
            message = pos.getDescription() + " Key located at position #" + position + ". ";
        } else {
            message = pos.getDescription() + " Unable to locate key to get text against it. ";
        }

        Result<String> valueGetting = listValues.getTextFromElementNumber(position);
        if (valueGetting.getStatus().getBooleanValue()) {
            result = valueGetting.getValue();
            message += valueGetting.getDescription();
            status = true;
        } else {
            message += valueGetting.getDescription();
            status = false;
        }
        return new Result<String>(status, result, message);
    }

    private PageElements listKeys;
    private PageElements listValues;
}
